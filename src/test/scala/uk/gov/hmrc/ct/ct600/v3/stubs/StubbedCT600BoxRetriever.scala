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

package uk.gov.hmrc.ct.ct600.v3.stubs

import uk.gov.hmrc.ct.accounts.{AC2, AC205, AC206}
import uk.gov.hmrc.ct.accounts.frsse2008.stubs.StubbedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.stubs.StubbedFilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600a.v3._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.ct600j.v3._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever
import uk.gov.hmrc.ct.{CATO10, CATO11, CATO12, CountryOfRegistration}
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.ct600.v3.retriever.{CT600BoxRetriever, CT600DeclarationBoxRetriever}

class StubbedCT600BoxRetriever extends CT600BoxRetriever
                                  with StubbedAccountsBoxRetriever
                                  with StubbedFilingAttributesBoxValueRetriever
                                  with ComputationsBoxRetriever
                                  with CT600ABoxRetriever
                                  with CT600JBoxRetriever
                                  with CT600DeclarationBoxRetriever {


  override def b45Input(): B45Input = ???

  override def b1(): B1 = ???

  override def b45(): B45 = ???

  override def b780(): B780 = ???

  override def b690(): B690 = ???

  override def b975(): B975 = ???

  override def b980(): B980 = ???

  override def n092(): N092 = ???

  override def b765(): B765 = ???

  override def b85A(): B85A = ???

  override def b750(): B750 = ???

  override def b150(): B150 = ???

  override def b2(): B2 = ???

  override def b735(): B735 = ???

  override def b705(): B705 = ???

  override def b755(): B755 = ???

  override def bFQ1(): BFQ1 = ???

  override def b4(): B4 = ???

  override def b710(): B710 = ???

  override def b620(): B620 = ???

  override def b40(): B40 = ???

  override def b515(): B515 = ???

  override def b55(): B55 = ???

  override def b90A(): B90A = ???

  override def b595(): B595 = ???

  override def b985(): B985 = ???

  override def b3(): B3 = ???

  override def b760(): B760 = ???

  override def b775(): B775 = ???

  override def b80A(): B80A = ???

  override def j25(): J25 = ???

  override def j10(): J10 = ???

  override def j20A(): J20A = ???

  override def j25A(): J25A = ???

  override def j40(): J40 = ???

  override def j40A(): J40A = ???

  override def j5(): J5 = ???

  override def j15(): J15 = ???

  override def j30(): J30 = ???

  override def j45(): J45 = ???

  override def j10A(): J10A = ???

  override def j15A(): J15A = ???

  override def j45A(): J45A = ???

  override def j50(): J50 = ???

  override def j30A(): J30A = ???

  override def j20(): J20 = ???

  override def j35(): J35 = ???

  override def j35A(): J35A = ???

  override def j50A(): J50A = ???

  override def j5A(): J5A = ???

  override def lpq03(): LPQ03 = ???

  override def lp04(): LP04 = ???

  override def lpq08(): LPQ08 = ???

  override def a5(): A5 = ???

  override def lpq04(): LPQ04 = ???

  override def lpq10(): LPQ10 = ???

  override def lpq07(): LPQ07 = ???

  override def loansToParticipators(): LoansToParticipators = ???

  override def cp36(): CP36 = ???

  override def cp303(): CP303 = ???

  override def cp501(): CP501 = ???

  override def cp287(): CP287 = ???

  override def cpQ20(): CPQ20 = ???

  override def cp15(): CP15 = ???

  override def cp21(): CP21 = ???

  override def cp24(): CP24 = ???

  override def cp18(): CP18 = ???

  override def cp281(): CP281 = ???

  override def lec01(): LEC01 = ???

  override def cp86(): CP86 = ???

  override def ap2(): AP2 = ???

  override def cpQ17(): CPQ17 = ???

  override def cp674(): CP674 = ???

  override def cp668(): CP668 = ???

  override def cp80(): CP80 = ???

  override def cp89(): CP89 = ???

  override def cp53(): CP53 = ???

  override def cp302(): CP302 = ???

  override def cp35(): CP35 = ???

  override def cp83(): CP83 = ???

  override def cpQ1000(): CPQ1000 = ???

  override def cp503(): CP503 = ???

  override def cp23(): CP23 = ???

  override def cp91Input(): CP91Input = ???

  override def cp17(): CP17 = ???

  override def cp667(): CP667 = ???

  override def cpQ19(): CPQ19 = ???

  override def cp47(): CP47 = ???

  override def cp673(): CP673 = ???

  override def cp32(): CP32 = ???

  override def cp26(): CP26 = ???

  override def cp20(): CP20 = ???

  override def cpQ8(): CPQ8 = ???

  override def cp286(): CP286 = ???

  override def cp82(): CP82 = ???

  override def cp29(): CP29 = ???

  override def cp8(): CP8 = ???

  override def cp85(): CP85 = ???

  override def ap1(): AP1 = ???

  override def cp79(): CP79 = ???

  override def cp46(): CP46 = ???

  override def cp2(): CP2 = ???

  override def cpQ10(): CPQ10 = ???

  override def cp52(): CP52 = ???

  override def cp88(): CP88 = ???

  override def cp34(): CP34 = ???

  override def cp55(): CP55 = ???

  override def cp49(): CP49 = ???

  override def cp87Input(): CP87Input = ???

  override def cp301(): CP301 = ???

  override def cpQ7(): CPQ7 = ???

  override def cp28(): CP28 = ???

  override def cpQ21(): CPQ21 = ???

  override def cp22(): CP22 = ???

  override def cp502(): CP502 = ???

  override def cp16(): CP16 = ???

  override def cp43(): CP43 = ???

  override def cp37(): CP37 = ???

  override def cp31(): CP31 = ???

  override def cp19(): CP19 = ???

  override def cpQ18(): CPQ18 = ???

  override def cp1(): CP1 = ???

  override def cp672(): CP672 = ???

  override def cp666(): CP666 = ???

  override def cp25(): CP25 = ???

  override def cp285(): CP285 = ???

  override def cp78(): CP78 = ???

  override def cp7(): CP7 = ???

  override def cp84(): CP84 = ???

  override def cp57(): CP57 = ???

  override def cp30(): CP30 = ???

  override def ap3(): AP3 = ???

  override def cp51(): CP51 = ???

  override def cp510(): CP510 = ???

  override def cp33(): CP33 = ???

  override def cp48(): CP48 = ???

  override def cp27(): CP27 = ???

  override def generateValues: Map[String, CtValue[_]] = ???

  override def b65(): B65 = ???

  override def cp252(): CP252 = ???

  override def countryOfRegistration(): CountryOfRegistration = ???

  override def ac205(): AC205 = ???

  override def ac206(): AC206 = ???

  override def ac2(): AC2 = ???
}
