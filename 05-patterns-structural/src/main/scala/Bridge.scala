object Bridge extends App {

  case class Person(name: String, age: Int)

  trait PersonReader {
    def readPeople(): Unit
  }

  trait PersonNotifier {
    def notifyPeople(): Unit
  }

  trait PersonRegistry {
    this: PersonReader with PersonNotifier =>

    var people: List[Person] = Nil
  }

  trait DatabasePersonReader extends PersonReader {
    this: PersonRegistry =>

    def readPeople() {
      people ++= List(
        Person("Zenek", 40),
        Person("Franek", 25),
        Person("Mietek", 51),
        Person("Zdzisiek", 44)
      )
    }
  }

  trait SmsPersonNotifier extends PersonNotifier {
    this: PersonRegistry =>

    def notifyPeople() {
      people.foreach { person =>
        println(s"Sending SMS message to $person")
      }
    }
  }

  trait EmailPersonNotifier extends PersonNotifier {
    this: PersonRegistry =>

    def notifyPeople() {
      people.foreach { person =>
        println(s"Sending e-mail to $person")
      }
    }
  }

  val registry1 = new PersonRegistry with DatabasePersonReader with SmsPersonNotifier

  registry1.readPeople()
  registry1.notifyPeople()

  val registry2 = new PersonRegistry with DatabasePersonReader with EmailPersonNotifier

  registry2.readPeople()
  registry2.notifyPeople()

}
