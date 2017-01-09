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

package uk.gov.hmrc.ct.computations.Validators

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations.CP252
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait ComputationValidatableBox[T <: BoxRetriever] extends ValidatableBox[T] {

  def mandatoryIfCompanyIsTrading(retriever: ComputationsBoxRetriever, boxId: String, value: Option[Int]) =
    (retriever.cpQ8().value, value) match {
      case (Some(false), None) => Set(CtValidation(boxId = Some(boxId), errorMessageKey = s"error.$boxId.fieldMustHaveValueIfTrading"))
      case _ => Set()
    }

  def environmentFriendlyExpenditureCannotExceedRelevantFYAExpenditure(retriever: ComputationsBoxRetriever, value: CP252) = {
    val relevantFYAExpenditure = retriever.cp79().orZero
    val relevantFYAExpenditureOnEnvironmentFriendly = value.orZero

    if(relevantFYAExpenditureOnEnvironmentFriendly > relevantFYAExpenditure) {
      Set(CtValidation(boxId = Some("CP252"), errorMessageKey = s"error.CP252.exceedsRelevantFYAExpenditure"))
    } else {
      Set()
    }
  }

}
