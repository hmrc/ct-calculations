/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs102.retriever

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors.LoansToDirectors
import uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions.RelatedPartyTransactions
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs102AccountsBoxRetriever extends Frs10xAccountsBoxRetriever {

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

  def ac106(): AC106

  def ac107(): AC107

  def ac106A(): AC106A

  def ac125(): AC125

  def ac126(): AC126

  def ac130(): AC130

  def ac131(): AC131 = AC131.calculate(this)

  def ac132(): AC132 = AC132.calculate(this)

  def ac200a(): AC200A

  def ac200(): AC200

  def ac212(): AC212

  def ac213(): AC213

  def ac214(): AC214

  def ac217(): AC217 = AC217.calculate(this)

  def ac219(): AC219

  def ac115(): AC115

  def ac116(): AC116

  def ac117(): AC117 = AC117.calculate(this)

  def ac119(): AC119

  def ac120(): AC120

  def ac121(): AC121 = AC121.calculate(this)

  def ac122(): AC122 = AC122.calculate(this)

  def ac209(): AC209

  def ac210(): AC210

  def ac211(): AC211

  def ac320(): AC320

  def ac320A(): AC320A

  def ac321(): AC321

  def ac322(): AC322

  def ac323(): AC323

  def ac324(): AC324

  def ac7110A(): AC7110A

  def ac138B(): AC138B

  def ac139B(): AC139B

  def ac150B(): AC150B

  def ac151B(): AC151B

  def ac5032(): AC5032

  def ac5052A(): AC5052A

  def ac5052B(): AC5052B

  def ac5052C(): AC5052C

  def ac5058A(): AC5058A

  def ac5064A(): AC5064A

  def ac124(): AC124

  def ac128(): AC128

  def ac133(): AC133 = AC133.calculate(this)

  def ac5133(): AC5133

  def ac187(): AC187

  def ac188(): AC188 = AC188(ac77())

  def ac189(): AC189

  def ac190(): AC190 = AC190.calculate(this)

  def ac5076C(): AC5076C

  def ac114(): AC114

  def ac118(): AC118

  def ac123(): AC123 = AC123.calculate(this)

  def ac5123(): AC5123

  def ac7100(): AC7100

  def ac7200(): AC7200

  def ac7210A(): AC7210A

  def ac7210B(): AC7210B

  def ac7300(): AC7300

  def ac7400(): AC7400

  def ac7401(): AC7401

  def ac7500(): AC7500

  def ac7600(): AC7600

  def ac7601(): AC7601

  def ac7800(): AC7800

  def loansToDirectors(): LoansToDirectors

  def ac7900(): AC7900

  def ac8084(): AC8084

  def ac8085(): AC8085

  def ac7901(): AC7901

  def relatedPartyTransactions(): RelatedPartyTransactions
}
