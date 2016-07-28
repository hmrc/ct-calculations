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

  def ac16(): AC16

  def ac17(): AC17

  def ac18(): AC18

  def ac19(): AC19

  def ac20(): AC20

  def ac21(): AC21

  def ac26(): AC26 = AC26.calculate(this)

  def ac27(): AC27 = AC27.calculate(this)

  def ac28(): AC28

  def ac29(): AC29

  def ac30(): AC30

  def ac31(): AC31

  def ac32(): AC32 = AC32.calculate(this)

  def ac33(): AC33 = AC33.calculate(this)

  def ac34(): AC34

  def ac35(): AC35

  def ac36(): AC36 = AC36.calculate(this)

  def ac37(): AC37 = AC37.calculate(this)

  def ac5032(): AC5032

  def ac8021(): AC8021

  def retrieveDirectors(): Directors

  def ac8033(): AC8033

  def ac8023(): AC8023

  def acQ8003(): ACQ8003

  def acQ8009(): ACQ8009
  
  def ac8051(): AC8051

  def ac8052(): AC8052

  def ac8053(): AC8053

  def ac8054(): AC8054

  def acQ8161(): ACQ8161
}
