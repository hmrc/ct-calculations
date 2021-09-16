/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box.retriever

import uk.gov.hmrc.ct.box.CtValue

import java.lang.reflect.{Method, Modifier}

object BoxValues {

  def generateValues[T <: BoxRetriever](retriever: T): Map[String, CtValue[_]] = {
    boxIdFunctions(retriever.getClass).map { method =>
      val boxName = method.getReturnType.getSimpleName
      method.invoke(retriever) match {
        case x: CtValue[_] => (boxName -> x)
      }
    }.toMap
  }

  def boxIdFunctions(retrieverClass: Class[_]): Seq[Method] = retrieverClass.getMethods.filter(boxMethod)

  protected def boxMethod: (Method) => Boolean = x => isPublic(x) && hasNoParameters(x) && returnsCatoValue(x)

  protected def hasNoParameters(method: Method): Boolean = method.getParameterTypes.isEmpty

  protected def isPublic(method: Method): Boolean = Modifier.isPublic(method.getModifiers)

  protected def returnsCatoValue(method: Method): Boolean = classOf[CtValue[_]].isAssignableFrom(method.getReturnType)
}
