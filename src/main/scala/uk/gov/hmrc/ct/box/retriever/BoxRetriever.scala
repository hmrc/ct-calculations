

package uk.gov.hmrc.ct.box.retriever

import uk.gov.hmrc.ct.box._

trait BoxRetriever {

  def generateValues: Map[String, CtValue[_]]

  def validateValues(values: Map[String, CtValue[_]]): Set[CtValidation] = {
    var validationErrors = Set[CtValidation]()

    values.foreach {
      case (_, box: ValidatableBox[_]) =>
        box.asInstanceOf[ValidatableBox[BoxRetriever]].validate(this) match {
          case errors if errors.nonEmpty => validationErrors ++= errors
          case _ =>
        }
      case _ =>
    }

    validationErrors
  }


}

object BoxRetriever {
  def anyHaveValue(boxes: OptionalCtValue[_]*): Boolean = boxes.exists(_.hasValue)
}
