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

package uk.gov.hmrc.ct.computations.stubs

import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.{CATO11, CATO12}

class StubbedComputationsBoxRetriever extends ComputationsBoxRetriever with StubbedAccountsBoxRetriever with StubbedFilingAttributesBoxValueRetriever {

  override def retrieveAP1(): AP1 = ???

  override def retrieveCP36(): CP36 = ???

  override def retrieveCP303(): CP303 = ???

  override def retrieveCP501(): CP501 = ???

  override def retrieveCATO11(): CATO11 = ???

  override def retrieveCP287(): CP287 = ???

  override def retrieveCP21(): CP21 = ???

  override def retrieveCP15(): CP15 = ???

  override def retrieveCPQ20(): CPQ20 = ???

  override def retrieveCP24(): CP24 = ???

  override def retrieveCP18(): CP18 = ???

  override def retrieveCP281(): CP281 = ???

  override def retrieveCP86(): CP86 = ???

  override def retrieveLEC01(): LEC01 = ???

  override def retrieveAP2(): AP2 = ???

  override def retrieveCPQ17(): CPQ17 = ???

  override def retrieveCP674(): CP674 = ???

  override def retrieveCP668(): CP668 = ???

  override def retrieveCP80(): CP80 = ???

  override def retrieveCP89(): CP89 = ???

  override def retrieveCP53(): CP53 = ???

  override def retrieveCP302(): CP302 = ???

  override def retrieveCP35(): CP35 = ???

  override def retrieveCP83(): CP83 = ???

  override def retrieveCPQ1000(): CPQ1000 = ???

  override def retrieveCP503(): CP503 = ???

  override def retrieveCP23(): CP23 = ???

  override def retrieveCP91Input(): CP91Input = ???

  override def retrieveCP17(): CP17 = ???

  override def retrieveCP667(): CP667 = ???

  override def retrieveCPQ19(): CPQ19 = ???

  override def retrieveCP47(): CP47 = ???

  override def retrieveCP673(): CP673 = ???

  override def retrieveCP26(): CP26 = ???

  override def retrieveCP32(): CP32 = ???

  override def retrieveCP20(): CP20 = ???

  override def retrieveCPQ8(): CPQ8 = ???

  override def retrieveCP286(): CP286 = ???

  override def retrieveCP82(): CP82 = ???

  override def retrieveCP29(): CP29 = ???

  override def retrieveCP8(): CP8 = ???

  override def retrieveCP85(): CP85 = ???

  override def retrieveCP79(): CP79 = ???

  override def retrieveCP46(): CP46 = ???

  override def retrieveCP2(): CP2 = ???

  override def retrieveCPQ10(): CPQ10 = ???

  override def retrieveCP52(): CP52 = ???

  override def retrieveCP88(): CP88 = ???

  override def retrieveCP34(): CP34 = ???

  override def retrieveCP49(): CP49 = ???

  override def retrieveCP55(): CP55 = ???

  override def retrieveCP87Input(): CP87Input = ???

  override def retrieveCPQ7(): CPQ7 = ???

  override def retrieveCP301(): CP301 = ???

  override def retrieveCP28(): CP28 = ???

  override def retrieveCP22(): CP22 = ???

  override def retrieveCP502(): CP502 = ???

  override def retrieveCPQ21(): CPQ21 = ???

  override def retrieveCP81Input(): CP81Input = ???

  override def retrieveCP16(): CP16 = ???

  override def retrieveCP43(): CP43 = ???

  override def retrieveCP37(): CP37 = ???

  override def retrieveCP31(): CP31 = ???

  override def retrieveCPQ18(): CPQ18 = ???

  override def retrieveCP19(): CP19 = ???

  override def retrieveCP1(): CP1 = ???

  override def retrieveCP672(): CP672 = ???

  override def retrieveCP666(): CP666 = ???

  override def retrieveCP25(): CP25 = ???

  override def retrieveCP285(): CP285 = ???

  override def retrieveCP78(): CP78 = ???

  override def retrieveCATO12(): CATO12 = ???

  override def retrieveCP7(): CP7 = ???

  override def retrieveCP84(): CP84 = ???

  override def retrieveCP57(): CP57 = ???

  override def retrieveCP30(): CP30 = ???

  override def retrieveAP3(): AP3 = ???

  override def retrieveCP51(): CP51 = ???

  override def retrieveCP510(): CP510 = ???

  override def retrieveCP33(): CP33 = ???

  override def retrieveCP48(): CP48 = ???

  override def retrieveCP27(): CP27 = ???

  override def generateValues: Map[String, CtValue[_]] = ???
}
