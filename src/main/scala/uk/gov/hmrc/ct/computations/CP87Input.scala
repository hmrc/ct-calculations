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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.ComputationValidatableBox
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP87Input(value: Option[Int]) extends CtBoxIdentifier(name = "First year allowance claimed")  with CtOptionalInteger with Input with ComputationValidatableBox[ComputationsBoxRetriever] with CtTypeConverters{
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    validateZeroOrPositiveInteger(this) ++
      mandatoryIfCompanyIsTrading(boxRetriever,"CP87Input", value) ++
      firstYearAllowanceNotGreaterThanMaxFYA(boxRetriever)
  }

  private def firstYearAllowanceNotGreaterThanMaxFYA(retriever: ComputationsBoxRetriever): Set[CtValidation] = {

    val expenditureQualifyingForFirstYearAllowanceInput = retriever.cp81()
    val cpAux1 = retriever.cpAux1()

    val maxFYA = expenditureQualifyingForFirstYearAllowanceInput + cpAux1

    value match {
      case Some(fyaClaimed) if fyaClaimed > maxFYA =>
        Set(CtValidation(boxId = Some("CP87Input"), errorMessageKey = "error.CP87Input.firstYearAllowanceClaimExceedsAllowance", args = Some(Seq(maxFYA.toString))))
      case _ =>
        Set()
    }
  }
}

object CP87Input {

  def apply(int: Int): CP87Input = CP87Input(Some(int))

}
