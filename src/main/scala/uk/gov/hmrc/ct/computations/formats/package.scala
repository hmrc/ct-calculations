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

package uk.gov.hmrc.ct.computations

import play.api.libs.json.{Format, Json, OFormat}
import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.box.formats._
import uk.gov.hmrc.ct.accounts.{AC5, AC6}
import uk.gov.hmrc.ct.computations.covidSupport.{CP121, CP122, CP123, CP124, CP125}
import uk.gov.hmrc.ct.computations.lowEmissionCars.{Car, LEC01}
import uk.gov.hmrc.ct.computations.machineryAndPlant.{CP105, CP109, CP110, CP94, CP97}

package object formats {

  implicit val ap1Format: Format[AP1] = new OptionalIntegerFormat[AP1](AP1.apply)

  implicit val ap2Format: Format[AP2] = new OptionalIntegerFormat[AP2](AP2.apply)

  implicit val ap3Format: Format[AP3] = new OptionalIntegerFormat[AP3](AP3.apply)

  implicit val cp1Format: Format[CP1] = new DateFormat[CP1](CP1.apply)

  implicit val cp2Format: Format[CP2] = new DateFormat[CP2](CP2.apply)

  implicit val ac5Format: Format[AC5] = new DateFormat[AC5](AC5.apply)

  implicit val ac6Format: Format[AC6] = new DateFormat[AC6](AC6.apply)

  implicit val cp6Format: Format[CP6] = new IntegerFormat[CP6](CP6.apply)

  implicit val cp7Format: Format[CP7] = new OptionalIntegerFormat[CP7](CP7.apply)

  implicit val cp8Format: Format[CP8] = new OptionalIntegerFormat[CP8](CP8.apply)

  implicit val cp14Format: Format[CP14] = new IntegerFormat[CP14](CP14.apply)

  implicit val cp15Format: Format[CP15] = new OptionalIntegerFormat[CP15](CP15.apply)

  implicit val cp16Format: Format[CP16] = new OptionalIntegerFormat[CP16](CP16.apply)

  implicit val cp17Format: Format[CP17] = new OptionalIntegerFormat[CP17](CP17.apply)

  implicit val cp18Format: Format[CP18] = new OptionalIntegerFormat[CP18](CP18.apply)

  implicit val cp19Format: Format[CP19] = new OptionalIntegerFormat[CP19](CP19.apply)

  implicit val cp20Format: Format[CP20] = new OptionalIntegerFormat[CP20](CP20.apply)

  implicit val cp21Format: Format[CP21] = new OptionalIntegerFormat[CP21](CP21.apply)

  implicit val cp22Format: Format[CP22] = new OptionalIntegerFormat[CP22](CP22.apply)

  implicit val cp23Format: Format[CP23] = new OptionalIntegerFormat[CP23](CP23.apply)

  implicit val cp24Format: Format[CP24] = new OptionalIntegerFormat[CP24](CP24.apply)

  implicit val cp25Format: Format[CP25] = new OptionalIntegerFormat[CP25](CP25.apply)

  implicit val cp26Format: Format[CP26] = new OptionalIntegerFormat[CP26](CP26.apply)

  implicit val cp27Format: Format[CP27] = new OptionalIntegerFormat[CP27](CP27.apply)

  implicit val cp28Format: Format[CP28] = new OptionalIntegerFormat[CP28](CP28.apply)

  implicit val cp29Format: Format[CP29] = new OptionalIntegerFormat[CP29](CP29.apply)

  implicit val cp30Format: Format[CP30] = new OptionalIntegerFormat[CP30](CP30.apply)

  implicit val cp31Format: Format[CP31] = new OptionalIntegerFormat[CP31](CP31.apply)

  implicit val cp32Format: Format[CP32] = new OptionalIntegerFormat[CP32](CP32.apply)

  implicit val cp33Format: Format[CP33] = new OptionalIntegerFormat[CP33](CP33.apply)

  implicit val cp34Format: Format[CP34] = new OptionalIntegerFormat[CP34](CP34.apply)

  implicit val cp35Format: Format[CP35] = new OptionalIntegerFormat[CP35](CP35.apply)

  implicit val cp36Format: Format[CP36] = new OptionalIntegerFormat[CP36](CP36.apply)

  implicit val cp37Format: Format[CP37] = new OptionalIntegerFormat[CP37](CP37.apply)

  implicit val cp38Format: Format[CP38] = new IntegerFormat[CP38](CP38.apply)

  implicit val cp40Format: Format[CP40] = new IntegerFormat[CP40](CP40.apply)

  implicit val cp43Format: Format[CP43] = new OptionalIntegerFormat[CP43](CP43.apply)

  implicit val cp44Format: Format[CP44] = new IntegerFormat[CP44](CP44.apply)

  implicit val cp45Format: Format[CP45] = new IntegerFormat[CP45](CP45.apply)

  implicit val cp46Format: Format[CP46] = new OptionalIntegerFormat[CP46](CP46.apply)

  implicit val cp47Format: Format[CP47] = new OptionalIntegerFormat[CP47](CP47.apply)

  implicit val cp48Format: Format[CP48] = new OptionalIntegerFormat[CP48](CP48.apply)

  implicit val cp49Format: Format[CP49] = new OptionalIntegerFormat[CP49](CP49.apply)

  implicit val cp50Format: Format[CP50] = new OptionalIntegerFormat[CP50](CP50.apply)

  implicit val cp51Format: Format[CP51] = new OptionalIntegerFormat[CP51](CP51.apply)

  implicit val cp52Format: Format[CP52] = new OptionalIntegerFormat[CP52](CP52.apply)

  implicit val cp53Format: Format[CP53] = new OptionalIntegerFormat[CP53](CP53.apply)

  implicit val cp54Format: Format[CP54] = new IntegerFormat[CP54](CP54.apply)

  implicit val cp55Format: Format[CP55] = new OptionalIntegerFormat[CP55](CP55.apply)

  implicit val cp56Format: Format[CP56] = new IntegerFormat[CP56](CP56.apply)

  implicit val cp57Format: Format[CP57] = new OptionalIntegerFormat[CP57](CP57.apply)

  implicit val cp58Format: Format[CP58] = new IntegerFormat[CP58](CP58.apply)

  implicit val cp59Format: Format[CP59] = new IntegerFormat[CP59](CP59.apply)

  implicit val cp78Format: Format[CP78] = new OptionalIntegerFormat[CP78](CP78.apply)

  implicit val cp79Format: Format[CP79] = new OptionalIntegerFormat[CP79](CP79.apply)

  implicit val cp80Format: Format[CP80] = new OptionalIntegerFormat[CP80](CP80.apply)

  implicit val cp81Format: Format[CP81] = new IntegerFormat[CP81](CP81.apply)

  implicit val cp81InputFormat: Format[CP81Input] = new OptionalIntegerFormat[CP81Input](CP81Input.apply)

  implicit val cp82Format: Format[CP82] = new OptionalIntegerFormat[CP82](CP82.apply)

  implicit val cp83Format: Format[CP83] = new OptionalIntegerFormat[CP83](CP83.apply)

  implicit val cp84Format: Format[CP84] = new OptionalIntegerFormat[CP84](CP84.apply)

  implicit val cp85Format: Format[CP85] = new OptionalIntegerFormat[CP85](CP85.apply)

  implicit val cp86Format: Format[CP86] = new OptionalIntegerFormat[CP86](CP86.apply)

  implicit val cp87InputFormat: Format[CP87Input] = new OptionalIntegerFormat[CP87Input](CP87Input.apply)

  implicit val cp87aFormat: Format[CP87a] = new OptionalIntegerFormat[CP87a](CP87a.apply)

  implicit val cp88Format: Format[CP88] = new OptionalIntegerFormat[CP88](CP88.apply)

  implicit val cp89Format: Format[CP89] = new OptionalIntegerFormat[CP89](CP89.apply)

  implicit val cp90Format: Format[CP90] = new OptionalIntegerFormat[CP90](CP90.apply)

  implicit val cp91Format: Format[CP91] = new OptionalIntegerFormat[CP91](CP91.apply)

  implicit val cp91InputFormat: Format[CP91Input] = new OptionalIntegerFormat[CP91Input](CP91Input.apply)

  implicit val cp92Format: Format[CP92] = new OptionalIntegerFormat[CP92](CP92.apply)

  implicit val cp93Format: Format[CP93] = new OptionalIntegerFormat[CP93](CP93.apply)

  implicit val cp94Format: Format[CP94] = new IntegerFormat[CP94](CP94.apply)

  implicit val cp95Format: Format[CP95] = new OptionalIntegerFormat[CP95](CP95.apply)

  implicit val cp96Format: Format[CP96] = new OptionalIntegerFormat[CP96](CP96.apply)

  implicit val cp97Format: Format[CP97] = new IntegerFormat[CP97](CP97.apply)

  implicit val cp98Format: Format[CP98] = new OptionalIntegerFormat[CP98](CP98.apply)

  implicit val cp99Format: Format[CP99] = new IntegerFormat[CP99](CP99.apply)

  implicit val cp100Format: Format[CP100] = new IntegerFormat[CP100](CP100.apply)

  implicit val cp101Format: Format[CP101] = new OptionalIntegerFormat[CP101](CP101.apply)

  implicit val cp102Format: Format[CP102] = new OptionalIntegerFormat[CP102](CP102.apply)

  implicit val cp103Format: Format[CP103] = new OptionalIntegerFormat[CP103](CP103.apply)

  implicit val cp104Format: Format[CP104] = new OptionalIntegerFormat[CP104](CP104.apply)

  implicit val cp105Format: Format[CP105] = new IntegerFormat[CP105](CP105.apply)

  implicit val cp106Format: Format[CP106] = new OptionalIntegerFormat[CP106](CP106.apply)

  implicit val cp107Format: Format[CP107] = new OptionalIntegerFormat[CP107](CP107.apply)

  implicit val cp108Format: Format[CP108] = new OptionalIntegerFormat[CP108](CP108.apply)

  implicit val cp109Format: Format[CP109] = new IntegerFormat[CP109](CP109.apply)

  implicit val cp110Format: Format[CP110] = new IntegerFormat[CP110](CP110.apply)

  implicit val cp111Format: Format[CP111] = new IntegerFormat[CP111](CP111.apply)

  implicit val cp113Format: Format[CP113] = new IntegerFormat[CP113](CP113.apply)

  implicit val cp114Format: Format[CP114] = new IntegerFormat[CP114](CP114.apply)

  implicit val cp115Format: Format[CP115] = new IntegerFormat[CP115](CP115.apply)

  implicit val cp116Format: Format[CP116] = new IntegerFormat[CP116](CP116.apply)

  implicit val cp117Format: Format[CP117] = new IntegerFormat[CP117](CP117.apply)

  implicit val cp118Format: Format[CP118] = new IntegerFormat[CP118](CP118.apply)

  implicit val cp120Format: Format[CP120] = new OptionalBooleanFormat[CP120](CP120.apply)

  implicit val cp121Format: Format[CP121] = new OptionalIntegerFormat[CP121](CP121.apply)

  implicit val CP122Format: Format[CP122] = new OptionalIntegerFormat[CP122](CP122.apply)

  implicit val cp123Format: Format[CP123] = new OptionalIntegerFormat[CP123](CP123.apply)

  implicit val CP124Format: Format[CP124] = new OptionalIntegerFormat[CP124](CP124.apply)

  implicit val CP125Format: Format[CP125] = new OptionalIntegerFormat[CP125](CP125.apply)

  implicit val CP127Format: Format[CP127] = new OptionalIntegerFormat[CP127](CP127.apply)

  implicit val cp128Format: Format[CP128] = new OptionalBooleanFormat[CP128](CP128.apply)

  implicit val cp129Format: Format[CP129] = new OptionalBooleanFormat[CP129](CP129.apply)

  implicit val cp186Format: Format[CP186] = new OptionalIntegerFormat[CP186](CP186.apply)

  implicit val cp234Format: Format[CP234] = new OptionalIntegerFormat[CP234](CP234.apply)

  implicit val cp235Format: Format[CP235] = new OptionalIntegerFormat[CP235](CP235.apply)

  implicit val cp237Format: Format[CP237] = new OptionalIntegerFormat[CP237](CP237.apply)

  implicit val cp238Format: Format[CP238] = new OptionalIntegerFormat[CP238](CP238.apply)

  implicit val cp239Format: Format[CP239] = new IntegerFormat[CP239](CP239.apply)

  implicit val cp240Format: Format[CP240] = new OptionalIntegerFormat[CP240](CP240.apply)

  implicit val cp245Format: Format[CP245] = new OptionalIntegerFormat[CP245](CP245.apply)

  implicit val cp246Format: Format[CP246] = new OptionalIntegerFormat[CP246](CP246.apply)

  implicit val cp247Format: Format[CP247] = new OptionalIntegerFormat[CP247](CP247.apply)

  implicit val cp248Format: Format[CP248] = new OptionalIntegerFormat[CP248](CP248.apply)

  implicit val cp249Format: Format[CP249] = new OptionalIntegerFormat[CP249](CP249.apply)

  implicit val cp251Format: Format[CP251] = new IntegerFormat[CP251](CP251.apply)

  implicit val cp252Format: Format[CP252] = new OptionalIntegerFormat[CP252](CP252.apply)

  implicit val cp253Format: Format[CP253] = new IntegerFormat[CP253](CP253.apply)

  implicit val cp256Format: Format[CP256] = new IntegerFormat[CP256](CP256.apply)

  implicit val cp257Format: Format[CP257] = new OptionalIntegerFormat[CP257](CP257.apply)

  implicit val cp258Format: Format[CP258] = new IntegerFormat[CP258](CP258.apply)

  implicit val cp259Format: Format[CP259] = new IntegerFormat[CP259](CP259.apply)

  implicit val cp263Format: Format[CP263] = new OptionalIntegerFormat[CP263](CP263.apply)

  implicit val cp264Format: Format[CP264] = new IntegerFormat[CP264](CP264.apply)

  implicit val cp265Format: Format[CP265] = new IntegerFormat[CP265](CP265.apply)

  implicit val cp266Format: Format[CP266] = new IntegerFormat[CP266](CP266.apply)

  implicit val cp273Format: Format[CP273] = new IntegerFormat[CP273](CP273.apply)

  implicit val cp274Format: Format[CP274] = new IntegerFormat[CP274](CP274.apply)

  implicit val cp278Format: Format[CP278] = new IntegerFormat[CP278](CP278.apply)

  implicit val cp279Format: Format[CP279] = new OptionalIntegerFormat[CP279](CP279.apply)

  implicit val cp281Format: Format[CP281] = new OptionalIntegerFormat[CP281](CP281.apply)

  implicit val cp281aFormat: Format[CP281a] = new OptionalIntegerFormat[CP281a](CP281a.apply)

  implicit val cp281bFormat: Format[CP281b] = new OptionalIntegerFormat[CP281b](CP281b.apply)

  implicit val cp281cFormat: Format[CP281c] = new OptionalIntegerFormat[CP281c](CP281c.apply)

  implicit val cp281dFormat: Format[CP281d] = new OptionalIntegerFormat[CP281d](CP281d.apply)

  implicit val cp282Format: Format[CP282] = new OptionalIntegerFormat[CP282](CP282.apply)

  implicit val cp283Format: Format[CP283] = new OptionalIntegerFormat[CP283](CP283.apply)

  implicit val cp283aFormat: Format[CP283a] = new OptionalIntegerFormat[CP283a](CP283a.apply)

  implicit val cp283Formatb: Format[CP283b] = new OptionalIntegerFormat[CP283b](CP283b.apply)

  implicit val cp283Formatc: Format[CP283c] = new OptionalIntegerFormat[CP283c](CP283c.apply)

  implicit val cp283Formatd: Format[CP283d] = new OptionalIntegerFormat[CP283d](CP283d.apply)

  implicit val cp284Format: Format[CP284] = new OptionalIntegerFormat[CP284](CP284.apply)

  implicit val cp285Format: Format[CP285] = new OptionalDateFormat[CP285](CP285.apply)

  implicit val cp286Format: Format[CP286] = new OptionalIntegerFormat[CP286](CP286.apply)

  implicit val cp286aFormat: Format[CP286a] = new OptionalIntegerFormat[CP286a](CP286a.apply)

  implicit val cp286bFormat: Format[CP286b] = new OptionalIntegerFormat[CP286b](CP286b.apply)

  implicit val cp287Format: Format[CP287] = new OptionalIntegerFormat[CP287](CP287.apply)

  implicit val cp288Format: Format[CP288] = new OptionalIntegerFormat[CP288](CP288.apply)

  implicit val cp288aFormat: Format[CP288a] = new OptionalIntegerFormat[CP288a](CP288a.apply)

  implicit val cp288bFormat: Format[CP288b] = new OptionalIntegerFormat[CP288b](CP288b.apply)

  implicit val cp289Format: Format[CP289] = new OptionalIntegerFormat[CP289](CP289.apply)

  implicit val cp290Format: Format[CP290] = new OptionalIntegerFormat[CP290](CP290.apply)

  implicit val cp290aFormat: Format[CP290a] = new OptionalIntegerFormat[CP290a](CP290a.apply)

  implicit val cp290bFormat: Format[CP290b] = new OptionalIntegerFormat[CP290b](CP290b.apply)

  implicit val cp291Format: Format[CP291] = new OptionalIntegerFormat[CP291](CP291.apply)

  implicit val cp292Format: Format[CP292] = new IntegerFormat[CP292](CP292.apply)

  implicit val cp293Format: Format[CP293] = new IntegerFormat[CP293](CP293.apply)

  implicit val cp294Format: Format[CP294] = new IntegerFormat[CP294](CP294.apply)

  implicit val cp295Format: Format[CP295] = new IntegerFormat[CP295](CP295.apply)

  implicit val cp296Format: Format[CP296] = new OptionalIntegerFormat[CP296](CP296.apply)

  implicit val cp297Format: Format[CP297] = new OptionalIntegerFormat[CP297](CP297.apply)

  implicit val cp298Format: Format[CP298] = new OptionalIntegerFormat[CP298](CP298.apply)

  implicit val cp301Format: Format[CP301] = new OptionalIntegerFormat[CP301](CP301.apply)

  implicit val cp302Format: Format[CP302] = new OptionalIntegerFormat[CP302](CP302.apply)

  implicit val cp303Format: Format[CP303] = new OptionalIntegerFormat[CP303](CP303.apply)

  implicit val cp305Format: Format[CP305] = new IntegerFormat[CP305](CP305.apply)

  implicit val cp501Format: Format[CP501] = new OptionalIntegerFormat[CP501](CP501.apply)

  implicit val cp502Format: Format[CP502] = new OptionalIntegerFormat[CP502](CP502.apply)

  implicit val cp503Format: Format[CP503] = new OptionalIntegerFormat[CP503](CP503.apply)

  implicit val cp504Format: Format[CP504] = new IntegerFormat[CP504](CP504.apply)

  implicit val cp505Format: Format[CP505] = new OptionalIntegerFormat[CP505](CP505.apply)

  implicit val cp506Format: Format[CP506] = new OptionalIntegerFormat[CP506](CP506.apply)

  implicit val cp507Format: Format[CP507] = new IntegerFormat[CP507](CP507.apply)

  implicit val cp508Format: Format[CP508] = new IntegerFormat[CP508](CP508.apply)

  implicit val cp509Format: Format[CP509] = new IntegerFormat[CP509](CP509.apply)

  implicit val cp510Format: Format[CP510] = new OptionalIntegerFormat[CP510](CP510.apply)

  implicit val cp511Format: Format[CP511] = new IntegerFormat[CP511](CP511.apply)

  implicit val cp512Format: Format[CP512] = new IntegerFormat[CP512](CP512.apply)

  implicit val cp513Format: Format[CP513] = new OptionalIntegerFormat[CP513](CP513.apply)

  implicit val cp514Format: Format[CP514] = new IntegerFormat[CP514](CP514.apply)

  implicit val cp515Format: Format[CP515] = new OptionalIntegerFormat[CP515](CP515.apply)

  implicit val cp665Format: Format[CP665] = new OptionalIntegerFormat[CP665](CP665.apply)

  implicit val cp666Format: Format[CP666] = new OptionalIntegerFormat[CP666](CP666.apply)

  implicit val cp667Format: Format[CP667] = new OptionalIntegerFormat[CP667](CP667.apply)

  implicit val cp668Format: Format[CP668] = new OptionalIntegerFormat[CP668](CP668.apply)

  implicit val cp669Format: Format[CP669] = new OptionalIntegerFormat[CP669](CP669.apply)

  implicit val cp670Format: Format[CP670] = new OptionalIntegerFormat[CP670](CP670.apply)

  implicit val cp671Format: Format[CP671] = new OptionalIntegerFormat[CP671](CP671.apply)

  implicit val cp672Format: Format[CP672] = new OptionalIntegerFormat[CP672](CP672.apply)

  implicit val cp672aFormat: Format[CP672a] = new OptionalIntegerFormat[CP672a](CP672a.apply)

  implicit val cp673Format: Format[CP673] = new OptionalIntegerFormat[CP673](CP673.apply)

  implicit val cp674Format: Format[CP674] = new OptionalIntegerFormat[CP674](CP674.apply)

  implicit val cp675Format: Format[CP675] = new OptionalIntegerFormat[CP675](CP675.apply)

  implicit val cp676Format: Format[CP676] = new OptionalIntegerFormat[CP676](CP676.apply)

  implicit val cp677Format: Format[CP677] = new OptionalIntegerFormat[CP677](CP677.apply)

  implicit val cp678Format: Format[CP678] = new OptionalIntegerFormat[CP678](CP678.apply)

  implicit val cp679Format: Format[CP679] = new OptionalIntegerFormat[CP679](CP679.apply)

  implicit val cp680Format: Format[CP680] = new OptionalIntegerFormat[CP680](CP680.apply)

  implicit val cp980Format: Format[CP980] = new OptionalIntegerFormat[CP980](CP980.apply)

  implicit val cp981Format: Format[CP981] = new OptionalIntegerFormat[CP981](CP981.apply)

  implicit val cp982Format: Format[CP982] = new OptionalIntegerFormat[CP982](CP982.apply)

  implicit val cp983Format: Format[CP983] = new OptionalIntegerFormat[CP983](CP983.apply)

  implicit val cp997Format: Format[CP997] = new OptionalIntegerFormat[CP997](CP997.apply)

  implicit val cp997NIFormat: Format[CP997NI] = new OptionalIntegerFormat[CP997NI](CP997NI.apply)

  implicit val cp997cFormat: Format[CP997c] = new OptionalIntegerFormat[CP997c](CP997c.apply)

  implicit val cp997dFormat: Format[CP997d] = new OptionalIntegerFormat[CP997d](CP997d.apply)

  implicit val cp998Format: Format[CP998] = new OptionalIntegerFormat[CP998](CP998.apply)

  implicit val cp999Format: Format[CP999] = new IntegerFormat[CP999](CP999.apply)

  implicit val cp3010Format: Format[CP3010] = new OptionalIntegerFormat[CP3010](CP3010.apply)

  implicit val cp3020Format: Format[CP3020] = new OptionalIntegerFormat[CP3020](CP3020.apply)

  implicit val cp3030Format: Format[CP3030] = new OptionalIntegerFormat[CP3030](CP3030.apply)

  implicit val cpAux1Format: Format[CPAux1] = new IntegerFormat[CPAux1](CPAux1.apply)

  implicit val cpAux2Format: Format[CPAux2] = new IntegerFormat[CPAux2](CPAux2.apply)

  implicit val cpAux3Format: Format[CPAux3] = new IntegerFormat[CPAux3](CPAux3.apply)

  implicit val cpq1000Format: Format[CPQ1000] = new OptionalBooleanFormat[CPQ1000](CPQ1000.apply)

  implicit val cpq7Format: Format[CPQ7] = new OptionalBooleanFormat[CPQ7](CPQ7.apply)

  implicit val cpq8Format: Format[CPQ8] = new OptionalBooleanFormat[CPQ8](CPQ8.apply)

  implicit val cpq10Format: Format[CPQ10] = new OptionalBooleanFormat[CPQ10](CPQ10.apply)

  implicit val cpq11Format: Format[CPQ11] = new OptionalBooleanFormat[CPQ11](CPQ11.apply)

  implicit val cpq17Format: Format[CPQ17] = new OptionalBooleanFormat[CPQ17](CPQ17.apply)

  implicit val cpq18Format: Format[CPQ18] = new OptionalBooleanFormat[CPQ18](CPQ18.apply)

  implicit val cpq19Format: Format[CPQ19] = new OptionalBooleanFormat[CPQ19](CPQ19.apply)

  implicit val cpq20Format: Format[CPQ20] = new OptionalBooleanFormat[CPQ20](CPQ20.apply)

  implicit val cpq117Format: Format[CPQ117] = new OptionalBooleanFormat[CPQ117](CPQ117.apply)

  implicit val cpq21Format: Format[CPQ21] = new OptionalBooleanFormat[CPQ21](CPQ21.apply)

  implicit val cpq321Format: Format[CPQ321] = new OptionalBooleanFormat[CPQ321](CPQ321.apply)

  implicit val carFormatter: OFormat[Car] = Json.format[Car]

  lazy implicit val sba01Format: Format[SBA01] = Json.format[SBA01]

  lazy implicit val buildingFormat: OFormat[Building] = Json.format[Building]

  implicit val lec01Format: Format[LEC01] = Json.format[LEC01]

  implicit val cato01Format: Format[CATO01] = new IntegerFormat[CATO01](CATO01.apply)

  implicit val cato02Format: Format[CATO02] = new IntegerFormat[CATO02](CATO02.apply)

  implicit val cato03Format: Format[CATO03] = new IntegerFormat[CATO03](CATO03.apply)

  implicit val cato04Format: Format[CATO04] = new BigDecimalFormat[CATO04](CATO04.apply)

  implicit val cato05Format: Format[CATO05] = new BigDecimalFormat[CATO05](CATO05.apply)

  implicit val cato10Format: Format[CATO10] = new BooleanFormat[CATO10](CATO10.apply)

  implicit val cato11Format: Format[CATO11] = new OptionalStringFormat[CATO11](CATO11.apply)

  implicit val cato12Format: Format[CATO12] = new OptionalStringFormat[CATO12](CATO12.apply)

  implicit val cato13Format: Format[CATO13] = new IntegerFormat[CATO13](CATO13.apply)

  implicit val cato14Format: Format[CATO14] = new IntegerFormat[CATO14](CATO14.apply)

  implicit val cato15Format: Format[CATO15] = new IntegerFormat[CATO15](CATO15.apply)

  implicit val cato16Format: Format[CATO16] = new IntegerFormat[CATO16](CATO16.apply)

  implicit val cato19Format: Format[CATO19] = new BooleanFormat[CATO19](CATO19.apply)

  implicit val cato20Format: Format[CATO20] = new IntegerFormat[CATO20](CATO20.apply)

  implicit val cato21Format: Format[CATO21] = new BigDecimalFormat[CATO21](CATO21.apply)

  implicit val cato22Format: Format[CATO22] = new BigDecimalFormat[CATO22](CATO22.apply)

  implicit val cpSuperDeductionPercentageFormat: Format[CPSuperDeductionPercentage] = new BigDecimalFormat[CPSuperDeductionPercentage](CPSuperDeductionPercentage.apply)

  implicit val cpSuperDeductionOverlapFormat: Format[CPSuperDeductionOverlap] = new BooleanFormat[CPSuperDeductionOverlap](CPSuperDeductionOverlap.apply)

  implicit val cato23Format: Format[CATO23] = new IntegerFormat[CATO23](CATO23.apply)

}
