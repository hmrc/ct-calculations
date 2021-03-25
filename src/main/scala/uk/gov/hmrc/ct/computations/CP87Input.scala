/*
 * Copyright 2021 HM Revenue & Customs
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
    collectErrors(
      validateZeroOrPositiveInteger(this),
      cannotExistErrorIf(hasValue && boxRetriever.cpQ8().isTrue),
      mandatoryIfCompanyIsTrading(boxRetriever,"CP87Input", value),
      firstYearAllowanceNotGreaterThanMaxFYA(boxRetriever)
    )
  }

  private def firstYearAllowanceNotGreaterThanMaxFYA(retriever: ComputationsBoxRetriever): Set[CtValidation] = {

    val expenditureQualifyingForFirstYearAllowanceInput: CP81 = retriever.cp81()
    val expenditureQualifyingForFYAValue = expenditureQualifyingForFirstYearAllowanceInput.value

    value match {
      case Some(fyaClaimed) if fyaClaimed > expenditureQualifyingForFirstYearAllowanceInput =>
        Set(CtValidation(boxId = Some("CP87Input"), errorMessageKey = "error.CP87Input.firstYearAllowanceClaimExceedsAllowance", args = Some(Seq(expenditureQualifyingForFYAValue.toString))))
      case _ =>
        Set()
    }
  }
}

object CP87Input {

  def apply(int: Int): CP87Input = CP87Input(Some(int))

}
