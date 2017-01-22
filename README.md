# katsconf2017

Tutorial at Kats Conf 2, 2017 [![Build Status](https://travis-ci.org/larsrh/katsconf2017.svg?branch=master)](https://travis-ci.org/larsrh/katsconf2017)

## Installation instructions

SBT is the "Scala Build Tool".
We will use it to compile & run our code.

You need a working installation of Java 8.

### Windows

Go to the [SBT download page](http://www.scala-sbt.org/download.html) and install it from there.

### Linux/macOS

Run the following commands:

```
$ ./setup
$ ./sbt
```

After downloading the entire Internet, this should load up the SBT shell.

_Alternatively,_ you can install SBT from your distribution's package manager:
- Arch Linux: `sbt`
- Homebrew: `sbt`
- deb-based/rpm-based: [additional repository](http://www.scala-sbt.org/download.html)

## Development

Depending on your installation, run `./sbt` or `sbt` in the repository root.
SBT will print something like this (or similar):

```
[info] Loading global plugins from /home/lars/.sbt/0.13/plugins/project
[info] Loading global plugins from /home/lars/.sbt/0.13/plugins
[info] Loading project definition from /home/lars/proj/katsconf2017/project
[info] Set current project to katsconf2017 (in build file:/home/lars/proj/katsconf2017/)
>
```

This is the SBT shell where you can type commands.
As a first step, type `update`, which will fetch all necessary dependencies.
To compile, run, or test, type `compile`, `run`, or `test`, respectively.
To exit, press `Ctrl-D` or type `exit`.
