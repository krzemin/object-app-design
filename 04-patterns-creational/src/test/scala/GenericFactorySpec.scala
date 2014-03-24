import org.specs2.mutable._

class NoParamClass() { var k = 5 }
class MultiParamClass(var a: Int, var b: String, var c: Long)

class GenericFactorySpec extends Specification {

  "GenericFactory" should {
    "create instance of parameterless class" in {
      val gf = new GenericFactory
      val obj1 = gf.createObject("NoParamClass", false)
      val obj2 = gf.createObject("NoParamClass", false)
      (obj1 eq obj2) must beFalse
    }
    "create instance of multi-parameter class" in {
      val gf = new GenericFactory
      val obj1 = gf.createObject("MultiParamClass", false, 5, "test", 10L)
      val obj2 = gf.createObject("MultiParamClass", false, 5, "test", 10L)
      (obj1 eq obj2) must beFalse
    }
    "create unique instance in singleton mode" in {
      val gf = new GenericFactory
      val obj1 = gf.createObject("NoParamClass", true)
      val obj2 = gf.createObject("NoParamClass", true)
      (obj1 eq obj2) must beTrue
    }
    "create unique instance in singleton mode for the same parameters" in {
      val gf = new GenericFactory
      val obj1 = gf.createObject("MultiParamClass", true, 1, "a", 1L)
      val obj2 = gf.createObject("MultiParamClass", true, 1, "a", 1L)
      (obj1 eq obj2) must beTrue
    }
    "create different instances in singleton mode for different parameters" in {
      val gf = new GenericFactory
      val obj1 = gf.createObject("MultiParamClass", true, 1, "a", 1L)
      val obj2 = gf.createObject("MultiParamClass", true, 2, "a", 1L)
      (obj1 eq obj2) must beFalse
    }
  }

}
