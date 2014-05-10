import akka.actor.{ActorRef, Actor}
import scala.collection.immutable.Queue
import scala.util.Random

object CommandActors {
  import Command._
  case object Get
  case class Put(cmd: (Command, List[String]))
  case object Finished

  class CmdQueueActor extends Actor {
    var queue = Queue.empty[(Command, List[String])]
    def receive = {
      case Put(cmdargs) => queue :+= cmdargs
      case Get =>
        queue.dequeueOption match {
          case Some((cmdargs, rest)) =>
            sender ! Put(cmdargs)
            queue = rest
          case None =>
            sender ! Finished
        }
    }
  }

  class Producer(queue: ActorRef, toProduce: Int) extends Actor {

    def produceCmd: (Command, List[String]) =
      Random.shuffle((1 to 4).toList).head match {
        case 1 => (new FtpCommand("lfile"), List("ftp://file.com/file1"))
        case 2 => (new HttpCommand("lfile"), List("http://file.com/file1"))
        case 3 => (new RandomFileCommand(4096), List("lfile"))
        case 4 => (new CopyFileCommand, List("srcfile", "targetfile"))
      }

    (1 to toProduce) foreach { _ => queue ! Put(produceCmd) }

    println(s"Producer produced $toProduce elements")

    def receive = {
      case _ =>
    }
  }

  class Consumer(id: Int, queue: ActorRef) extends Actor {
    queue ! Get
    def receive = {
      case Put(cmdargs @ (cmd, args)) =>
        println(s"consumer $id executes $cmdargs")
        cmd.execute(args)
        queue ! Get
      case Finished =>
        println(s"consumer $id finished due to empty queue")
    }
  }
}
