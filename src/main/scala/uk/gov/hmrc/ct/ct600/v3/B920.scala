/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever


case class B920(value: String) extends CtBoxIdentifier("bank/BS name")
with CtString with Input with ValidatableBox[CT600BoxRetriever] {

  def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    val allorNoneGroup:Set[CtString] = Set(
      boxRetriever.retrieveB920(),
      boxRetriever.retrieveB925(),
      boxRetriever.retrieveB930(),
      boxRetriever.retrieveB935()
    )
    validateAllFilledOrEmptyStrings("B920",allorNoneGroup) ++
      validateStringByLength("B920", this, 2, 56) ++
      validateStringByRegex("B920", this, validNonForeignLessRestrictiveCharacters)
  }
}
