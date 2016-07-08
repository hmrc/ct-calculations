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

package uk.gov.hmrc.ct.accounts.frs10x

import uk.gov.hmrc.ct.accounts.frs10x.formats.DirectorsDetailsFormatter
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._


case class DirectorsDetails(directorsDetails: List[DirectorDetails] = List.empty) extends CtBoxIdentifier(name = "Loans to participators.") with CtValue[List[DirectorDetails]] with Input with ValidatableBox[Frs10xAccountsBoxRetriever] {

  def +(other: DirectorsDetails): DirectorsDetails = new DirectorsDetails(directorsDetails ++ other.directorsDetails)

  override def value = directorsDetails

  override def asBoxString = DirectorsDetailsFormatter.asBoxString(this)

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
    directorsDetails.foldRight(Set[CtValidation]())((dd, tail) => dd.validate(boxRetriever) ++ tail)
  }
//
//  def validateLoanRequired(boxRetriever: CT600ABoxRetriever): Set[CtValidation] = {
//    boxRetriever.retrieveLPQ03().value.getOrElse(false) match {
//      case true if loans.isEmpty => Set(CtValidation(Some("LoansToParticipators"), "error.loan.required", None))
//      case _ => Set.empty
//    }
//  }
}

case class DirectorDetails ( id: String, AC8001: String) extends ValidatableBox[Frs10xAccountsBoxRetriever] {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] =
    validateStringByLength(id, AC8001, 1, 40) ++ validateStringByRegex(id, AC8001, validCoHoCharacters)
}
