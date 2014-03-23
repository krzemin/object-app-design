import org.specs2.mutable._
import Singletons._

class SingletonsSpec extends Specification {
  "SingleInstanceSingleton" should {
    "create an instance" in {
      val singleton = new SingleInstanceSingleton(new String("abc"))
      singleton.get must not beNull
    }

    "create an unique instance" in {
      val singleton = new SingleInstanceSingleton(new String("abc"))
      val obj1 = singleton.get
      val obj2 = singleton.get
      (obj1 eq obj2) must beTrue
    }
  }

}
