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

package uk.gov.hmrc.ct.ct600e.v2.retriever

import uk.gov.hmrc.ct.box.retriever.{FilingAttributesBoxValueRetriever, BoxValues, BoxRetriever}
import uk.gov.hmrc.ct.ct600e.v2._

object CT600EBoxRetriever extends BoxValues[CT600EBoxRetriever]

trait CT600EBoxRetriever extends BoxRetriever {

  def retrieveE1(): E1

  def retrieveE2(): E2

  def retrieveE3(): E3

  def retrieveE4(): E4

  def retrieveE5(): E5

  def retrieveE6(): E6

  def retrieveE7(): E7

  def retrieveE8(): E8

  def retrieveE9(): E9

  def retrieveE10(): E10

  def retrieveE11(): E11

  def retrieveE12(): E12

  def retrieveE13(): E13

  def retrieveE14(): E14

  def retrieveE15(): E15

  def retrieveE16(): E16

  def retrieveE17(): E17

  def retrieveE18(): E18

  def retrieveE19(): E19

  def retrieveE20(): E20

  def retrieveE20a(): E20a

  def retrieveE21(): E21

  def retrieveE21b(): E21b

  def retrieveE22(): E22

  def retrieveE22c(): E22c

  def retrieveE23(): E23

  def retrieveE23d(): E23d

  def retrieveE24e(): E24e

  def retrieveE25f(): E25f

  def retrieveE26(): E26

  def retrieveE27(): E27

  def retrieveE28(): E28

  def retrieveE1000(): E1000

  def retrieveE1001(): E1001

  def retrieveE1010(): E1010

  def retrieveE1011(): E1011

  def retrieveE1012(): E1012

  def retrieveE1013(): E1013


}
