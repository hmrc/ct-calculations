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

package uk.gov.hmrc.ct.computations.stubs

import uk.gov.hmrc.ct.accounts.frsse2008.stubs.StubbedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.box.stubs.StubbedFilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.{CountryOfRegistration, CATO11, CATO12}

class StubbedComputationsBoxRetriever extends ComputationsBoxRetriever with StubbedAccountsBoxRetriever with StubbedFilingAttributesBoxValueRetriever {

  override def ap1(): AP1 = ???

  override def cp36(): CP36 = ???

  override def cp303(): CP303 = ???

  override def cp501(): CP501 = ???

  override def cp287(): CP287 = ???

  override def cp21(): CP21 = ???

  override def cp15(): CP15 = ???

  override def cpQ20(): CPQ20 = ???

  override def cp24(): CP24 = ???

  override def cp18(): CP18 = ???

  override def cp281(): CP281 = ???

  override def cp86(): CP86 = ???

  override def lec01(): LEC01 = ???

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

  override def cp26(): CP26 = ???

  override def cp32(): CP32 = ???

  override def cp20(): CP20 = ???

  override def cpQ8(): CPQ8 = ???

  override def cp286(): CP286 = ???

  override def cp82(): CP82 = ???

  override def cp29(): CP29 = ???

  override def cp8(): CP8 = ???

  override def cp85(): CP85 = ???

  override def cp79(): CP79 = ???

  override def cp46(): CP46 = ???

  override def cp2(): CP2 = ???

  override def cpQ10(): CPQ10 = ???

  override def cp52(): CP52 = ???

  override def cp88(): CP88 = ???

  override def cp34(): CP34 = ???

  override def cp49(): CP49 = ???

  override def cp55(): CP55 = ???

  override def cp87Input(): CP87Input = ???

  override def cpQ7(): CPQ7 = ???

  override def cp301(): CP301 = ???

  override def cp28(): CP28 = ???

  override def cp22(): CP22 = ???

  override def cp502(): CP502 = ???

  override def cpQ21(): CPQ21 = ???

  override def cp16(): CP16 = ???

  override def cp43(): CP43 = ???

  override def cp37(): CP37 = ???

  override def cp31(): CP31 = ???

  override def cpQ18(): CPQ18 = ???

  override def cp19(): CP19 = ???

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

  override def cp252(): CP252 = ???

  override def generateValues: Map[String, CtValue[_]] = ???

  override def countryOfRegistration(): CountryOfRegistration = CountryOfRegistration.EnglandWales
}
