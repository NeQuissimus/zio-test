package scalaz.zio.test

trait PlatformReflection extends Reflection {
  def loadObject[T](name: String): T = Class.forName(s"${name}$$").getField("MODULE$").get(null).asInstanceOf[T]
}
