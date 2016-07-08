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

package uk.gov.hmrc.ct.accounts.frs10x.retriever


import uk.gov.hmrc.ct.accounts.frs10x._
import uk.gov.hmrc.ct.accounts.frs10x.abridged._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, BoxValues, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}

trait Frs10xAccountsBoxRetriever extends AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def retrieveAC16(): AC16

  def retrieveAC17(): AC17

  def retrieveAC18(): AC18

  def retrieveAC19(): AC19

  def retrieveAC20(): AC20

  def retrieveAC21(): AC21

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

  def retrieveAC8021(): AC8021

  def retrieveAC8023(): AC8023

  def retrieveAC8051(): AC8051

  def retrieveAC8052(): AC8052

  def retrieveAC8053(): AC8053

  def retrieveAC8054(): AC8054

  def retrieveACQ8161(): ACQ8161
}
