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

package uk.gov.hmrc.ct

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC8021, AC8023, ACQ8161}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xDirectorsBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.version.Return

class HmrcAccountsApprovalRequiredCalculatorSpec extends WordSpec with Matchers with MockitoSugar {

  import uk.gov.hmrc.ct.version.calculations.ReturnVersionsFixture._

  val retriever = mock[FilingAttributesBoxValueRetriever]

  "HmrcAccountsApprovalRequiredCalculator" should {
    allCoHoOnlyReturnCombinations.zipWithIndex.foreach {
      case (returns, index) =>
        s"return false for CoHo Only filing return set $index" in {
          HmrcAccountsApprovalRequiredCalculatorForTest(returns).calculateApprovalRequired(retriever) shouldBe HmrcAccountsApprovalRequired(false)
        }
    }

    hmrcOnlyFRSSE2008ReturnCombinationsNoUploads.zipWithIndex.foreach {
      case (returns, index) =>
        s"return true for HMRC Only FRSSE2008 filing non upload return set $index" in {
          HmrcAccountsApprovalRequiredCalculatorForTest(returns).calculateApprovalRequired(retriever) shouldBe HmrcAccountsApprovalRequired(true)
        }
    }

    hmrcOnlyFRS10xReturnCombinationsNoUploads.zipWithIndex.foreach {
      case (returns, index) =>
        s"return true for HMRC Only FRS10x filing non upload return set $index" in {
          HmrcAccountsApprovalRequiredCalculatorForTest(returns).calculateApprovalRequired(retriever) shouldBe HmrcAccountsApprovalRequired(true)
        }
    }

    hmrcOnlyUploadAccountsReturnCombinations.zipWithIndex.foreach {
      case (returns, index) =>
        s"return false for HMRC Only filing upload return set $index" in {
          HmrcAccountsApprovalRequiredCalculatorForTest(returns).calculateApprovalRequired(retriever) shouldBe HmrcAccountsApprovalRequired(false)
        }
    }

    jointReturnCombinationsWithSameAccountsForCoHoAndHmrc.zipWithIndex.foreach {
      case (returns, index) =>
        s"return false for joint filing same accounts for both return set $index" in {
          HmrcAccountsApprovalRequiredCalculatorForTest(returns).calculateApprovalRequired(retriever) shouldBe HmrcAccountsApprovalRequired(false)
        }
    }

    allJointReturnCombinationsWithDifferentAccountsBetweenHmrcAndCoHo.zipWithIndex.foreach {
      case (returns, index) =>
        s"return false for joint filing different accounts for both return set $index" in {
          HmrcAccountsApprovalRequiredCalculatorForTest(returns).calculateApprovalRequired(retriever) shouldBe HmrcAccountsApprovalRequired(true)
        }
    }

    "return false for FRS102 and answered true to file P&l and directors report to CoHo" in {
      val boxRetriever = mock[Frs10xBoxRetriever]
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))
      HmrcAccountsApprovalRequiredCalculatorForTest(jointStatutoryFRS102V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(false)
      HmrcAccountsApprovalRequiredCalculatorForTest(jointAbridgedFRS102V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(false)
    }

    "return true for FRS102 and answered true to file P&l and false directors report to CoHo" in {
      val boxRetriever = mock[Frs10xBoxRetriever]
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))
      HmrcAccountsApprovalRequiredCalculatorForTest(jointStatutoryFRS102V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(true)
      HmrcAccountsApprovalRequiredCalculatorForTest(jointAbridgedFRS102V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(true)
    }

    "return true for FRS102 and answered false to file P&l and true directors report to CoHo" in {
      val boxRetriever = mock[Frs10xBoxRetriever]
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))
      HmrcAccountsApprovalRequiredCalculatorForTest(jointStatutoryFRS102V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(true)
      HmrcAccountsApprovalRequiredCalculatorForTest(jointAbridgedFRS102V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(true)
    }

    "return true for FRS102 and answered false to file P&l and directors report to CoHo" in {
      val boxRetriever = mock[Frs10xBoxRetriever]
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))
      HmrcAccountsApprovalRequiredCalculatorForTest(jointStatutoryFRS102V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(true)
      HmrcAccountsApprovalRequiredCalculatorForTest(jointAbridgedFRS102V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(true)
    }

    "return false for joint filing FRS105 and answered false to file directors report to HMRC and true to P&L" in {
      val boxRetriever = mock[Frs10xBoxRetriever]
      when(boxRetriever.ac8021()).thenReturn(AC8021(None))
      when(boxRetriever.ac8023()).thenReturn(AC8023(Some(false)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))
      HmrcAccountsApprovalRequiredCalculatorForTest(jointMicroFRS105V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(false)
    }

    "return true for FRS105 and answered false to file directors report to CoHo and true to P&L" in {
      val boxRetriever = mock[Frs10xBoxRetriever]
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
      when(boxRetriever.ac8023()).thenReturn(AC8023(Some(true)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))
      HmrcAccountsApprovalRequiredCalculatorForTest(jointMicroFRS105V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(true)
    }

    "return true for FRS105 and answered false to file P&L to CoHo and true to directors report" in {
      val boxRetriever = mock[Frs10xBoxRetriever]
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
      when(boxRetriever.ac8023()).thenReturn(AC8023(Some(true)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))
      HmrcAccountsApprovalRequiredCalculatorForTest(jointMicroFRS105V3Returns).calculateApprovalRequired(boxRetriever) shouldBe HmrcAccountsApprovalRequired(true)
    }
  }
}

case class HmrcAccountsApprovalRequiredCalculatorForTest(returns: Set[Return]) extends HmrcAccountsApprovalRequiredCalculator {
  override def doCalculation[A <: BoxRetriever](boxRetriever: A): Set[Return] = returns
}

trait Frs10xBoxRetriever extends FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever
