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

package uk.gov.hmrc.ct.accounts.retriever

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.box.retriever.{FilingAttributesBoxValueRetriever, BoxRetriever, BoxValues}

object AccountsBoxRetriever extends BoxValues[AccountsBoxRetriever]

trait AccountsBoxRetriever extends BoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def retrieveAC1(): AC1

  def retrieveAC3(): AC3
  
  def retrieveAC4(): AC4

  def retrieveAC12(): AC12

  def retrieveAC13(): AC13

  def retrieveAC14(): AC14

  def retrieveAC15(): AC15

  def retrieveAC16(): AC16 = AC16.calculate(this)

  def retrieveAC17(): AC17 = AC17.calculate(this)

  def retrieveAC18(): AC18

  def retrieveAC19(): AC19

  def retrieveAC20(): AC20

  def retrieveAC21(): AC21

  def retrieveAC22(): AC22

  def retrieveAC23(): AC23

  def retrieveAC26(): AC26 = AC26.calculate(this)

  def retrieveAC27(): AC27 = AC27.calculate(this)

  def retrieveAC28(): AC28

  def retrieveAC29(): AC29

  def retrieveAC30(): AC30

  def retrieveAC31(): AC31

  def retrieveAC32(): AC32 = AC32.calculate(this)

  def retrieveAC33(): AC33 = AC33.calculate(this)

  def retrieveAC34(): AC34

  def retrieveAC35(): AC35

  def retrieveAC36(): AC36 = AC36.calculate(this)

  def retrieveAC37(): AC37 = AC37.calculate(this)

  def retrieveAC38(): AC38

  def retrieveAC39(): AC39

  def retrieveAC40(): AC40 = AC40.calculate(this)

  def retrieveAC41(): AC41 = AC41.calculate(this)

  def retrieveAC405(): AC405

  def retrieveAC406(): AC406

  def retrieveAC410(): AC410

  def retrieveAC411(): AC411

  def retrieveAC415(): AC415

  def retrieveAC416(): AC416

  def retrieveAC420(): AC420

  def retrieveAC421(): AC421

  def retrieveAC425(): AC425

  def retrieveAC426(): AC426

  def retrieveAC435(): AC435 = AC435.calculate(this)

  def retrieveAC436(): AC436 = AC436.calculate(this)

  def retrieveAC205(): AC205

  def retrieveAC206(): AC206
}
