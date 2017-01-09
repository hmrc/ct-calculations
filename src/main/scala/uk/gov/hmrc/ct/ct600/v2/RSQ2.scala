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

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class RSQ2(inputValue: Option[Boolean], defaultValue: Option[Boolean]) extends CtBoxIdentifier
  with CtOptionalBoolean with InputWithDefault[Boolean] with ValidatableBox[BoxRetriever] with Validators {

  override def validate(boxRetriever: BoxRetriever): Set[CtValidation] = {
    boxRetriever match {
      case compsRetriever: ComputationsBoxRetriever => {
        collectErrors(
          cannotExistIf(CP287GreaterThenZeroAndHaveInputValue(compsRetriever)),
          requiredIf(CP287NotExistsAndNoInputValue(compsRetriever))
        )
      }
      case _ => validateAsMandatory(this) //Charities may not have Computations, but still need to validate as mandatory
    }

  }

  private def CP287GreaterThenZeroAndHaveInputValue(retriever: ComputationsBoxRetriever)() =
    retriever.cp287().value.exists(_ > 0) && inputValue.isDefined

  private def CP287NotExistsAndNoInputValue(retriever: ComputationsBoxRetriever)() =
    !retriever.cp287().value.exists(_ > 0) && inputValue.isEmpty
}

object RSQ2 {

  def apply(inputValue: Option[Boolean]): RSQ2 = RSQ2(inputValue, None)
}
