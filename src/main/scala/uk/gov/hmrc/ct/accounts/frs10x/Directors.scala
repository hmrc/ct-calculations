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

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.accounts.frs10x.formats.DirectorsFormatter
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.validation.DirectorsReportEnabled
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class Directors(directors: List[Director] = List.empty) extends CtBoxIdentifier(name = "Directors.")
  with CtValue[List[Director]]
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with DirectorsReportEnabled {

  override def value = directors

  override def asBoxString = DirectorsFormatter.asBoxString(this)

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    validateDirectorRequired(boxRetriever ) ++ validateAtMost12Directors() ++ validateDirectorsUnique() ++
    directors.foldRight(Set[CtValidation]())((dd, tail) => dd.validate(boxRetriever) ++ tail)
  }

  def validateDirectorRequired(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    failIf (directorsReportEnabled(boxRetriever) && directors.isEmpty) {
      Set(CtValidation(Some("AC8001"), "error.Directors.AC8001.global.atLeast1", None))
    }
  }

  def validateAtMost12Directors(): Set[CtValidation] = {
    directors.size match {
      case n if n > 12 => Set(CtValidation(Some("AC8001"), "error.Directors.AC8001.atMost12", None))
      case _ => Set.empty
    }
  }

  def validateDirectorsUnique(): Set[CtValidation] = {

    val uniqueNames = directors.map(_.AC8001).toSet

    directors.size != uniqueNames.size match {
      case true => Set(CtValidation(Some("AC8001"), "error.Directors.AC8001.unique", None))
      case false => Set.empty
    }
  }

}

case class Director(id: String, AC8001: String,
                    appointed: Option[Boolean] = None,
                    resigned: Option[Boolean] = None,
                    appointmentDate: Option[LocalDate] = None,
                    resignationDate: Option[LocalDate] = None) extends ValidatableBox[Frs10xAccountsBoxRetriever] {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] =
    validateStringByLength("AC8001", AC8001, "Directors.AC8001", 1, 40) ++ validateStringByRegex("AC8001", AC8001, "Directors.AC8001", validCoHoCharacters)
}



