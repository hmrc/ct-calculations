/*
 * Copyright 2024 HM Revenue & Customs
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

import uk.gov.hmrc.ct.accounts.frs10x.boxes.ACQ8999
import uk.gov.hmrc.ct.{CATO24, CountryOfRegistration}
import uk.gov.hmrc.ct.accounts.frsse2008.stubs.StubbedAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC2, AC205, AC206, AC5, AC6}
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.box.stubs.StubbedFilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.covidSupport.{CP121, CP122, CP123, CP124, CP125}
import uk.gov.hmrc.ct.computations.lowEmissionCars.LEC01
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600.v3.retriever.{CT600BoxRetriever, CT600DeclarationBoxRetriever}
import uk.gov.hmrc.ct.ct600a.v3._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.ct600j.v3._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

class StubbedCT600BoxRetriever extends CT600BoxRetriever
                                  with StubbedAccountsBoxRetriever
                                  with StubbedFilingAttributesBoxValueRetriever
                                  with ComputationsBoxRetriever
                                  with CT600ABoxRetriever
                                  with CT600JBoxRetriever
                                  with CT600DeclarationBoxRetriever {


  override def b45Input(): B45Input = ???

  override def b1(): B1 = ???

  override def b5(): B5 = ???

  override def b6(): B6 = ???

  override def b7(): B7 = ???

  override def b8(): B8 = ???

  override def b45(): B45 = ???

  override def b616(): B616 = ???

  override def b617(): B617 = ???

  override def b618(): B618 = ???

  override def b780(): B780 = ???

  override def b690(): B690 = ???

  override def b693(): B693 = ???

  override def b694(): B694 = ???

  override def b975(): B975 = ???

  override def b980(): B980 = ???

  override def n092(): N092 = ???

  override def b765(): B765 = ???

  override def b85A(): B85A = ???

  override def b750(): B750 = ???

  override def b150(): B150 = ???

  override def b2(): B2 = ???

  override def b773(): B773 = ???

  override def b735(): B735 = ???

  override def b741(): B741 = ???

  override def b742(): B742 = ???

  override def b743(): B743 = ???

  override def b744(): B744 = ???

  override def b705(): B705 = ???

  override def b755(): B755 = ???

  override def bFQ1(): BFQ1 = ???

  override def b4(): B4 = ???

  override def b710(): B710 = ???

  override def b620(): B620 = ???

  override def b40(): B40 = ???

  override def b515(): B515 = ???

  override def b527(): B527 = ???

  override def b55(): B55 = ???

  override def b90A(): B90A = ???

  override def b595(): B595 = ???

  override def b985(): B985 = ???

  override def b3(): B3 = ???

  override def b326(): B326 = ???

  override def b327(): B327 = ???

  override def b328(): B328 = ???

  override def b329(): B329 = ???

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

  override def cp281a(): CP281a = ???

  override def cp281b(): CP281b = ???

  override def cp283a(): CP283a = ???

  override def cp283b(): CP283b = ???

  override def cp283d(): CP283d = ???

  override def cp288a(): CP288a = ???

  override def cp288b(): CP288b = ???

  override def cp997(): CP997 = ???

  override def lec01(): LEC01 = ???

  override def cp86(): CP86 = ???

  override def ap2(): AP2 = ???

  override def cpQ17(): CPQ17 = ???

  override def cp674(): CP674 = ???

  override def cp675(): CP675 = ???

  override def cp676(): CP676 = ???

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

  override def cp286a(): CP286a = ???

  override def cp286b(): CP286b = ???

  override def cp82(): CP82 = ???

  override def cp29(): CP29 = ???

  override def cp8(): CP8 = ???

  override def cp85(): CP85 = ???

  override def ap1(): AP1 = ???

  override def cp79(): CP79 = ???

  override def cp665(): CP665 = ???

  override def cp46(): CP46 = ???

  override def cp2(): CP2 = ???

  override def cpQ10(): CPQ10 = ???

  override def cp52(): CP52 = ???

  override def cp88(): CP88 = ???

  override def cp34(): CP34 = ???

  override def cp55(): CP55 = ???

  override def cp49(): CP49 = ???

  override def cp87Input(): CP87Input = ???

  override def cp87a(): CP87a = ???

  override def cp301(): CP301 = ???

  override def cp3010(): CP3010 = ???

  override def cp3020(): CP3020 = ???

  override def cp3030(): CP3030 = ???

  override def cpQ321(): CPQ321 = ???

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

  override def cp672a(): CP672a = ???

  override def cp666(): CP666 = ???

  override def cp677(): CP677 = ???

  override def cp678(): CP678 = ???

  override def cp679(): CP679 = ???

  override def cp680(): CP680 = ???

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

  override def cp281c(): CP281c = ???

  override def cp283c(): CP283c = ???

  override def cp997c(): CP997c = ???

  override def cp997d(): CP997d = ???

  override def cpQ117(): CPQ117 = ???

  override def cpQ11(): CPQ11 = ???

  override def sba01: SBA01 = ???

  override def sba02: List[Option[Int]] = ???

  override def cp980(): CP980 = ???

  override def cp981(): CP981 = ???

  override def cp982(): CP982 = ???

  override def cp983(): CP983 = ???

  override def cato24: CATO24 = ???

  override def cp120: CP120 = ???

  override def cp121: CP121 = ???
  
  override def cp122: CP122 = ???

  override def cp123: CP123 = ???

  override def cp124: CP124 = ???

  override def cp125: CP125 = ???

  override def cp127: CP127 = ???

  override def cp128: CP128 = ???

  override def cp129: CP129 = ???

  override def acq8999a: ACQ8999 = ???

  override def ac5(): AC5 = ???

  override def ac6(): AC6 = ???

  override def cpSuperDeductionPercentage(): _root_.uk.gov.hmrc.ct.computations.CPSuperDeductionPercentage = ???

  override def cpSuperDeductionOverlap(): CPSuperDeductionOverlap = ???
}
