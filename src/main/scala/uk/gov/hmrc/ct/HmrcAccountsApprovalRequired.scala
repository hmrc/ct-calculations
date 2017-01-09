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

import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xDirectorsBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.version.CoHoVersions.{FRS102, FRS105}
import uk.gov.hmrc.ct.version.calculations.ReturnVersionsCalculator
import uk.gov.hmrc.ct.version.{CoHoEquivalent, HmrcAccounts, Return}

case class HmrcAccountsApprovalRequired(value: Boolean) extends CtBoxIdentifier("True if approval required for HMRC version of accounts requires") with CtBoolean

object HmrcAccountsApprovalRequired extends Calculated[HmrcAccountsApprovalRequired, FilingAttributesBoxValueRetriever] with HmrcAccountsApprovalRequiredCalculator {

  override def calculate(boxRetriever: FilingAttributesBoxValueRetriever): HmrcAccountsApprovalRequired = calculateApprovalRequired(boxRetriever)
}

trait HmrcAccountsApprovalRequiredCalculator extends ReturnVersionsCalculator {


  def calculateApprovalRequired(boxRetriever: FilingAttributesBoxValueRetriever): HmrcAccountsApprovalRequired = {

    val returns = doCalculation(boxRetriever)
    val hmrcAccountsReturn = findHmrcAccountsType(returns)
    val coHoAccountReturn = hmrcAccountsReturn.flatMap { hmrc =>
      findMatchingCohoAccounts(returns, hmrc)
    }

    val isFrs102 = isFRS102(hmrcAccountsReturn)
    val isFrs105 = isFRS105(hmrcAccountsReturn)

    val approvalRequired = (hmrcAccountsReturn, coHoAccountReturn, isFrs102, isFrs105) match {
      case (Some(_), None, _, _) => true
      case (Some(_), Some(_), true, _) => hmrcApprovalRequiredForFRS102(boxRetriever)
      case (Some(_), Some(_), _, true) => hmrcApprovalRequiredForFRS105(boxRetriever)
      case _ => false
    }

    HmrcAccountsApprovalRequired(approvalRequired)
  }

  private def hmrcApprovalRequiredForFRS102(boxRetriever: FilingAttributesBoxValueRetriever) = {

    val (drToCoHo, plToCoHo) = boxRetriever match {
      case br: FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever => (br.ac8021().orFalse, br.acq8161().orFalse)
      case _ => (true, true)
    }

    !drToCoHo || !plToCoHo
  }

  private def hmrcApprovalRequiredForFRS105(boxRetriever: FilingAttributesBoxValueRetriever) = {

    val (drToHmrc, drToCoHo, plToCoHo) = boxRetriever match {
      case br: FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever => (br.ac8023().orFalse, br.ac8021().orFalse, br.acq8161().orFalse)
      case _ => (true, true, true)
    }

    (drToHmrc, drToCoHo, plToCoHo) match {
      case (false, false, false) => true
      case (true, false, true) => true
      case (true, true, false) => true
      case _ => false
    }
  }

  private def isFRS102(hmrcReturn: Option[Return]): Boolean = hmrcReturn.exists(_.version == FRS102)

  private def isFRS105(hmrcReturn: Option[Return]): Boolean = hmrcReturn.exists(_.version == FRS105)

  private def findHmrcAccountsType(returns: Set[Return]): Option[Return] = {
    returns.find { ret =>
      ret.submission match {
        case accounts: HmrcAccounts with CoHoEquivalent => true
        case _ => false
      }
    }
  }

  private def findMatchingCohoAccounts(returns: Set[Return], hmrcAccounts: Return): Option[Return] = {
    hmrcAccounts.submission match {
      case accounts: CoHoEquivalent => returns.find( _.submission == accounts.coHoReturnType)
      case _ => None
    }
  }
}
