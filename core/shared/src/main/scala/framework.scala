package scalaz.zio.test

import sbt.testing.{ Fingerprint, Framework, SubclassFingerprint }

import scalaz.zio.Task

trait TestSpec { self: Singleton =>
  def tests: Map[String, Task[Result]]
}

sealed abstract class Result
case object Succeeded extends Result
case object Failed extends Result

final class TestFramework extends Framework {
  override val name = "ZIO-Test"

  val fingerprints: Array[Fingerprint] = Array(
    new SubclassFingerprint {
      val superclassName          = classOf[TestSpec].getName
      val isModule                = true
      val requireNoArgConstructor = false
    }
  )

  override def runner(args: Array[String], remoteArgs: Array[String], testClassLoader: ClassLoader): ZioTestRunner =
    ZioTestRunner(args, remoteArgs, testClassLoader)
}
