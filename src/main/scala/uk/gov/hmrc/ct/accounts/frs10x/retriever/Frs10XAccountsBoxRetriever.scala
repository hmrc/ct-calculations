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

  def retrieveAC42(): AC42

  def retrieveAC43(): AC43

  def retrieveAC42(): AC42

  def retrieveAC43(): AC43

  def retrieveAC42(): AC42

  def retrieveAC43(): AC43

  def retrieveAC42(): AC42

  def retrieveAC43(): AC43

  def retrieveAC44(): AC44

  def retrieveAC45(): AC45

  def retrieveAC50(): AC50

  def retrieveAC51(): AC51

  def retrieveAC52(): AC52

  def retrieveAC53(): AC53

  def retrieveAC54(): AC54

  def retrieveAC55(): AC55

  def retrieveAC1076(): AC1076

  def retrieveAC1077(): AC1077

  def retrieveAC58(): AC58

  def retrieveAC59(): AC59

  def retrieveAC64(): AC64

  def retrieveAC65(): AC65

  def retrieveAC66(): AC66

  def retrieveAC67(): AC67

  def retrieveAC1178(): AC1178

  def retrieveAC1179(): AC1179

  def retrieveAC70(): AC70

  def retrieveAC71(): AC71

  def retrieveAC74(): AC74

  def retrieveAC75(): AC75

  def retrieveAC76(): AC76

  def retrieveAC77(): AC77

  def retrieveAC5032(): AC5032

  def retrieveAC8021(): AC8021

  def retrieveDirectors(): Directors

  def retrieveAC8033(): AC8033

  def retrieveAC8023(): AC8023

  def retrieveACQ8003(): ACQ8003

  def retrieveACQ8009(): ACQ8009
  
  def retrieveAC8051(): AC8051

  def retrieveAC8052(): AC8052

  def retrieveAC8053(): AC8053

  def retrieveAC8054(): AC8054

  def retrieveACQ8161(): ACQ8161
}
