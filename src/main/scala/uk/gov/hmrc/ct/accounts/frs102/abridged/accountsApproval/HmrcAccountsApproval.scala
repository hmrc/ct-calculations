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

package uk.gov.hmrc.ct.accounts.frs102.abridged.accountsApproval

import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs10xDirectorsBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class HmrcAccountsApproval(ac199A: List[AC199A] = List.empty, ac8092: List[AC8092] = List.empty, ac8091: AC8091, ac198A: AC198A) extends CtBoxIdentifier(name = "Accounts approval")
  with CtValue[HmrcAccountsApproval]
  with AccountsApproval {

  override def value = this

  override def approvalEnabled(boxRetriever: AbridgedAccountsBoxRetriever with Frs10xDirectorsBoxRetriever with Frs10xFilingQuestionsBoxRetriever with FilingAttributesBoxValueRetriever) = {
    (boxRetriever.companiesHouseFiling().value, boxRetriever.hmrcFiling().value, boxRetriever.ac8021().value, boxRetriever.acQ8161().value) match {
      case (false, true, _, _) => true
      case (true, true, Some(false), _) => true
      case (true, true, _, Some(false)) => true
      case _ => false
    }
  }

}

