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

package uk.gov.hmrc.ct.accounts.frs102.boxes.accountsApproval


import org.joda.time.LocalDate
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AC4, AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

trait AccountsApprovalFixture extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  self: MockFrs102AccountsRetriever =>

  def setUpDisabledMocks(): Unit = Unit

  val Date = Some(new LocalDate())
  val True = Some(true)
  val Approver = Some("approver")

  before {
    Mockito.when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate()))
  }

  def testAccountsApproval(boxId: String, builder: (List[AC199A], List[AC8092], AC8091, AC198A) => AccountsApproval) {

    "return no error at least one approver, approved, and valid date" in {
      setUpMocks()

      val aa = builder(List(), List(AC8092(Approver)), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe empty
    }

    "return error with boxId for AC8091 errors" in {
      setUpMocks()
      val aa = builder(List(), List(AC8092(Approver)), AC8091(None), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId),"error.AC8091.required"))
    }

    "return no error for AC8091 errors if approval is disabled" in {
      setUpDisabledMocks()
      val aa = builder(List(), List(AC8092(Approver)), AC8091(None), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set.empty
    }

    "return error with boxId for ac198A errors" in {
      setUpMocks()
      val aa = builder(List(), List(AC8092(Approver)), AC8091(True), AC198A(None))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId),"error.AC198A.required"))
    }

    "return no error for ac198A errors if approval disabled" in {
      setUpDisabledMocks()
      val aa = builder(List(), List(AC8092(Approver)), AC8091(True), AC198A(None))

      aa.validate(boxRetriever) shouldBe Set.empty
    }

    "return global error when no approvers" in {
      setUpMocks()
      val aa = builder(List(), List(), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(None, s"error.$boxId.atLeast1"))
    }

    "return no global error for no approvers when approval disabled" in {
      setUpDisabledMocks()
      val aa = builder(List(), List(), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set.empty
    }

    "return global error when more than 12 approvers" in {
      setUpMocks()
      val approvers = for (i <- (1 to 13).toList) yield AC199A("approver")
      val aa = builder(approvers, List(), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(None, s"error.$boxId.approvers.atMost12"))
    }

    "return no global error when more than 12 approvers when approval disabled" in {
      setUpDisabledMocks()
      val approvers = for (i <- (1 to 13).toList) yield AC199A("approver")
      val aa = builder(approvers, List(), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set.empty
    }

    "return global error when more than 12 other approvers" in {
      setUpMocks()
      val otherApprovers = for (i <- (1 to 13).toList) yield AC8092(Approver)
      val aa = builder(List(), otherApprovers, AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(None, s"error.$boxId.otherApprovers.atMost12"))
    }

    "return no global error for more than 12 other approvers when approval disabled" in {
      setUpDisabledMocks()
      val otherApprovers = for (i <- (1 to 13).toList) yield AC8092(Approver)
      val aa = builder(List(), otherApprovers, AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set.empty
    }

    "return error when invalid approver" in {
      setUpMocks()
      val aa = builder(List(AC199A("^"), AC199A("^^")), List(), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId),"error.simpleList.AC199A.0.regexFailure"),
                                              CtValidation(Some(boxId),"error.simpleList.AC199A.1.regexFailure"))
    }

    "return no error for invalid approver when approval disabled" in {
      setUpDisabledMocks()
      val aa = builder(List(AC199A("^"), AC199A("^^")), List(), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set.empty
    }

    "return error when invalid other approver" in {
      setUpMocks()
      val aa = builder(List(), List(AC8092(Some("^")), AC8092(Some("^"))), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId),"error.simpleList.AC8092.0.regexFailure"),
                                              CtValidation(Some(boxId),"error.simpleList.AC8092.1.regexFailure"))
    }

    "return no error for invalid other approver when approval disabled" in {
      setUpDisabledMocks()
      val aa = builder(List(), List(AC8092(Some("^")), AC8092(Some("^"))), AC8091(True), AC198A(Date))

      aa.validate(boxRetriever) shouldBe Set.empty
    }

    "return as populated if entry in AC199A" in {
      setUpMocks()
      val aa = builder(List(AC199A("Director")), List.empty, AC8091(None), AC198A(None))

      aa.anyValuesPopulated shouldBe true
    }

    "return as populated if entry in AC8092" in {
      setUpMocks()
      val aa = builder(List.empty, List(AC8092(Some("Approver"))), AC8091(None), AC198A(None))

      aa.anyValuesPopulated shouldBe true
    }

    "return as populated if entry in AC8091" in {
      setUpMocks()
      val aa = builder(List.empty, List.empty, AC8091(Some(true)), AC198A(None))

      aa.anyValuesPopulated shouldBe true
    }

    "return as populated if entry in AC198A" in {
      setUpMocks()
      val aa = builder(List.empty, List.empty, AC8091(None), AC198A(Date))

      aa.anyValuesPopulated shouldBe true
    }

    "return as not populated if no values" in {
      setUpMocks()
      val aa = builder(List.empty, List.empty, AC8091(None), AC198A(None))

      aa.anyValuesPopulated shouldBe false
    }

  }

}
