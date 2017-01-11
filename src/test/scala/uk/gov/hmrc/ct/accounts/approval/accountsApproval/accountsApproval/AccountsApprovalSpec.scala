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

package uk.gov.hmrc.ct.accounts.approval.accountsApproval.accountsApproval

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.{CoHoAccountsApprovalRequired, HmrcAccountsApprovalRequired}
import uk.gov.hmrc.ct.accounts.approval.boxes._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC4, TestAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AccountsApprovalSpec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[TestAccountsRetriever]

  val Date = Some(new LocalDate())
  val True = Some(true)
  val Approver = Some("approver")

  private def approvalRequiredFalse(emptyApproval: AccountsApproval, builder: (List[AC199A], List[AC8092], AC8091, AC198A) => AccountsApproval, enabled: (Boolean) => _, boxId: String): Unit = {
    "approval required is false" when {

      "return no error for AC8091 errors if approval is disabled and empty" in {
        enabled(false)
        emptyApproval.validate(boxRetriever) shouldBe Set.empty
      }

      "return cannot exist error for populated approval is disabled" in {
        enabled(false)
        val aa = builder(List(), List(AC8092(Approver)), AC8091(None), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
      }

      "return cannot exist error for ac198A errors if approval disabled" in {
        enabled(false)
        val aa = builder(List(), List(AC8092(Approver)), AC8091(True), AC198A(None))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
      }

      "return cannot exist global error for no approvers when approval disabled" in {
        enabled(false)
        val aa = builder(List(), List(), AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
      }

      "return cannot exist error when more than 12 approvers when approval disabled" in {
        enabled(false)
        val approvers = for (i <- (1 to 13).toList) yield AC199A("approver")
        val aa = builder(approvers, List(), AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
      }

      "return cannot exist error for more than 12 other approvers when approval disabled" in {
        enabled(false)
        val otherApprovers = for (i <- (1 to 13).toList) yield AC8092(Approver)
        val aa = builder(List(), otherApprovers, AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
      }

      "return cannot exist for invalid approver when approval disabled" in {
        enabled(false)
        val aa = builder(List(AC199A("^"), AC199A("^^")), List(), AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
      }

      "return cannot exist for invalid other approver when approval disabled" in {
        enabled(false)
        val aa = builder(List(), List(AC8092(Some("^")), AC8092(Some("^"))), AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
      }
    }
  }

  private def approvalRequiredTrue(emptyApproval: AccountsApproval, builder: (List[AC199A], List[AC8092], AC8091, AC198A) => AccountsApproval, enabled: (Boolean) => _, boxId: String): Unit = {

    "approval required is true" when {

      "have display condition true when HmrcAccountsApprovalRequired is true" in {
        enabled(true)
        emptyApproval.approvalEnabled(boxRetriever) shouldBe true
      }

      "return no error at least one approver, approved, and valid date" in {
        enabled(true)
        val aa = builder(List(), List(AC8092(Approver)), AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe empty
      }

      "return error with boxId for AC8091 errors" in {
        enabled(true)
        val aa = builder(List(), List(AC8092(Approver)), AC8091(None), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), "error.AC8091.required"))
      }

      "return error with boxId for ac198A errors" in {
        enabled(true)
        val aa = builder(List(), List(AC8092(Approver)), AC8091(True), AC198A(None))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId),"error.AC198A.required"))
      }

      "return global error when no approvers" in {
        enabled(true)
        val aa = builder(List(), List(), AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(None, s"error.$boxId.atLeast1"))
      }

      "return global error when more than 12 approvers" in {
        enabled(true)
        val approvers = for (i <- (1 to 13).toList) yield AC199A("approver")
        val aa = builder(approvers, List(), AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(None, s"error.$boxId.approvers.atMost12"))
      }

      "return global error when more than 12 other approvers" in {
        enabled(true)
        val otherApprovers = for (i <- (1 to 13).toList) yield AC8092(Approver)
        val aa = builder(List(), otherApprovers, AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(None, s"error.$boxId.otherApprovers.atMost12"))
      }

      "return error when invalid approver" in {
        enabled(true)
        val aa = builder(List(AC199A("^"), AC199A("^^")), List(), AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId),"error.simpleList.AC199A.0.regexFailure"),
                                               CtValidation(Some(boxId),"error.simpleList.AC199A.1.regexFailure"))
      }

      "return error when invalid other approver" in {
        enabled(true)
        val aa = builder(List(), List(AC8092(Some("^")), AC8092(Some("^"))), AC8091(True), AC198A(Date))

        aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId),"error.simpleList.AC8092.0.regexFailure"),
                                               CtValidation(Some(boxId),"error.simpleList.AC8092.1.regexFailure"))
      }
    }
  }

  private def approvalPopulated(builder: (List[AC199A], List[AC8092], AC8091, AC198A) => AccountsApproval): Unit = {

    "return as populated if entry in AC199A" in {
      val aa = builder(List(AC199A("Director")), List.empty, AC8091(None), AC198A(None))

      aa.anyValuesPopulated shouldBe true
    }

    "return as populated if entry in AC8092" in {
      val aa = builder(List.empty, List(AC8092(Some("Approver"))), AC8091(None), AC198A(None))

      aa.anyValuesPopulated shouldBe true
    }

    "return as populated if entry in AC8091" in {
      val aa = builder(List.empty, List.empty, AC8091(Some(true)), AC198A(None))

      aa.anyValuesPopulated shouldBe true
    }

    "return as populated if entry in AC198A" in {
      val aa = builder(List.empty, List.empty, AC8091(None), AC198A(Date))

      aa.anyValuesPopulated shouldBe true
    }

    "return as not populated if no values" in {
      val aa = builder(List.empty, List.empty, AC8091(None), AC198A(None))

      aa.anyValuesPopulated shouldBe false
    }
  }

  "HmrcAccountsApproval" should {
    val emptyApproval = HmrcAccountsApproval(List.empty, List.empty, AC8091(None), AC198A(None))
    when(boxRetriever.ac4()).thenReturn(AC4(Date.get.minusDays(1)))
    val enabled = {
      (b: Boolean) =>
        when(boxRetriever.hmrcAccountsApprovalRequired()).thenReturn(HmrcAccountsApprovalRequired(b))
    }

    approvalRequiredFalse(emptyApproval, HmrcAccountsApproval.apply, enabled, "HmrcAccountsApproval")
    approvalRequiredTrue(emptyApproval, HmrcAccountsApproval.apply, enabled, "HmrcAccountsApproval")
    approvalPopulated(HmrcAccountsApproval.apply)
  }

  "CompaniesHouseAccountsApproval" should {
    val emptyApproval = CompaniesHouseAccountsApproval(List.empty, List.empty, AC8091(None), AC198A(None))
    when(boxRetriever.ac4()).thenReturn(AC4(Date.get.minusDays(1)))
    val enabled = {
      b: Boolean =>
        when(boxRetriever.coHoAccountsApprovalRequired).thenReturn(CoHoAccountsApprovalRequired(b))
    }

    approvalRequiredFalse(emptyApproval, CompaniesHouseAccountsApproval.apply, enabled, "CompaniesHouseAccountsApproval")
    approvalRequiredTrue(emptyApproval, CompaniesHouseAccountsApproval.apply, enabled, "CompaniesHouseAccountsApproval")
    approvalPopulated(CompaniesHouseAccountsApproval.apply)
  }
}
