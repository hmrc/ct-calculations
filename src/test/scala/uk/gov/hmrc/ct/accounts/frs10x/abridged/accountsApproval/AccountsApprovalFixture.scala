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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.accountsApproval


import org.joda.time.LocalDate
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.AC4
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.{AccountsFreeTextValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class AccountsApprovalFixture extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter
  with MockAbridgedAccountsRetriever with AccountsFreeTextValidationFixture {

  val Date = Some(new LocalDate())
  val True = Some(true)
  val Approver = Some("approver")

  before {
    Mockito.when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate()))
  }

  def testAccountsApproval(builder: (List[AC199A], List[AC8092], AC8091, AC198A) => AccountsApproval) {

    "return no error at least one approver, approved, and valid date" in {

      val aa = builder(List(), List(AC8092(Approver)), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe empty
    }

    "return error with 'AccountsApproval' boxId for AC8091 errors" in {

      val aa = builder(List(), List(AC8092(Approver)), AC8091(None), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(Some("AccountsApproval"),"error.AC8091.required"))
    }

    "return error with 'AccountsApproval' boxId for ac198A errors" in {

      val aa = builder(List(), List(AC8092(Approver)), AC8091(True), AC198A(None))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(Some("AccountsApproval"),"error.AC198A.required"))
    }

    "return global error when no approvers" in {

      val aa = builder(List(), List(), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(None,"error.AccountsApproval.atLeast1"))
    }

    "return global error when more than 12 approvers" in {

      val approvers = for (i <- (1 to 13).toList) yield AC199A("approver")
      val aa = builder(approvers, List(), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(None,"error.AccountsApproval.approvers.atMost12"))
    }

    "return global error when more than 12 other approvers" in {

      val otherApprovers = for (i <- (1 to 13).toList) yield AC8092(Approver)
      val aa = builder(List(), otherApprovers, AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(None,"error.AccountsApproval.otherApprovers.atMost12"))
    }

    "return error when invalid approver" in {

      val aa = builder(List(AC199A("^"), AC199A("^^")), List(), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(Some("AccountsApproval"),"error.simpleList.AC199A.0.regexFailure"),
                                              CtValidation(Some("AccountsApproval"),"error.simpleList.AC199A.1.regexFailure"))
    }

    "return error when invalid other approver" in {

      val aa = builder(List(), List(AC8092(Some("^")), AC8092(Some("^"))), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(Some("AccountsApproval"),"error.simpleList.AC8092.0.regexFailure"),
                                              CtValidation(Some("AccountsApproval"),"error.simpleList.AC8092.1.regexFailure"))
    }
  }

}
