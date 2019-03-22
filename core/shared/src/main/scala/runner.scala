package scalaz.zio.test

import java.util.Arrays

import sbt.testing.{ EventHandler, Logger, Runner, Task, TaskDef }

final class ZioTestRunner(val args: Array[String], val remoteArgs: Array[String]) extends Runner {
  def done(): String                           = ???
  def tasks(defs: Array[TaskDef]): Array[Task] = defs.map(taskDefToTask)

  private def taskDefToTask(td: TaskDef): Task = new Task {
      override def taskDef(): TaskDef = td
      override def execute(eventHandler: EventHandler, loggers: Array[Logger]): Array[Task] = {
        loggers.foreach(_.debug(s"Using args: ${ZioTestRunner.arrayToString(args)}"))
        loggers.foreach(_.debug(s"Using remoteArgs: ${ZioTestRunner.arrayToString(remoteArgs)}"))

        // RUN THE IOs

        ???
      }
      override def tags(): Array[String] = Array.empty
    }
}

object ZioTestRunner {
  def apply(args: Array[String], remoteArgs: Array[String], testClassLoader: ClassLoader): ZioTestRunner = new ZioTestRunner(args, remoteArgs)

  private def arrayToString(a: Array[String]): String = Arrays.toString(a.asInstanceOf[Array[AnyRef]])
}
