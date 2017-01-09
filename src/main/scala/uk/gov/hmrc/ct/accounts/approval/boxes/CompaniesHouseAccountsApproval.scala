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

package uk.gov.hmrc.ct.accounts.approval.boxes

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


case class CompaniesHouseAccountsApproval(ac199A: List[AC199A] = List.empty, ac8092: List[AC8092] = List.empty, ac8091: AC8091, ac198A: AC198A) extends CtBoxIdentifier(name = "Accounts approval")
  with CtValue[CompaniesHouseAccountsApproval]
  with AccountsApproval {

  override def value = this

  override def approvalEnabled(boxRetriever: FilingAttributesBoxValueRetriever) = boxRetriever.coHoAccountsApprovalRequired().value
}
