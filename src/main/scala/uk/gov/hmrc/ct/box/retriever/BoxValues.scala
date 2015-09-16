package uk.gov.hmrc.ct.box.retriever

import java.lang.reflect.{Method, Modifier}

import uk.gov.hmrc.ct.box.CtValue

trait BoxValues[T] {

  def generateValues(retriever: T): Map[String, CtValue[_]] = {
    retrieveBoxIdFunctions(retriever.getClass).map { method =>
      val boxName = method.getReturnType.getSimpleName
      method.invoke(retriever) match {
        case x: CtValue[_] => (boxName -> x)
      }
    }.toMap
  }

  def retrieveBoxIdFunctions(retrieverClass: Class[_]): Seq[Method] = {
    retrieverClass.getMethods
      .filter(retrieveBoxMethod)
  }

  protected def retrieveBoxMethod: (Method) => Boolean = x => isPublic(x) && isAnyRetrieveBoxMethod(x) && hasNoParameters(x) && returnsCatoValue(x)

  protected def retrieveBoxMethod(boxId: String): (Method) => Boolean = x => isPublic(x) && isRetrieveBoxMethod(x, boxId) && hasNoParameters(x) && returnsCatoValue(x)

  protected def hasNoParameters(method: Method): Boolean = method.getParameterTypes.isEmpty

  protected def isAnyRetrieveBoxMethod(method: Method): Boolean = method.getName.startsWith("retrieve")

  protected def isRetrieveBoxMethod(method: Method, boxId: String): Boolean = method.getName.matches("retrieve" + boxId)

  protected def isPublic(method: Method): Boolean = Modifier.isPublic(method.getModifiers)

  protected def returnsCatoValue(method: Method): Boolean = classOf[CtValue[_]].isAssignableFrom(method.getReturnType)
}
