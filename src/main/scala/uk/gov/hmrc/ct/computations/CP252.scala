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


case class CP252(value: Option[Int]) extends CtBoxIdentifier("Expenditure on designated environmentally friendly machinery and plant") with CtOptionalInteger with Input with ComputationValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    validateZeroOrPositiveInteger(this) ++
      environmentFriendlyExpenditureCannotExceedRelevantFYAExpenditure(boxRetriever, this) ++
      cannotExistIf(hasValue && boxRetriever.cpQ8().isTrue)
  }
}

object CP252 {

  def apply(value: Int): CP252 = CP252(Some(value))
}
