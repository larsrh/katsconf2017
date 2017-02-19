package katsconf

import scala.language.higherKinds
import scala.io.StdIn

import cats._
import cats.arrow.FunctionK
import cats.data.State
import cats.free._

object Exercise3 extends App {

  // Task 1: Define an "algebra" for I/O on terminals.

  sealed trait Terminal[A]
  case object ReadLine extends Terminal[String]
  case class WriteLine(inp: String) extends Terminal[Unit]

  type TerminalIO[A] = Free[Terminal, A]

  object Terminal {
    private def lift[A](t: Terminal[A]): TerminalIO[A] =
      Free.liftF[Terminal, A](t)

    // Task 2: Define convenience functions for the operations.

    def readLine: TerminalIO[String] = lift(ReadLine)
    def writeLine(inp:String): TerminalIO[Unit] = lift(WriteLine(inp))

    def run[A, M[_] : Monad](tio: TerminalIO[A])(interpreter: FunctionK[Terminal, M]): M[A] = tio.foldMap[M](interpreter)
  }

  // Task 3: Write an interpreter which compiles `Terminal` to `IO`.

  def terminalToEval: FunctionK[Terminal, Eval] = λ[FunctionK[Terminal, Eval]] {
    case ReadLine => Eval.later(StdIn.readLine())
    case WriteLine(inp) => Eval.later(Console.out.println(inp))
  }


  // Task 4: Write an interpreter which compiles `Terminal` to `State`.

  case class Mock(in: List[String], out: List[String])
  type MockState[A] = State[Mock, A]

  def terminalToState: FunctionK[Terminal, MockState] = λ[FunctionK[Terminal, MockState]] {
    case ReadLine =>
      State[Mock, String] {
        case m @ Mock(Nil, out) => (m, "")
        case Mock(in :: ins, out) => (Mock(ins, out), in)
      }
    case WriteLine(s) => State.modify(m => m.copy(out = s :: m.out))
  }

  // Task 5: Be creative :-)

  val program: Free[Terminal, String] = for {
    r1 <- Terminal.readLine
    r2 <- Terminal.readLine
    _ <- Terminal.writeLine(s"::: $r1 :::")
    _ <- Terminal.writeLine(s"::: $r2 :::")
  } yield s"$r1 $r2"

  val mockState = Mock(in = List("hello ", "world"), out = List.empty)
  val runTermToState: (Mock, String) =
    Terminal.run(program)(terminalToState)
    .run(mockState).value
  println(s"processed Mock state ${runTermToState._1}")
  val runTermToEval: Eval[String] =
    Terminal.run(program)(terminalToEval)

  println(s"program output ${runTermToEval.value}")

}
