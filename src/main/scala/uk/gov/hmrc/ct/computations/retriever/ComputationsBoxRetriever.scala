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

package uk.gov.hmrc.ct.computations.retriever

import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, BoxValues}
import uk.gov.hmrc.ct.computations._

object ComputationsBoxRetriever extends BoxValues[ComputationsBoxRetriever]

trait ComputationsBoxRetriever extends BoxRetriever {

  self: AccountsBoxRetriever =>

  def retrieveAP1(): AP1

  def retrieveAP2(): AP2

  def retrieveAP3(): AP3

  def retrieveCP1(): CP1

  def retrieveCP2(): CP2

  def retrieveCP7(): CP7

  def retrieveCP8(): CP8

  def retrieveCP15(): CP15

  def retrieveCP16(): CP16

  def retrieveCP17(): CP17

  def retrieveCP18(): CP18

  def retrieveCP19(): CP19

  def retrieveCP20(): CP20

  def retrieveCP21(): CP21

  def retrieveCP22(): CP22

  def retrieveCP23(): CP23

  def retrieveCP24(): CP24

  def retrieveCP25(): CP25

  def retrieveCP26(): CP26

  def retrieveCP27(): CP27

  def retrieveCP28(): CP28

  def retrieveCP29(): CP29

  def retrieveCP30(): CP30

  def retrieveCP31(): CP31

  def retrieveCP32(): CP32

  def retrieveCP33(): CP33

  def retrieveCP34(): CP34

  def retrieveCP35(): CP35

  def retrieveCP36(): CP36

  def retrieveCP37(): CP37

  def retrieveCP43(): CP43

  def retrieveCP46(): CP46

  def retrieveCP47(): CP47

  def retrieveCP48(): CP48

  def retrieveCP49(): CP49

  def retrieveCP51(): CP51

  def retrieveCP52(): CP52

  def retrieveCP53(): CP53

  def retrieveCP55(): CP55

  def retrieveCP57(): CP57

  def retrieveCP78(): CP78

  def retrieveCP79(): CP79

  def retrieveCP80(): CP80

  def retrieveCP81Input(): CP81Input

  def retrieveCP82(): CP82

  def retrieveCP83(): CP83

  def retrieveCP84(): CP84

  def retrieveCP85(): CP85

  def retrieveCP86(): CP86

  def retrieveCP87Input(): CP87Input

  def retrieveCP88(): CP88

  def retrieveCP89(): CP89

  def retrieveCP91Input(): CP91Input

  def retrieveCP281(): CP281

  def retrieveCP285(): CP285

  def retrieveCP286(): CP286

  def retrieveCP287(): CP287

  def retrieveCP301(): CP301

  def retrieveCP302(): CP302

  def retrieveCP303(): CP303

  def retrieveCP501(): CP501

  def retrieveCP502(): CP502

  def retrieveCP503(): CP503

  def retrieveCP510(): CP510

  def retrieveCP14(): CP14 = CP14.calculate(this)

  def retrieveCP38(): CP38 = CP38.calculate(this)

  def retrieveCP40(): CP40 = CP40(retrieveCP38())

  def retrieveCP44(): CP44 = CP44.calculate(this)

  def retrieveCP45(): CP45 = CP45(retrieveCP44())

  def retrieveCP54(): CP54 = CP54.calculate(this)

  def retrieveCP58(): CP58 = CP58(retrieveCP43())

  def retrieveCP59(): CP59 = CP59.calculate(this)

  def retrieveCP81(): CP81 = CP81(retrieveCP81Input())

  def retrieveCP87(): CP87 = CP87(retrieveCP87Input())

  def retrieveCP90(): CP90 = CP90.calculate(this)

  def retrieveCP91(): CP91 = CP91.calculate(this)

  def retrieveCP92(): CP92 = CP92.calculate(this)

  def retrieveCP93(): CP93 = CP93(retrieveCP186())

  def retrieveCP95(): CP95 = CP95(retrieveCP93())

  def retrieveCP96(): CP96 = CP96(retrieveCP91())

  def retrieveCP98(): CP98 = CP98(retrieveCP96())

  def retrieveCP99(): CP99 = CP99.calculate(this)

  def retrieveCP100(): CP100 = CP100(retrieveCP45())

  def retrieveCP101(): CP101 = CP101(retrieveCP46())

  def retrieveCP102(): CP102 = CP102(retrieveCP47())

  def retrieveCP103(): CP103 = CP103(retrieveCP48())

  def retrieveCP104(): CP104 = CP104(retrieveCP49())

  def retrieveCP106(): CP106 = CP106(retrieveCP51())

  def retrieveCP107(): CP107 = CP107(retrieveCP52())

  def retrieveCP108(): CP108 = CP108(retrieveCP53())

  def retrieveCP114(): CP114 = CP114(retrieveCP58())

  def retrieveCP116(): CP116 = CP116(retrieveCP59())

  def retrieveCP117(): CP117 = CP117.calculate(this)

  def retrieveCP118(): CP118 = CP118.calculate(this)

  def retrieveCP186(): CP186 = CP186.calculate(this)

  def retrieveCP234(): CP234 = CP234(retrieveCP281())

  def retrieveCP235(): CP235 = CP235.calculate(this)

  def retrieveCP237(): CP237 = CP237(retrieveCP287())

  def retrieveCP238(): CP238 = CP238(retrieveCP290())

  def retrieveCP239(): CP239 = CP239(retrieveCP294())

  def retrieveCP240(): CP240 = CP240(retrieveCP288())

  def retrieveCP245(): CP245 = CP245(retrieveCP96())

  def retrieveCP246(): CP246 = CP246(retrieveCP93())

  def retrieveCP247(): CP247 = CP247(retrieveCP91())

  def retrieveCP248(): CP248 = CP248(retrieveCP186())

  def retrieveCP249(): CP249 = CP249(retrieveCP88())

  def retrieveCP251(): CP251 = CP251(retrieveCP81())

  def retrieveCP252(): CP252 = CP252(retrieveCP79())

  def retrieveCP253(): CP253 = CP253.calculate(this)

  def retrieveCP256(): CP256 = CP256.calculate(this)

  def retrieveCP257(): CP257 = CP257.calculate(this)

  def retrieveCP258(): CP258 = CP258.calculate(this)

  def retrieveCP259(): CP259 = CP259.calculate(this)

  def retrieveCP264(): CP264 = CP264(retrieveCP239())

  def retrieveCP265(): CP265 = CP265(retrieveCP293())

  def retrieveCP266(): CP266 = CP266(retrieveCP295())

  def retrieveCP273(): CP273 = CP273(retrieveCP251())

  def retrieveCP274(): CP274 = CP274(retrieveCP253())

  def retrieveCP278(): CP278 = CP278(retrieveCP252())

  def retrieveCP279(): CP279 = CP279(retrieveCP88())

  def retrieveCP282(): CP282 = CP282.calculate(this)

  def retrieveCP283(): CP283 = CP283.calculate(this)

  def retrieveCP284(): CP284 = CP284.calculate(this)

  def retrieveCP288(): CP288 = CP288.calculate(this)

  def retrieveCP289(): CP289 = CP289.calculate(this)

  def retrieveCP290(): CP290 = CP290.calculate(this)

  def retrieveCP291(): CP291 = CP291.calculate(this)

  def retrieveCP292(): CP292 = CP292(retrieveCP58())

  def retrieveCP293(): CP293 = CP293.calculate(this)

  def retrieveCP294(): CP294 = CP294.calculate(this)

  def retrieveCP295(): CP295 = CP295.calculate(this)

  def retrieveCP305(): CP305 = CP305.calculate(this)

  def retrieveCP504(): CP504 = CP504(retrieveCP501())

  def retrieveCP505(): CP505 = CP505(retrieveCP502())

  def retrieveCP507(): CP507 = CP507(retrieveCP501())

  def retrieveCP508(): CP508 = CP508(retrieveCP503())

  def retrieveCP509(): CP509 = CP509.calculate(this)

  def retrieveCP511(): CP511 = CP511.calculate(this)

  def retrieveCP512(): CP512 = CP512(retrieveCP511())

  def retrieveCP513(): CP513 = CP513(retrieveCP502())

  def retrieveCP514(): CP514 = CP514(retrieveCP511())

  def retrieveCP515(): CP515 = CP515(retrieveCP513())

  def retrieveCP666(): CP666

  def retrieveCP667(): CP667

  def retrieveCP668(): CP668

  def retrieveCP669(): CP669 = CP669.calculate(this)

  def retrieveCP670(): CP670 = CP670.calculate(this)

  def retrieveCP671(): CP671 = CP671(retrieveCP91())

  def retrieveCP672(): CP672

  def retrieveCP673(): CP673

  def retrieveCP674(): CP674

  def retrieveCP998(): CP998 = CP998.calculate(this)

  def retrieveCP999(): CP999 = CP999.calculate(this)

  def retrieveCPAux1(): CPAux1 = CPAux1.calculate(this)

  def retrieveCPAux2(): CPAux2 = CPAux2.calculate(this)

  def retrieveCPAux3(): CPAux3 = CPAux3.calculate(this)

  def retrieveCPQ1000(): CPQ1000

  def retrieveCPQ7(): CPQ7

  def retrieveCPQ8(): CPQ8

  def retrieveCPQ10(): CPQ10

  def retrieveCPQ17(): CPQ17

  def retrieveCPQ18(): CPQ18

  def retrieveCPQ19(): CPQ19

  def retrieveCPQ20(): CPQ20

  def retrieveCPQ21(): CPQ21

  def retrieveCATO01(): CATO01 = CATO01.calculate(this)

  def retrieveCATO02(): CATO02 = CATO02.calculate(this)

  def retrieveCATO03(): CATO03 = CATO03.calculate(this)

  def retrieveCATO11(): CATO11

  def retrieveCATO12(): CATO12

  def retrieveCATO13(): CATO13 = CATO13.calculate(this)

  def retrieveCATO14(): CATO14 = CATO14.calculate(this)

  def retrieveCATO15(): CATO15 = CATO15.calculate(this)

  def retrieveCATO16(): CATO16 = CATO16.calculate(this)

  def retrieveCATO20(): CATO20 = CATO20.calculate(this)

  def retrieveCATO21(): CATO21 = CATO21.calculate(this)

  def retrieveCATO22(): CATO22 = CATO22.calculate(this)

  def retrieveLEC01(): LEC01

  def retrieveLEC10(): LEC10 = LEC10.calculate(this)

  def retrieveLEC11(): LEC11 = LEC11.calculate(this)

  def retrieveLEC12(): LEC12 = LEC12.calculate(this)

  def retrieveLEC13(): LEC13 = LEC13.calculate(this)
}
