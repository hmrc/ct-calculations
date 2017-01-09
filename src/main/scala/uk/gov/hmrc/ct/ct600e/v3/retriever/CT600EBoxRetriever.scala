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

package uk.gov.hmrc.ct.ct600e.v3.retriever

import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.ct600e.v3._

trait CT600EBoxRetriever extends BoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def e1(): E1

  def e2(): E2 = E2(utr())

  def e3(): E3

  def e4(): E4

  def e5(): E5

  def e10(): E10

  def e15(): E15

  def e20(): E20

  def e25(): E25 = E25.calculate(this)

  def e30(): E30

  def e35(): E35

  def e40(): E40

  def e45(): E45

  def e50(): E50

  def e55(): E55

  def e60(): E60

  def e65(): E65

  def e70(): E70

  def e75(): E75

  def e80(): E80

  def e85(): E85

  def e90(): E90 = E90.calculate(this)

  def e95(): E95

  def e100(): E100

  def e105(): E105

  def e110(): E110

  def e115(): E115

  def e120(): E120

  def e125(): E125 = E125.calculate(this)

  def e130(): E130

  def e135(): E135

  def e140(): E140

  def e145(): E145

  def e150(): E150

  def e155(): E155

  def e160(): E160

  def e165(): E165

  def e170(): E170 = E170.calculate(this)

  def e170A() : E170A

  def e170B() : E170B

  def e175(): E175

  def e180(): E180

  def e185(): E185

  def e190(): E190

  def e1003(): E1003 = E1003.calculate(this)

  def e1005(): E1005 = E1005.calculate(this)

  def e1013(): E1013 = E1013.calculate(this)

  def e1015(): E1015 = E1015.calculate(this)

}
