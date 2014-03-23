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

}
