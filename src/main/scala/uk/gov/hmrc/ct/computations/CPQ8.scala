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

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.AllowancesQuestionsValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ8(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did the company cease trading?")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with AllowancesQuestionsValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val boxId = "CPQ8"
    val companyCars = boxRetriever.cpQ7()
    val machineryOrPlant = boxRetriever.cpQ10()
    val structuresAndBuildings = boxRetriever.cpQ11()

    val validateMandatory = {
      if (companyCars.isTrue || machineryOrPlant.isTrue || structuresAndBuildings.isTrue) {
        validateBooleanAsMandatory(boxId ,this)
      } else validationSuccess
    }

    if(isSBALive(boxRetriever.cp2())) {
      validateMandatory
    }
    else
      {
        validateAgainstCPQ7(boxRetriever, boxId, value)
        validateMandatory
      }
  }

}