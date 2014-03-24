class GenericFactory {

  private var instances = Map.empty[(String, Seq[Any]), AnyRef]

  def createObject(typeName: String,
                   isSingleton: Boolean,
                   params: Any*): AnyRef = {
    if(isSingleton) {
      instances.get((typeName, params)) match {
        case Some(inst) => inst
        case None =>
          val inst = instantiate(typeName, params: _*)
          instances += (typeName, params) -> inst
          inst
      }
    } else {
      instantiate(typeName, params: _*)
    }
  }

  private def instantiate(typeName: String, params: Any*): AnyRef = {
    Class.forName(typeName)
      .getConstructors.head
      .newInstance(params.asInstanceOf[Seq[AnyRef]]: _*)
      .asInstanceOf[AnyRef]
  }

}
