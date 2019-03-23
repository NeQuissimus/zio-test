package scalaz.zio.test

import scala.reflect.ClassTag

import scala.scalajs.reflect.Reflect

trait PlatformReflection extends Reflection {
  def loadObject[T](name: String): T = Reflect.lookupLoadableModuleClass(name).getOrElse(null).asInstanceOf[T]
}
