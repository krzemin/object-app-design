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
      println(s"threadID: $threadId")
      instances.get(threadId) match {
        case Some(instance) => instance
        case None =>
          val inst = create
          instances += (threadId -> inst)
          inst
      }
    }
  }

}
