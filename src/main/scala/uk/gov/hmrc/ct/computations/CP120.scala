/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, CtValidation, Input, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP120(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did you have Eat Out to Help Out Scheme") with CtOptionalBoolean with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val apStartDate = boxRetriever.cp1().value
    val apEndDate = boxRetriever.cp2().value

    def validateAsMandatoryIfInDate() = {
      if(covidSupport.doesPeriodCoverEotho(apStartDate, apEndDate) && !boxRetriever.acq8999a().value.getOrElse(false)){
        validateAsMandatory(this)()
      } else {
        validationSuccess
      }
    }

    collectErrors(validateAsMandatoryIfInDate)
  }
}
