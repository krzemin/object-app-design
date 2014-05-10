import akka.actor.{Props, ActorSystem}

object CommandMain extends App {
  import CommandActors._

  val system = ActorSystem()
  val queueRef = system.actorOf(Props[CmdQueueActor])
  val prodRef = system.actorOf(Props(new Producer(queueRef, 10)))

  Thread.sleep(500)

  val cons1Ref = system.actorOf(Props(new Consumer(1, queueRef)))
  val cons2Ref = system.actorOf(Props(new Consumer(2, queueRef)))

  sys.addShutdownHook(system.shutdown())

}
