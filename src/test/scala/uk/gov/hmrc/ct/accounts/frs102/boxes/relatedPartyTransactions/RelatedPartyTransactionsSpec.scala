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

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes.AC7800
import uk.gov.hmrc.ct.accounts.frs102.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC205, AC206}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class RelatedPartyTransactionsSpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfterEach {
  import RelatedPartyTransactionsMockSetup._

  val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever]

  val validTransaction = RelatedPartyTransaction(
    uuid = "uuid",
    ac7801 = AC7801(Some(true)),
    ac299A = AC299A(Some("blah")),
    ac300A = AC300A(Some("blah")),
    ac301A = AC301A(Some("blah")),
    ac302A = AC302A(None),
    ac303A = AC303A(None)
  )
  
  "RelatedPartyTransactions" should {
    "validate successfully when no validation errors are present" in {
      setupDefaults(mockBoxRetriever)

      val transactions = RelatedPartyTransactions(transactions = List(validTransaction), ac7806 = AC7806(None))

      transactions.validate(mockBoxRetriever) shouldBe empty
    }

    "return error when there are errors but AC7800 not set to true" in {
      setupDefaults(mockBoxRetriever)
      when(mockBoxRetriever.ac7800()).thenReturn(AC7800(None))

      val transaction = RelatedPartyTransaction(
        uuid = "uuid",
        ac7801 = AC7801(None),
        ac299A = AC299A(None),
        ac300A = AC300A(None),
        ac301A = AC301A(None),
        ac302A = AC302A(None),
        ac303A = AC303A(None)
      )
      val transactions = RelatedPartyTransactions(transactions = List(transaction), ac7806 = AC7806(None))

      transactions.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("RelatedPartyTransactions"), "error.RelatedPartyTransactions.cannot.exist"))
    }

    "errors against correct transaction and contextualised #1" in {
      setupDefaults(mockBoxRetriever)

      val transaction = RelatedPartyTransaction(
        uuid = "uuid",
        ac7801 = AC7801(None),
        ac299A = AC299A(None),
        ac300A = AC300A(None),
        ac301A = AC301A(None),
        ac302A = AC302A(None),
        ac303A = AC303A(None)
      )
      val transactions = RelatedPartyTransactions(transactions = List(transaction), ac7806 = AC7806(Some("^^")))

      transactions.validate(mockBoxRetriever) shouldBe Set(
        CtValidation(Some("RelatedPartyTransactions"), "error.compoundList.transactions.0.AC7801.required", None),
        CtValidation(Some("RelatedPartyTransactions"), "error.compoundList.transactions.0.AC299A.required", None),
        CtValidation(Some("RelatedPartyTransactions"), "error.compoundList.transactions.0.AC300A.required", None),
        CtValidation(Some("RelatedPartyTransactions"), "error.compoundList.transactions.0.AC301A.required", None),
        CtValidation(Some("RelatedPartyTransactions"), "error.AC7806.regexFailure",Some(List("^")))
      )
    }

    "errors against correct transaction and contextualised #2" in {
      setupDefaults(mockBoxRetriever)

      val transaction2 = RelatedPartyTransaction(
        uuid = "uuid",
        ac7801 = AC7801(Some(true)),
        ac299A = AC299A(Some("blah")),
        ac300A = AC300A(Some("blah")),
        ac301A = AC301A(Some("blah")),
        ac302A = AC302A(Some(-99)),
        ac303A = AC303A(Some(-99))
      )
      val transactions = RelatedPartyTransactions(transactions = List(validTransaction , transaction2), ac7806 = AC7806(None))

      transactions.validate(mockBoxRetriever) shouldBe Set(
        CtValidation(Some("RelatedPartyTransactions"),"error.compoundList.transactions.1.AC302A.below.min", Some(List("0", "99999999"))),
        CtValidation(Some("RelatedPartyTransactions"),"error.compoundList.transactions.1.AC303A.below.min", Some(List("0", "99999999")))
      )
    }

    "range error when no transactions" in {
      setupDefaults(mockBoxRetriever)

      val transactions = RelatedPartyTransactions(transactions = List.empty, ac7806 = AC7806(None))

      transactions.validate(mockBoxRetriever) shouldBe Set(CtValidation(None,"error.RelatedPartyTransactions.atLeast1",None))
    }

    "range error when too many transactions" in {
      setupDefaults(mockBoxRetriever)

      val transactions = RelatedPartyTransactions(transactions = List.tabulate(20)(index => validTransaction), ac7806 = AC7806(None))
      transactions.validate(mockBoxRetriever) shouldBe empty

      val tooManyTransactions = RelatedPartyTransactions(transactions = List.tabulate(21)(index => validTransaction), ac7806 = AC7806(None))
      tooManyTransactions.validate(mockBoxRetriever) shouldBe Set(CtValidation(None,"error.RelatedPartyTransactions.atMost20",None))
    }
  }
}

object RelatedPartyTransactionsMockSetup extends MockitoSugar {

  def setupDefaults(mockBoxRetriever: AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever) = {
    // previous POA responses
    when(mockBoxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2014, 4, 6))))
    when(mockBoxRetriever.ac206()).thenReturn(AC206(Some(new LocalDate(2015, 4, 5))))

    when(mockBoxRetriever.ac7800()).thenReturn(AC7800(Some(true)))
  }
}
