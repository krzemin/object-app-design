import org.specs2.mutable._
import Singletons._

class SingletonsSpec extends Specification {

  
  "SingleInstanceSingleton" should {
    "create an instance" in {
      val singleton = new SingleInstanceSingleton(Some("abc"))
      singleton.get must not beNull
    }

    "create an unique instance" in {
      val singleton = new SingleInstanceSingleton(Some("abc"))
      val obj1 = singleton.get
      val obj2 = singleton.get
      (obj1 eq obj2) must beTrue
    }
  }

  "PerThreadInstanceSingleton" should {
    "create an instance" in {
      val singleton = new PerThreadInstanceSingleton(Some("abc"))
      singleton.get must not beNull
    }

    "create an unique instance in the same thread" in {
      val singleton = new PerThreadInstanceSingleton(Some("abc"))
      val obj1 = singleton.get
      val obj2 = singleton.get
      (obj1 eq obj2) must beTrue
    }

    "create separate instances in different threads" in {
      val singleton = new PerThreadInstanceSingleton(Some("abc"))
      var obj1: Object = null
      var obj2: Object = null
      val t1: Thread = new Thread(new Runnable {
        override def run(): Unit = { obj1 = singleton.get }
      })
      val t2: Thread = new Thread(new Runnable {
        override def run(): Unit = { obj2 = singleton.get }
      })
      t1.start()
      t2.start()
      t1.join()
      t2.join()
      (obj1 eq obj2) must beFalse
    }
  }

}
