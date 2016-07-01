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

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtBoolean, Calculated, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.calculations.PeriodOfAccountsCalculator

case class B50(value: Boolean) extends CtBoxIdentifier("Making more then one return for this company") with CtBoolean

object B50 extends Calculated[B50, Frsse2008AccountsBoxRetriever] with PeriodOfAccountsCalculator {

  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever) =
    B50(isLongPeriodOfAccounts(boxRetriever.retrieveAC3(), boxRetriever.retrieveAC4()))

}
