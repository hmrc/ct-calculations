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

package uk.gov.hmrc.ct.accounts.frs102.retriever

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait FullAccountsBoxRetriever extends Frs102AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def ac13(): AC13

  def ac14(): AC14

  def ac15(): AC15

  def ac16(): AC16 = AC16.calculate(this)

  def ac17(): AC17 = AC17.calculate(this)

  def ac22(): AC22

  def ac23(): AC23

  override def ac114(): AC114 = AC114.calculate(this)

  def ac114A(): AC114A

  def ac114B(): AC114B

  override def ac115(): AC115 = AC115.calculate(this)

  def ac115A(): AC115A

  def ac115B(): AC115B

  override def ac116(): AC116 = AC116.calculate(this)

  def ac116A(): AC116A

  def ac116B(): AC116B

  def ac117A(): AC117A = AC117A.calculate(this)

  def ac117B(): AC117B = AC117B.calculate(this)

  override def ac118(): AC118 = AC118.calculate(this)

  def ac118A(): AC118A

  def ac118B(): AC118B

  override def ac119(): AC119 = AC119.calculate(this)

  def ac119A(): AC119A

  def ac119B(): AC119B

  override def ac120(): AC120 = AC120.calculate(this)

  def ac120A(): AC120A

  def ac120B(): AC120B

  def ac121A(): AC121A = AC121A.calculate(this)

  def ac121B(): AC121B = AC121B.calculate(this)

  def ac122A(): AC122A = AC122A.calculate(this)

  def ac122B(): AC122B = AC122B.calculate(this)

  def ac123A(): AC123A = AC123A.calculate(this)

  def ac123B(): AC123B = AC123B.calculate(this)

  override def ac124(): AC124 = AC124.calculate(this)

  def ac124A(): AC124A

  def ac124B(): AC124B

  def ac124C(): AC124C

  def ac124D(): AC124D

  def ac124E(): AC124E

  override def ac125(): AC125 = AC125.calculate(this)

  def ac125A(): AC125A

  def ac125B(): AC125B

  def ac125C(): AC125C

  def ac125D(): AC125D

  def ac125E(): AC125E

  override def ac126(): AC126 = AC126.calculate(this)

  def ac126A(): AC126A

  def ac126B(): AC126B

  def ac126C(): AC126C

  def ac126D(): AC126D

  def ac126E(): AC126E

  def ac127(): AC127 = AC127.calculate(this)

  def ac127A(): AC127A = AC127A.calculate(this)

  def ac127B(): AC127B = AC127B.calculate(this)

  def ac127C(): AC127C = AC127C.calculate(this)

  def ac127D(): AC127D = AC127D.calculate(this)

  def ac127E(): AC127E = AC127E.calculate(this)

  override def ac128(): AC128 = AC128.calculate(this)

  def ac128A(): AC128A

  def ac128B(): AC128B

  def ac128C(): AC128C

  def ac128D(): AC128D

  def ac128E(): AC128E

  def ac129(): AC129 = AC129.calculate(this)

  def ac129A(): AC129A

  def ac129B(): AC129B

  def ac129C(): AC129C

  def ac129D(): AC129D

  def ac129E(): AC129E

  override def ac130(): AC130 = AC130.calculate(this)

  def ac130A(): AC130A

  def ac130B(): AC130B

  def ac130C(): AC130C

  def ac130D(): AC130D

  def ac130E(): AC130E

  def ac131A(): AC131A = AC131A.calculate(this)

  def ac131B(): AC131B = AC131B.calculate(this)

  def ac131C(): AC131C = AC131C.calculate(this)

  def ac131D(): AC131D = AC131D.calculate(this)

  def ac131E(): AC131E = AC131E.calculate(this)

  def ac132A(): AC132A = AC132A.calculate(this)

  def ac132B(): AC132B = AC132B.calculate(this)

  def ac132C(): AC132C = AC132C.calculate(this)

  def ac132D(): AC132D = AC132D.calculate(this)

  def ac132E(): AC132E = AC132E.calculate(this)

  def ac133A(): AC133A = AC133A.calculate(this)

  def ac133B(): AC133B = AC133B.calculate(this)

  def ac133C(): AC133C = AC133C.calculate(this)

  def ac133D(): AC133D = AC133D.calculate(this)

  def ac133E(): AC133E = AC133E.calculate(this)

  def ac134(): AC134

  def ac135(): AC135

  def ac138(): AC138

  def ac139(): AC139

  def ac136(): AC136

  def ac137(): AC137

  def ac140(): AC140 = AC140.calculate(this)

  def ac141(): AC141 = AC141.calculate(this)

  def ac142(): AC142

  def ac143(): AC143

  def ac144(): AC144

  def ac145(): AC145

  def ac146(): AC146

  def ac147(): AC147

  def ac148(): AC148

  def ac149(): AC149

  def ac150(): AC150

  def ac151(): AC151

  def ac152(): AC152

  def ac153(): AC153

  def ac154(): AC154 = AC154.calculate(this)

  def ac155(): AC155 = AC155.calculate(this)

  def ac156(): AC156

  def ac157(): AC157

  def ac158(): AC158

  def ac159(): AC159

  def ac160(): AC160

  def ac161(): AC161

  def ac162(): AC162 = AC162.calculate(this)

  def ac163(): AC163 = AC163.calculate(this)

  override def ac209(): AC209 = AC209.calculate(this)

  def ac209A(): AC209A

  def ac209B(): AC209B

  override def ac210(): AC210 = AC210.calculate(this)

  def ac210A(): AC210A

  def ac210B(): AC210B

  override def ac211(): AC211 = AC211.calculate(this)

  def ac211A(): AC211A

  def ac211B(): AC211B

  override def ac212(): AC212 = AC212.calculate(this)

  def ac212A(): AC212A

  def ac212B(): AC212B

  def ac212C(): AC212C

  def ac212D(): AC212D

  def ac212E(): AC212E

  override def ac213(): AC213 = AC213.calculate(this)

  def ac213A(): AC213A

  def ac213B(): AC213B

  def ac213C(): AC213C

  def ac213D(): AC213D

  def ac213E(): AC213E

  override def ac214(): AC214 = AC214.calculate(this)

  def ac214A(): AC214A

  def ac214B(): AC214B

  def ac214C(): AC214C

  def ac214D(): AC214D

  def ac214E(): AC214E

  def ac7210(): AC7210

  def acq5021(): ACQ5021

  def acq5022(): ACQ5022

  def acq5031(): ACQ5031

  def acq5032(): ACQ5032

  def acq5033(): ACQ5033

  def acq5034(): ACQ5034

  def acq5035(): ACQ5035
}
