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

package uk.gov.hmrc.ct.ct600.v2.retriever

import uk.gov.hmrc.ct.CATO04
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.BoxValues
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.retriever.DeclarationBoxRetriever
import uk.gov.hmrc.ct.ct600.v2._
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.ct600j.v2.retriever.CT600JBoxRetriever

object CT600BoxRetriever extends BoxValues[CT600BoxRetriever]

trait CT600BoxRetriever extends ComputationsBoxRetriever with CT600ABoxRetriever with CT600JBoxRetriever with CT600EBoxRetriever with DeclarationBoxRetriever {

  self: AccountsBoxRetriever =>

  def retrieveB38(): B38

  def retrieveB39(): B39

  def retrieveB42(): B42

  def retrieveB84(): B84

  def retrieveB91(): B91

  def retrieveB139(): B139

  def retrieveB140(): B140

  def retrieveB149(): B149

  def retrieveB150(): B150

  def retrieveB151(): B151

  def retrieveB152(): B152

  def retrieveB153(): B153

  def retrieveB155(): B155

  def retrieveB156(): B156

  def retrieveB158(): B158

  def retrieveB1571(): B1571

  def retrieveB1572(): B1572

  def retrieveB1573(): B1573

  def retrieveB1574(): B1574

  def retrieveB1575(): B1575

  def retrieveB3(): B3 = B3(retrieveCP256())

  def retrieveB4(): B4 = B4(retrieveCP257())

  def retrieveB5(): B5 = B5(retrieveCP258())

  def retrieveB6(): B6 = B6(retrieveCP259())

  def retrieveB11(): B11 = B11(retrieveCP511())

  def retrieveB14(): B14 = B14(retrieveCP515())

  def retrieveB21(): B21 = B21(retrieveCP265())

  def retrieveB30(): B30 = B30(retrieveCP264())

  def retrieveB35(): B35 = B35(retrieveCP305())

  def retrieveB37(): B37 = B37(retrieveCP295())

  def retrieveB43(): B43 = B43.calculate(this)

  def retrieveB44(): B44 = B44.calculate(this)

  def retrieveB45(): B45 = B45.calculate(this)

  def retrieveB46(): B46 = B46.calculate(this)

  def retrieveB46R(): B46R = B46R.calculate(this)

  def retrieveB53(): B53 = B53.calculate(this)

  def retrieveB54(): B54 = B54.calculate(this)

  def retrieveB55(): B55 = B55.calculate(this)

  def retrieveB56(): B56 = B56.calculate(this)

  def retrieveB56R(): B56R = B56R.calculate(this)

  def retrieveB63(): B63 = B63.calculate(this)

  def retrieveB64(): B64 = B64(retrieveCATO04())

  def retrieveB65(): B65 = B65.calculate(this)

  def retrieveB70(): B70 = B70.calculate(this)

  def retrieveB78(): B78 = B78(retrieveB65())

  def retrieveB79(): B79 = B79(retrieveA13())

  def retrieveB80(): B80 = B80.calculate(this)

  def retrieveB85(): B85 = B85.calculate(this)

  def retrieveB86(): B86 = B86.calculate(this)

  def retrieveB92(): B92 = B92.calculate(this)

  def retrieveB93(): B93 = B93.calculate(this)

  def retrieveB105(): B105 = B105(retrieveCP668())

  def retrieveB106(): B106 = B106(retrieveCP670())

  def retrieveB107(): B107 = B107(retrieveCP248())

  def retrieveB108(): B108 = B108(retrieveCP291())

  def retrieveB118(): B118 = B118(retrieveCP251())

  def retrieveB121(): B121 = B121(retrieveCP253())

  def retrieveB122(): B122 = B122.calculate(this)

  def retrieveB172(): B172 = B172(retrieveCP88())

  def retrieveB174(): B174 = B174(retrieveCP278)

  def retrieveCATO04(): CATO04 = CATO04.calculate(this)

  def retrieveRSQ1(): RSQ1

  def retrieveRSQ2(): RSQ2

  def retrieveRSQ3(): RSQ3

  def retrieveRSQ4(): RSQ4

  def retrieveRSQ7(): RSQ7

  def retrieveRSQ8(): RSQ8

  def retrieveRDQ1(): RDQ1

  def retrieveRDQ2(): RDQ2
}
