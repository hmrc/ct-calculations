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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever

import uk.gov.hmrc.ct.accounts.frs10x.abridged._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait AbridgedAccountsBoxRetriever extends AccountsBoxRetriever {

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

  def ac42(): AC42

  def ac43(): AC43

  def ac44(): AC44

  def ac45(): AC45

  def ac48(): AC48 = AC48.calculate(this)

  def ac49(): AC49 = AC49.calculate(this)

  def ac50(): AC50

  def ac51(): AC51

  def ac52(): AC52

  def ac53(): AC53

  def ac54(): AC54

  def ac55(): AC55

  def ac56(): AC56 = AC56.calculate(this)

  def ac57(): AC57 = AC57.calculate(this)

  def ac58(): AC58

  def ac59(): AC59

  def ac60(): AC60 = AC60.calculate(this)

  def ac61(): AC61 = AC61.calculate(this)

  def ac62(): AC62 = AC62.calculate(this)

  def ac63(): AC63 = AC63.calculate(this)

  def ac64(): AC64

  def ac65(): AC65

  def ac66(): AC66

  def ac67(): AC67

  def ac68(): AC68 = AC68.calculate(this)

  def ac69(): AC69 = AC69.calculate(this)

  def ac70(): AC70

  def ac71(): AC71

  def ac74(): AC74

  def ac75(): AC75

  def ac76(): AC76

  def ac77(): AC77

  def ac80(): AC80 = AC80.calculate(this)

  def ac81(): AC81 = AC81.calculate(this)

  def ac1076(): AC1076

  def ac1077(): AC1077

  def ac1178(): AC1178

  def ac1179(): AC1179

  def ac5032(): AC5032

  def ac5052A(): AC5052A

  def ac5052B(): AC5052B

  def ac5058A(): AC5058A

  def ac5064A(): AC5064A

  def ac7100(): AC7100

  def ac7200(): AC7200

  def ac7210A(): AC7210A

  def ac7210B(): AC7210B

  def ac7300(): AC7300

  def ac7400(): AC7400

  def ac7401(): AC7401

  def ac7500(): AC7500

  def ac7600(): AC7600

  def ac7800(): AC7800

  def ac7900(): AC7900
}
