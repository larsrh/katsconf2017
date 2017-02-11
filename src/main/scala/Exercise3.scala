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

  type TerminalIO[A] = Free[Terminal, A]

  object Terminal {
    private def lift[A](t: Terminal[A]): TerminalIO[A] =
      Free.liftF[Terminal, A](t)

    // Task 2: Define convenience functions for the operations.

    def readLine: TerminalIO[String] = lift(ReadLine)
    def writeLine = ???

    def run[A, M[_] : Monad](tio: TerminalIO[A])(interpreter: FunctionK[Terminal, M]): M[A] = tio.foldMap[M](interpreter)
  }

  // Task 3: Write an interpreter which compiles `Terminal` to `IO`.

  def terminalToEval: FunctionK[Terminal, Eval] = λ[FunctionK[Terminal, Eval]] {
    case ReadLine => Eval.later(StdIn.readLine())
  }


  // Task 4: Write an interpreter which compiles `Terminal` to `State`.

  case class Mock(in: List[String], out: List[String])
  type MockState[A] = State[Mock, A]

  def terminalToState: FunctionK[Terminal, MockState] = ???

  // Task 5: Be creative :-)

  val program = for {
    x <- Terminal.readLine
    y <- Terminal.readLine
  } yield x + y


  println(Terminal.run(program)(terminalToEval).value)

}
