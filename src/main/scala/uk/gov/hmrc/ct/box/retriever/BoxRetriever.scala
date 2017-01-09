/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
