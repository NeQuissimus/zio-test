package bla

import scalaz.zio._
import scalaz.zio.test._

object Bla {
  def helloWorld = ZIO.succeed("Hello World")
}

object BlaSpec extends TestSpec with DefaultRuntime with Matchers {
  val tests = Map(
    "first test" -> first,
    "second test" -> second
  )

  def first: Task[Result] = {
    Bla.helloWorld.map(expect("Hello World"))
  }

  def second: Task[Result] = {
    Bla.helloWorld.map(expect("FooBar"))
  }
}
