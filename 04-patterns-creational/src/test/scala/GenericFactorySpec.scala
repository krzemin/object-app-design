import org.specs2.mutable._

class NoParamClass() { var k = 5 }
class MultiParamClass(var a: Int, var b: String, var c: Long)

class GenericFactorySpec extends Specification {

  "GenericFactory" should {
    "create instance of parameterless class" in {
      val gf = new GenericFactory
      gf.createObject("NoParamClass", false) must not beNull
    }
//    "create instance of multi-parameter class" in {
//      val gf = new GenericFactory
//      gf.createObject("MultiParamClass", false, 5, "test", 10L) must not beNull
//    }
  }

}
