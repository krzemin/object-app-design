object Pool {

  class Aeroplane(val airport: Airport) {
    def fly() { println("Fly...") }
    def release() {
      airport.release(this)
    }
  }

  class Airport(val poolSize: Int) {

    var freePool = (1 to poolSize).map(_ => new Aeroplane(this)).toList
    var busyPool = Set.empty[Aeroplane]

    def get: Aeroplane = {
      freePool match {
        case Nil =>
          throw new NoSuchElementException
        case aeroplane :: free =>
          freePool = free
          busyPool += aeroplane
          aeroplane
      }
    }

    def release(aeroplane: Aeroplane) {
      busyPool -= aeroplane
      freePool ::= aeroplane
    }

  }



}
