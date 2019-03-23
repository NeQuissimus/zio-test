package scalaz.zio.test

trait Reflection {
  def loadObject[T](name: String): T
}
