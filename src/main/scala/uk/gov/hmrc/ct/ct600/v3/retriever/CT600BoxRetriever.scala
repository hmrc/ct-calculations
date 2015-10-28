/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v3.retriever

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.BoxValues
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.retriever.DeclarationBoxRetriever
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

object CT600BoxRetriever extends BoxValues[CT600BoxRetriever]

trait CT600BoxRetriever extends ComputationsBoxRetriever with CT600ABoxRetriever with CT600JBoxRetriever with DeclarationBoxRetriever {

  self: AccountsBoxRetriever =>

  def retrieveB1(): B1

  def retrieveB2(): B2

  def retrieveB3(): B3

  def retrieveB4(): B4

  def retrieveB40(): B40

  def retrieveB45(): B45

  def retrieveB50(): B50 = B50.calculate(this)

  def retrieveB55(): B55

  def retrieveB280(): B280 = B280.calculate(this)

  def retrieveB315(): B315 = B315(retrieveCP295())

  def retrieveB330(): B330 = B330.calculate(this)

  def retrieveB335(): B335 = B335.calculate(this)

  def retrieveB340() : B340 = B340.calculate(this)

  def retrieveB345() : B345 = B345.calculate(this)

  def retrieveB380() : B380 = B380.calculate(this)

  def retrieveB385() : B385 = B385.calculate(this)

  def retrieveB390() : B390 = B390.calculate(this)

  def retrieveB395() : B395 = B395.calculate(this)

  def retrieveB430() : B430 = B430.calculate(this)

  def retrieveB440(): B440 = B440(retrieveB430())

  def retrieveB475(): B475 = B475(retrieveB440())

  def retrieveB480(): B480 = B480.calculate(this)

  def retrieveB510(): B510 = B510.calculate(this)

  def retrieveB515(): B515

  def retrieveB520(): B520 = B520.calculate(this)

  def retrieveB525(): B525 = B525.calculate(this)

  def retrieveB595(): B595

  def retrieveB600(): B600 = B600.calculate(this)

  def retrieveB605(): B605 = B605.calculate(this)

  def retrieveB620(): B620

  def retrieveB860(): B860

  def retrieveB865(): B865 = B865(retrieveB605())

  def retrieveB870(): B870 = B870(retrieveB520())

  def retrieveBFQ1(): BFQ1

  def retrievePAYEEQ1(): PAYEEQ1

  def retrieveREPAYMENTSQ1(): REPAYMENTSQ1

  def retrieveB920(): B920

  def retrieveB925(): B925

  def retrieveB930(): B930

  def retrieveB935(): B935

  def retrieveB940(): B940

  def retrieveB955(): B955

  def retrieveB960(): B960

  def retrieveB965(): B965
}
