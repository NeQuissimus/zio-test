package scalaz.zio.test

import sbt.testing.{ Fingerprint, Framework, SubclassFingerprint }

import scalaz.zio.{ Runtime, Task }

trait TestSpec extends Runtime[Any] { self: Singleton  =>
  def tests: Map[String, Task[Result]]
}

trait Matchers {
  def equals[A](expected: A, got: A) = if (expected == got) Succeeded else Failed(s"Expected ${expected}, got ${got}")
  def expect[A](expected: A)(got: A) = equals(expected, got)
}

sealed abstract class Result
case object Succeeded extends Result
final case class Failed(reason: Throwable) extends Result
object Failed {
  def apply(s: String) = new Failed(new Exception(s))
}

final class TestFramework extends Framework {
  override val name = "ZIO-Test"

  val fingerprints: Array[Fingerprint] = Array(
    new SubclassFingerprint {
      val superclassName          = "scalaz.zio.test.TestSpec"
      val isModule                = true
      val requireNoArgConstructor = false
    }
  )

  override def runner(args: Array[String], remoteArgs: Array[String], testClassLoader: ClassLoader): ZioTestRunner =
    ZioTestRunner(args, remoteArgs, testClassLoader)
}
