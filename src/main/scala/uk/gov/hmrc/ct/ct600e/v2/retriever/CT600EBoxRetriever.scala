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

package uk.gov.hmrc.ct.ct600e.v2.retriever

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600e.v2._

trait CT600EBoxRetriever extends BoxRetriever {

  def e1(): E1

  def e2(): E2

  def e3(): E3

  def e4(): E4

  def e5(): E5

  def e6(): E6

  def e7(): E7

  def e8(): E8

  def e9(): E9

  def e10(): E10

  def e11(): E11

  def e12(): E12

  def e13(): E13

  def e14(): E14

  def e15(): E15

  def e16(): E16

  def e17(): E17

  def e18(): E18

  def e19(): E19

  def e20(): E20

  def e20a(): E20a

  def e21(): E21

  def e21b(): E21b

  def e22(): E22

  def e22c(): E22c

  def e23(): E23

  def e23d(): E23d

  def e24e(): E24e = E24e.calculate(this)

  def e24eA(): E24eA

  def e24eB(): E24eB

  def e25f(): E25f

  def e26(): E26

  def e27(): E27

  def e28(): E28

  def e1000(): E1000

  def e1001(): E1001

  def e1010(): E1010

  def e1011(): E1011

  def e1012(): E1012 = E1012.calculate(this)

  def e1013(): E1013

  def e1020(): E1020

  def e1021(): E1021

  def e1022(): E1022

  def e1023(): E1023

  def e1030() : E1030

  def e1031() : E1031

  def e1032() : E1032

  def e1033(): E1033 = E1033.calculate(this)

  def e1035(): E1035 = E1035.calculate(this)

  def e1043(): E1043 = E1043.calculate(this)

  def e1045(): E1045 = E1045.calculate(this)
}
