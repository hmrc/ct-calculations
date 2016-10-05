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
import uk.gov.hmrc.ct.accounts.frs10x.abridged.validation.CompoundBoxValidationHelper
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


case class LoansToDirectors(loans: List[LoanToDirector] = List.empty, ac7501: AC7501) extends CtBoxIdentifier(name = "Loans To Directors")
  with CtValue[LoansToDirectors]
  with Input
  with ValidatableBox[AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  import CompoundBoxValidationHelper._

  override def value = this

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    collectErrors (
      // TODO: add this when ready to implement confirm delete of this note
      // cannotExistIf(!boxRetriever.ac7500().orFalse && (loans.nonEmpty || ac7501.value.nonEmpty)),

      failIf(boxRetriever.ac7500().orFalse) {
        collectErrors(
          validateSimpleField(boxRetriever),
          validateLoanRequired(boxRetriever),
          validateAtMost20Loans(boxRetriever),
          validateLoans(boxRetriever)
        )
      }
    )
  }

  def calculateAC309A():LoansToDirectors = {
    this.copy(loans = loans.map(_.calculateAC309A()))
  }

  def validateLoans(boxRetriever: AbridgedAccountsBoxRetriever)(): Set[CtValidation] = {
    val loansErrorList = for ((loan, index) <- loans.zipWithIndex) yield {
      val errors = loan.validate(boxRetriever)
      errors.map(error => contextualiseError("LoansToDirectors", "loans", error, index))
    }
    loansErrorList.flatten.toSet
  }

  def validateLoanRequired(boxRetriever: AbridgedAccountsBoxRetriever)(): Set[CtValidation] = {
    failIf(loans.isEmpty) {
      Set(CtValidation(None, "error.LoansToDirectors.atLeast1", None))
    }
  }

  def validateAtMost20Loans(boxRetriever: AbridgedAccountsBoxRetriever)(): Set[CtValidation] = {
    failIf(loans.length > 20) {
      Set(CtValidation(None, "error.LoansToDirectors.atMost20", None))
    }
  }

  def validateSimpleField(boxRetriever: AbridgedAccountsBoxRetriever)() = {
    ac7501.validate(boxRetriever).map {
      error => error.copy(boxId = Some("LoansToDirectors"))
    }
  }
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

  override def value = throw new NotImplementedError("should never be used")

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] =
    collectErrors(
      () => ac304A.validate(boxRetriever),
      () => ac305A.validate(boxRetriever),
      () => ac306A.validate(boxRetriever),
      () => ac307A.validate(boxRetriever),
      () => ac308A.validate(boxRetriever),
      () => globalValidationForLoan()
    )
  
  def globalValidationForLoan(): Set[CtValidation] = {
    val anyAmountFieldHasAValue = (
      this.ac305A.value orElse 
      this.ac305A.value orElse
      this.ac305A.value
    ).nonEmpty

    if (!anyAmountFieldHasAValue) {
      Set(CtValidation(boxId = None, "error.LoansToDirectors.one.field.required"))
    } else {
      Set.empty
    }
  }

  def calculateAC309A(): LoanToDirector = {
    this.copy(ac309A = AC309A.calculate(this))
  }
}
