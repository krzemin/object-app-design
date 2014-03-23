class GenericFactory {

  private var instances = Map.empty[(String, Seq[Any]), AnyRef]

  def createObject(typeName: String,
                   isSingleton: Boolean,
                   params: Any*) {
    if(isSingleton) {
      instances((typeName, params)) match {
        case Some(inst) => inst
        case None => ???
      }
    } else {
      println("n = " + Class.forName(typeName).getConstructors.size)
      println("params = " + params.size)
      println("cons = " + Class.forName(typeName).getConstructors.head.getParameterTypes.size)
      Class.forName(typeName).getConstructors.head.newInstance(params.asInstanceOf[Seq[AnyRef]]).asInstanceOf[AnyRef]
    }
  }

}
