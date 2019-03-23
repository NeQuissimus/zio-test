package bla

import scalaz.zio._
import scalaz.zio.test._

object Bla {
  def helloWorld = ZIO.succeed("Hello World")
  def foobar     = ZIO.fail(new Exception("This is no good"))
}

object BlaSpec extends TestSpec with DefaultRuntime with SimpleMatchers {
  val tests = Map(
    "first test"  -> first,
    "second test" -> second,
    "third test"  -> third
  )

  def first: Task[Result] =
    Bla.helloWorld.map(expect("Hello World"))

  def second: Task[Result] =
    Bla.helloWorld.map(expect("FooBar"))

  def third: Task[Result] =
    Bla.foobar.mapError { e =>
      compare[Throwable]("Did not receive the expected error")(a => a.getMessage == e.getMessage)(e)
    }.flip
}
