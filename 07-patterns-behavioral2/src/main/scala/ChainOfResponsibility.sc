trait Handler {
  def handle(request: String)
}

abstract class AbstractHandler extends Handler {
  var nextHandler: AbstractHandler = null

  def addHandler(h: AbstractHandler): AbstractHandler = {
    if(nextHandler == null) {
      nextHandler = h
      nextHandler
    } else {
      nextHandler.addHandler(h)
    }
  }
}
class ArchiveHandler extends AbstractHandler {

  var requests: List[String] = Nil

  def handle(request: String) {
    requests :+= request
    if(nextHandler != null) {
      nextHandler.handle(request)
    }
  }
}

class PraiseHandler extends AbstractHandler {

  def isPraise(request: String) = request.startsWith("praise")

  def handle(request: String) {
    if(isPraise(request)) {
      println(s"praise go to boos: $request")
    } else {
      nextHandler.handle(request)
    }
  }
}

class ComplainHandler extends AbstractHandler {

  def isComplain(request: String) = request.startsWith("complain")

  def handle(request: String) {
    if(isComplain(request)) {
      println(s"complain go law department: $request")
    } else {
      nextHandler.handle(request)
    }
  }
}

class OrderHandler extends AbstractHandler {

  def isOrder(request: String) = request.startsWith("order")

  def handle(request: String) {
    if(isOrder(request)) {
      println(s"order go sales department: $request")
    } else {
      nextHandler.handle(request)
    }
  }
}

class OtherHandler extends AbstractHandler {
  def handle(request: String) {
    println(s"other go to marketing: $request")
  }
}

val archive = new ArchiveHandler
val chain = archive

chain.addHandler(new PraiseHandler)
     .addHandler(new ComplainHandler)
     .addHandler(new OrderHandler)
     .addHandler(new OtherHandler)

val requests = List(
  "complain 1 2 3",
  "order ka ka ka",
  "some other message",
  "praise to boss",
  "order of asphalt"
)
requests.foreach { req =>
  chain.handle(req)
}







val archived = archive.requests

