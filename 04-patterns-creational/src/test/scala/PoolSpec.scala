import org.specs2.mutable._
import Pool._

class PoolSpec extends Specification {
  "An Airport" should {
    "provide an aeroplane" in {
      val airport = new Airport(1)
      val aeroplane = airport.get
      (aeroplane.airport eq airport) must beTrue
    }
    "provide multiple aeroplanes" in {
      val airport = new Airport(3)
      val aeroplane1 = airport.get
      val aeroplane2 = airport.get
      (aeroplane1 eq aeroplane2) must beFalse
    }
    "throw when pool is exceeded" in {
      val airport = new Airport(0)
      airport.get must throwA[NoSuchElementException]
    }
    "reuse objects in pool" in {
      val airport = new Airport(1)
      val aeroplane1 = airport.get
      aeroplane1.release()
      val aeroplane2 = airport.get
      (aeroplane1 eq aeroplane2) must beTrue
    }
  }

}
