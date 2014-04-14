object EventAggregator extends App {

  trait Subscriber {
    def notify(msgType: String, msg: Any)
  }

  class EventAggregator {

    var subscribers: Map[String, Set[Subscriber]] = Map.empty

    def subscribe(msgType: String, subscriber: Subscriber) {
      subscribers += msgType -> (subscribers.getOrElse(msgType, Set.empty[Subscriber]) + subscriber)
    }
    def publish(msgType: String, msg: Any) {
      subscribers(msgType) foreach { s =>
        s.notify(msgType, msg)
      }
    }
  }


  object Sub1 extends Subscriber {
    def notify(msgType: String, msg: Any) = msgType match {
      case "News" => println(s"received news: $msg")
      case "Alarm" => println(s"received alarm: $msg")
    }
  }

  val ea = new EventAggregator
  ea.subscribe("News", Sub1)
  ea.subscribe("Alarm", Sub1)

  ea.publish("News", "Kaka")
  ea.publish("Alarm", "Kaka")



}
