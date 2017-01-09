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

package uk.gov.hmrc.ct.computations.retriever

import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.computations._

trait ComputationsBoxRetriever extends BoxRetriever {

  def ap1(): AP1

  def ap2(): AP2

  def ap3(): AP3

  def cp1(): CP1

  def cp2(): CP2

  def cp7(): CP7

  def cp8(): CP8

  def cp15(): CP15

  def cp16(): CP16

  def cp17(): CP17

  def cp18(): CP18

  def cp19(): CP19

  def cp20(): CP20

  def cp21(): CP21

  def cp22(): CP22

  def cp23(): CP23

  def cp24(): CP24

  def cp25(): CP25

  def cp26(): CP26

  def cp27(): CP27

  def cp28(): CP28

  def cp29(): CP29

  def cp30(): CP30

  def cp31(): CP31

  def cp32(): CP32

  def cp33(): CP33

  def cp34(): CP34

  def cp35(): CP35

  def cp36(): CP36

  def cp37(): CP37

  def cp43(): CP43

  def cp46(): CP46

  def cp47(): CP47

  def cp48(): CP48

  def cp49(): CP49

  def cp51(): CP51

  def cp52(): CP52

  def cp53(): CP53

  def cp55(): CP55

  def cp57(): CP57

  def cp78(): CP78

  def cp79(): CP79

  def cp80(): CP80

  def cp82(): CP82

  def cp83(): CP83

  def cp84(): CP84

  def cp85(): CP85

  def cp86(): CP86

  def cp87Input(): CP87Input

  def cp88(): CP88

  def cp89(): CP89

  def cp91Input(): CP91Input

  def cp281(): CP281

  def cp285(): CP285

  def cp286(): CP286

  def cp287(): CP287

  def cp301(): CP301

  def cp302(): CP302

  def cp303(): CP303

  def cp501(): CP501

  def cp502(): CP502

  def cp503(): CP503

  def cp510(): CP510

  def cp14(): CP14 = CP14.calculate(this)

  def cp38(): CP38 = CP38.calculate(this)

  def cp39(): CP39 = CP39(cp14())

  def cp40(): CP40 = CP40(cp38())

  def cp44(): CP44 = CP44.calculate(this)

  def cp45(): CP45 = CP45(cp44())

  def cp54(): CP54 = CP54.calculate(this)

  def cp58(): CP58 = CP58(cp43())

  def cp59(): CP59 = CP59.calculate(this)

  def cp81(): CP81 = CP81.calculate(this)

  def cp87(): CP87 = CP87(cp87Input())

  def cp90(): CP90 = CP90.calculate(this)

  def cp91(): CP91 = CP91.calculate(this)

  def cp92(): CP92 = CP92.calculate(this)

  def cp93(): CP93 = CP93(cp186())

  def cp95(): CP95 = CP95(cp93())

  def cp96(): CP96 = CP96(cp91())

  def cp98(): CP98 = CP98(cp96())

  def cp99(): CP99 = CP99.calculate(this)

  def cp100(): CP100 = CP100(cp45())

  def cp101(): CP101 = CP101(cp46())

  def cp102(): CP102 = CP102(cp47())

  def cp103(): CP103 = CP103(cp48())

  def cp104(): CP104 = CP104(cp49())

  def cp106(): CP106 = CP106(cp51())

  def cp107(): CP107 = CP107(cp52())

  def cp108(): CP108 = CP108(cp53())

  def cp114(): CP114 = CP114(cp58())

  def cp116(): CP116 = CP116(cp59())

  def cp117(): CP117 = CP117.calculate(this)

  def cp118(): CP118 = CP118.calculate(this)

  def cp186(): CP186 = CP186.calculate(this)

  def cp234(): CP234 = CP234(cp281())

  def cp235(): CP235 = CP235.calculate(this)

  def cp237(): CP237 = CP237(cp287())

  def cp238(): CP238 = CP238(cp290())

  def cp239(): CP239 = CP239(cp294())

  def cp240(): CP240 = CP240(cp288())

  def cp245(): CP245 = CP245(cp96())

  def cp246(): CP246 = CP246(cp93())

  def cp247(): CP247 = CP247(cp91())

  def cp248(): CP248 = CP248(cp186())

  def cp249(): CP249 = CP249(cp88())

  def cp251(): CP251 = CP251(cp81())

  def cp252(): CP252

  def cp253(): CP253 = CP253.calculate(this)

  def cp256(): CP256 = CP256.calculate(this)

  def cp257(): CP257 = CP257.calculate(this)

  def cp258(): CP258 = CP258.calculate(this)

  def cp259(): CP259 = CP259.calculate(this)

  def cp264(): CP264 = CP264(cp239())

  def cp265(): CP265 = CP265(cp293())

  def cp266(): CP266 = CP266(cp295())

  def cp273(): CP273 = CP273(cp251())

  def cp274(): CP274 = CP274(cp253())

  def cp278(): CP278 = CP278(cp252())

  def cp279(): CP279 = CP279(cp88())

  def cp282(): CP282 = CP282.calculate(this)

  def cp283(): CP283 = CP283.calculate(this)

  def cp284(): CP284 = CP284.calculate(this)

  def cp288(): CP288 = CP288.calculate(this)

  def cp289(): CP289 = CP289.calculate(this)

  def cp290(): CP290 = CP290.calculate(this)

  def cp291(): CP291 = CP291.calculate(this)

  def cp292(): CP292 = CP292(cp58())

  def cp293(): CP293 = CP293.calculate(this)

  def cp294(): CP294 = CP294.calculate(this)

  def cp295(): CP295 = CP295.calculate(this)

  def cp305(): CP305 = CP305.calculate(this)

  def cp500(): CP500 = CP500.calculate(this)

  def cp504(): CP504 = CP504(cp501())

  def cp505(): CP505 = CP505(cp502())

  def cp507(): CP507 = CP507(cp501())

  def cp508(): CP508 = CP508(cp503())

  def cp509(): CP509 = CP509.calculate(this)

  def cp511(): CP511 = CP511.calculate(this)

  def cp512(): CP512 = CP512(cp511())

  def cp513(): CP513 = CP513(cp502())

  def cp514(): CP514 = CP514(cp511())

  def cp515(): CP515 = CP515(cp513())

  def cp666(): CP666

  def cp667(): CP667

  def cp668(): CP668

  def cp669(): CP669 = CP669.calculate(this)

  def cp670(): CP670 = CP670.calculate(this)

  def cp671(): CP671 = CP671(cp91())

  def cp672(): CP672

  def cp673(): CP673

  def cp674(): CP674

  def cp998(): CP998 = CP998.calculate(this)

  def cp999(): CP999 = CP999.calculate(this)

  def cpAux1(): CPAux1 = CPAux1.calculate(this)

  def cpAux2(): CPAux2 = CPAux2.calculate(this)

  def cpAux3(): CPAux3 = CPAux3.calculate(this)

  def cpQ1000(): CPQ1000

  def cpQ7(): CPQ7

  def cpQ8(): CPQ8

  def cpQ10(): CPQ10

  def cpQ17(): CPQ17

  def cpQ18(): CPQ18

  def cpQ19(): CPQ19

  def cpQ20(): CPQ20

  def cpQ21(): CPQ21

  def cato01(): CATO01 = CATO01.calculate(this)

  def cato02(): CATO02 = CATO02.calculate(this)

  def cato03(): CATO03 = CATO03.calculate(this)

  def cato13(): CATO13 = CATO13.calculate(this)

  def cato14(): CATO14 = CATO14.calculate(this)

  def cato15(): CATO15 = CATO15.calculate(this)

  def cato16(): CATO16 = CATO16.calculate(this)

  def cato20(): CATO20 = CATO20.calculate(this)

  def cato21(): CATO21 = CATO21.calculate(this)

  def cato22(): CATO22 = CATO22.calculate(this)

  def lec01(): LEC01

  def lec10(): LEC10 = LEC10.calculate(this)

  def lec11(): LEC11 = LEC11.calculate(this)

  def lec12(): LEC12 = LEC12.calculate(this)

  def lec13(): LEC13 = LEC13.calculate(this)
}
