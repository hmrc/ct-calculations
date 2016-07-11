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
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever


case class DirectorsDetails(directorsDetails: List[DirectorDetails] = List.empty) extends CtBoxIdentifier(name = "Directors Details.") with CtValue[List[DirectorDetails]] with Input with ValidatableBox[Frs10xAccountsBoxRetriever] {

  override def value = directorsDetails

  override def asBoxString = DirectorsDetailsFormatter.asBoxString(this)

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
    validateDirectorRequired() ++
    validateAtMost12Directors() ++
    directorsDetails.foldRight(Set[CtValidation]())((dd, tail) => dd.validate(boxRetriever) ++ tail)
  }

  def +(other: DirectorsDetails): DirectorsDetails = new DirectorsDetails(directorsDetails ++ other.directorsDetails)

  def validateDirectorRequired(): Set[CtValidation] = {
    directorsDetails match {
      case Nil => Set(CtValidation(Some("AC8001"), "error.AC8001.required", None))
      case _ => Set.empty
    }
  }

  def validateAtMost12Directors(): Set[CtValidation] = {
    directorsDetails.size match {
      case n if n > 12 => Set(CtValidation(Some("AC8001"), "error.AC8001.atMost12", None))
      case _ => Set.empty
    }
  }
}

case class DirectorDetails (id: String, AC8001: String) extends ValidatableBox[Frs10xAccountsBoxRetriever] {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] =
    validateStringByLength("AC8001", AC8001, 1, 40) ++ validateStringByRegex("AC8001", AC8001, validCoHoCharacters)
}
