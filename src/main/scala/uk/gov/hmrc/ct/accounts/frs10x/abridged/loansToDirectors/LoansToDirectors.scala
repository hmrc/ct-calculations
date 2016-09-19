/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.loansToDirectors

import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class LoansToDirectors(loans: List[LoanToDirector] = List.empty, ac7501: AC7501) extends CtBoxIdentifier(name = "Loans To Directors")
  with CtValue[LoansToDirectors]
  with Input
  with ValidatableBox[AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def value = this

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = Set.empty
}

case class LoanToDirector(uuid: String,
                          ac304A: AC304A,
                          ac305A: AC305A,
                          ac306A: AC306A,
                          ac307A: AC307A,
                          ac308A: AC308A,
                          ac309A: AC309A
                         ) extends CtBoxIdentifier(name = "LoanToDirector")
  with ValidatableBox[AbridgedAccountsBoxRetriever]
  with Input
  with CtValue[LoanToDirector] {

  override def value = this

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] =
    collectErrors(
      //TODO Actual Coding
    )
}
