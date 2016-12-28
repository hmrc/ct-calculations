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

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Linked}

case class CoHoAccountsApprovalRequired(value: Boolean) extends CtBoxIdentifier("True if approval required for CoHo accounts") with CtBoolean

object CoHoAccountsApprovalRequired extends Linked[CompaniesHouseFiling, CoHoAccountsApprovalRequired] {

  override def apply(coHoFiling: CompaniesHouseFiling): CoHoAccountsApprovalRequired = new CoHoAccountsApprovalRequired(coHoFiling.value)
}
