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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.relatedPartyTransactions

import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class RelatedPartyTransactionsSpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

  val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever]

  "Directors" should {
    "validate successfully when no validation errors are present" in {
      val transactions = RelatedPartyTransactions(ac7806 = AC7806(None))

      transactions.validate(mockBoxRetriever) shouldBe empty
    }

    "mandatory errors when mandatory fields are missing" in {
      val transaction = RelatedPartyTransaction(
        uuid="uuid",
        ac7801 = AC7801(None),
        ac7802 = AC7802(None),
        ac7803 = AC7803(None),
        ac7804 = AC7804(None),
        ac7805 = AC7805(None)
      )
      val transactions = RelatedPartyTransactions(transactions = List(transaction), ac7806 = AC7806(None))

      transactions.validate(mockBoxRetriever) shouldBe Set(
        CtValidation(Some("AC7801"),"error.AC7801.0.required",None),
        CtValidation(Some("AC7802"),"error.AC7802.0.required",None),
        CtValidation(Some("AC7803"),"error.AC7803.0.required",None)
      )
    }
  }
}
