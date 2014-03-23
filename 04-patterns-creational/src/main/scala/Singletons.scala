import org.joda.time.{Duration, DateTime}

object Singletons {

  trait Singleton[T] {
    def get: T
  }

  class SingleInstanceSingleton[T](create: => T) extends Singleton[T] {
    private var instance: T = _
    def get: T = {
      if(instance == null) {
        instance = create
      }
      instance
    }
  }

  class PerThreadInstanceSingleton[T](create: => T) extends Singleton[T] {
    private var instances = Map.empty[Long, T]
    def get: T = {
      val threadId = Thread.currentThread().getId
      instances.get(threadId) match {
        case Some(instance) => instance
        case None =>
          val inst = create
          instances += (threadId -> inst)
          inst
      }
    }
  }

  class TimeWindowedInstanceSingleton[T](create: => T, windowSize: Duration)
    extends Singleton[T] {

    private var lastCreate: DateTime = _
    private var instance: T = _

    def get: T = {
      if(instance == null || lastCreate.plus(windowSize).isBeforeNow) {
        instance = create
        lastCreate = DateTime.now
      }
      instance
    }

  }

}
