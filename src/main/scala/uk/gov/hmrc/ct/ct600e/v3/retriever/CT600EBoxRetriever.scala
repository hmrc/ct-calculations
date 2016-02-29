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

package uk.gov.hmrc.ct.ct600e.v3.retriever

import uk.gov.hmrc.ct.box.retriever.{FilingAttributesBoxValueRetriever, BoxValues, BoxRetriever}
import uk.gov.hmrc.ct.ct600e.v3._

trait CT600EBoxRetriever extends BoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def retrieveE1(): E1

  def retrieveE2(): E2 = E2(retrieveUTR())

  def retrieveE3(): E3

  def retrieveE4(): E4

  def retrieveE5(): E5

  def retrieveE10(): E10

  def retrieveE15(): E15

  def retrieveE20(): E20

  def retrieveE25(): E25 = E25.calculate(this)

  def retrieveE30(): E30

  def retrieveE35(): E35

  def retrieveE40(): E40

  def retrieveE45(): E45

  def retrieveE50(): E50

  def retrieveE55(): E55

  def retrieveE60(): E60

  def retrieveE65(): E65

  def retrieveE70(): E70

  def retrieveE75(): E75

  def retrieveE80(): E80

  def retrieveE85(): E85

  def retrieveE90(): E90 = E90.calculate(this)

  def retrieveE95(): E95

  def retrieveE100(): E100

  def retrieveE105(): E105

  def retrieveE110(): E110

  def retrieveE115(): E115

  def retrieveE120(): E120

  def retrieveE125(): E125 = E125.calculate(this)

  def retrieveE130(): E130

  def retrieveE135(): E135

  def retrieveE140(): E140

  def retrieveE145(): E145

  def retrieveE150(): E150

  def retrieveE155(): E155

  def retrieveE160(): E160

  def retrieveE165(): E165

  def retrieveE170(): E170 = E170.calculate(this)

  def retrieveE170A() : E170A

  def retrieveE170B() : E170B

  def retrieveE175(): E175

  def retrieveE180(): E180

  def retrieveE185(): E185

  def retrieveE190(): E190

  def retrieveE1003(): E1003 = E1003.calculate(this)

  def retrieveE1005(): E1005 = E1005.calculate(this)

  def retrieveE1013(): E1013 = E1013.calculate(this)

  def retrieveE1015(): E1015 = E1015.calculate(this)

}
