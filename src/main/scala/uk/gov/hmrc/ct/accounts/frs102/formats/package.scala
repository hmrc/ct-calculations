/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs102

import play.api.libs.json.{JsResult, JsValue, Reads, _}
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors._
import uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions._
import uk.gov.hmrc.ct.box.formats._

package object formats {

  private def withDefault[A](key:String, default:A)(implicit writes:Writes[A]) = __.json.update((__ \ key).json.copyFrom((__ \ key).json.pick orElse Reads.pure(Json.toJson(default))))

  implicit val ac18Format: OptionalIntegerFormat[AC18] = new OptionalIntegerFormat[AC18](AC18.apply)
  implicit val ac19Format: OptionalIntegerFormat[AC19] = new OptionalIntegerFormat[AC19](AC19.apply)
  implicit val ac20Format: OptionalIntegerFormat[AC20] = new OptionalIntegerFormat[AC20](AC20.apply)
  implicit val ac21Format: OptionalIntegerFormat[AC21] = new OptionalIntegerFormat[AC21](AC21.apply)
  implicit val ac22Format: OptionalIntegerFormat[AC22] = new OptionalIntegerFormat[AC22](AC22.apply)
  implicit val ac23Format: OptionalIntegerFormat[AC23] = new OptionalIntegerFormat[AC23](AC23.apply)
  implicit val ac28Format: OptionalIntegerFormat[AC28] = new OptionalIntegerFormat[AC28](AC28.apply)
  implicit val ac29Format: OptionalIntegerFormat[AC29] = new OptionalIntegerFormat[AC29](AC29.apply)
  implicit val ac30Format: OptionalIntegerFormat[AC30] = new OptionalIntegerFormat[AC30](AC30.apply)
  implicit val ac31Format: OptionalIntegerFormat[AC31] = new OptionalIntegerFormat[AC31](AC31.apply)
  implicit val ac34Format: OptionalIntegerFormat[AC34] = new OptionalIntegerFormat[AC34](AC34.apply)
  implicit val ac35Format: OptionalIntegerFormat[AC35] = new OptionalIntegerFormat[AC35](AC35.apply)
  implicit val ac42Format: OptionalIntegerFormat[AC42] = new OptionalIntegerFormat[AC42](AC42.apply)
  implicit val ac43Format: OptionalIntegerFormat[AC43] = new OptionalIntegerFormat[AC43](AC43.apply)
  implicit val ac44Format: OptionalIntegerFormat[AC44] = new OptionalIntegerFormat[AC44](AC44.apply)
  implicit val ac45Format: OptionalIntegerFormat[AC45] = new OptionalIntegerFormat[AC45](AC45.apply)
  implicit val ac50Format: OptionalIntegerFormat[AC50] = new OptionalIntegerFormat[AC50](AC50.apply)
  implicit val ac51Format: OptionalIntegerFormat[AC51] = new OptionalIntegerFormat[AC51](AC51.apply)
  implicit val ac52Format: OptionalIntegerFormat[AC52] = new OptionalIntegerFormat[AC52](AC52.apply)
  implicit val ac53Format: OptionalIntegerFormat[AC53] = new OptionalIntegerFormat[AC53](AC53.apply)
  implicit val ac54Format: OptionalIntegerFormat[AC54] = new OptionalIntegerFormat[AC54](AC54.apply)
  implicit val ac55Format: OptionalIntegerFormat[AC55] = new OptionalIntegerFormat[AC55](AC55.apply)
  implicit val ac58Format: OptionalIntegerFormat[AC58] = new OptionalIntegerFormat[AC58](AC58.apply)
  implicit val ac59Format: OptionalIntegerFormat[AC59] = new OptionalIntegerFormat[AC59](AC59.apply)
  implicit val ac64Format: OptionalIntegerFormat[AC64] = new OptionalIntegerFormat[AC64](AC64.apply)
  implicit val ac65Format: OptionalIntegerFormat[AC65] = new OptionalIntegerFormat[AC65](AC65.apply)
  implicit val ac66Format: OptionalIntegerFormat[AC66] = new OptionalIntegerFormat[AC66](AC66.apply)
  implicit val ac67Format: OptionalIntegerFormat[AC67] = new OptionalIntegerFormat[AC67](AC67.apply)
  implicit val ac70Format: OptionalIntegerFormat[AC70] = new OptionalIntegerFormat[AC70](AC70.apply)
  implicit val ac71Format: OptionalIntegerFormat[AC71] = new OptionalIntegerFormat[AC71](AC71.apply)
  implicit val ac74Format: OptionalIntegerFormat[AC74] = new OptionalIntegerFormat[AC74](AC74.apply)
  implicit val ac75Format: OptionalIntegerFormat[AC75] = new OptionalIntegerFormat[AC75](AC75.apply)
  implicit val ac76Format: OptionalIntegerFormat[AC76] = new OptionalIntegerFormat[AC76](AC76.apply)
  implicit val ac77Format: OptionalIntegerFormat[AC77] = new OptionalIntegerFormat[AC77](AC77.apply)
  implicit val ac106Format: OptionalIntegerFormat[AC106] = new OptionalIntegerFormat[AC106](AC106.apply)
  implicit val ac106AFormat: OptionalStringFormat[AC106A] = new OptionalStringFormat[AC106A](AC106A.apply)
  implicit val ac107Format: OptionalIntegerFormat[AC107] = new OptionalIntegerFormat[AC107](AC107.apply)
  implicit val ac125Format: OptionalIntegerFormat[AC125] = new OptionalIntegerFormat[AC125](AC125.apply)
  implicit val ac125AFormat: OptionalIntegerFormat[AC125A] = new OptionalIntegerFormat[AC125A](AC125A.apply)
  implicit val ac125BFormat: OptionalIntegerFormat[AC125B] = new OptionalIntegerFormat[AC125B](AC125B.apply)
  implicit val ac125CFormat: OptionalIntegerFormat[AC125C] = new OptionalIntegerFormat[AC125C](AC125C.apply)
  implicit val ac125DFormat: OptionalIntegerFormat[AC125D] = new OptionalIntegerFormat[AC125D](AC125D.apply)
  implicit val ac125EFormat: OptionalIntegerFormat[AC125E] = new OptionalIntegerFormat[AC125E](AC125E.apply)
  implicit val ac126Format: OptionalIntegerFormat[AC126] = new OptionalIntegerFormat[AC126](AC126.apply)
  implicit val ac126AFormat: OptionalIntegerFormat[AC126A] = new OptionalIntegerFormat[AC126A](AC126A.apply)
  implicit val ac126BFormat: OptionalIntegerFormat[AC126B] = new OptionalIntegerFormat[AC126B](AC126B.apply)
  implicit val ac126CFormat: OptionalIntegerFormat[AC126C] = new OptionalIntegerFormat[AC126C](AC126C.apply)
  implicit val ac126DFormat: OptionalIntegerFormat[AC126D] = new OptionalIntegerFormat[AC126D](AC126D.apply)
  implicit val ac126EFormat: OptionalIntegerFormat[AC126E] = new OptionalIntegerFormat[AC126E](AC126E.apply)
  implicit val ac127Format: OptionalIntegerFormat[AC127] = new OptionalIntegerFormat[AC127](AC127.apply)
  implicit val ac127AFormat: OptionalIntegerFormat[AC127A] = new OptionalIntegerFormat[AC127A](AC127A.apply)
  implicit val ac127BFormat: OptionalIntegerFormat[AC127B] = new OptionalIntegerFormat[AC127B](AC127B.apply)
  implicit val ac127CFormat: OptionalIntegerFormat[AC127C] = new OptionalIntegerFormat[AC127C](AC127C.apply)
  implicit val ac127DFormat: OptionalIntegerFormat[AC127D] = new OptionalIntegerFormat[AC127D](AC127D.apply)
  implicit val ac127EFormat: OptionalIntegerFormat[AC127E] = new OptionalIntegerFormat[AC127E](AC127E.apply)
  implicit val ac130Format: OptionalIntegerFormat[AC130] = new OptionalIntegerFormat[AC130](AC130.apply)
  implicit val ac130AFormat: OptionalIntegerFormat[AC130A] = new OptionalIntegerFormat[AC130A](AC130A.apply)
  implicit val ac130BFormat: OptionalIntegerFormat[AC130B] = new OptionalIntegerFormat[AC130B](AC130B.apply)
  implicit val ac130CFormat: OptionalIntegerFormat[AC130C] = new OptionalIntegerFormat[AC130C](AC130C.apply)
  implicit val ac130DFormat: OptionalIntegerFormat[AC130D] = new OptionalIntegerFormat[AC130D](AC130D.apply)
  implicit val ac130EFormat: OptionalIntegerFormat[AC130E] = new OptionalIntegerFormat[AC130E](AC130E.apply)
  implicit val ac131Format: OptionalIntegerFormat[AC131] = new OptionalIntegerFormat[AC131](AC131.apply)
  implicit val ac131AFormat: OptionalIntegerFormat[AC131A] = new OptionalIntegerFormat[AC131A](AC131A.apply)
  implicit val ac131BFormat: OptionalIntegerFormat[AC131B] = new OptionalIntegerFormat[AC131B](AC131B.apply)
  implicit val ac131CFormat: OptionalIntegerFormat[AC131C] = new OptionalIntegerFormat[AC131C](AC131C.apply)
  implicit val ac131DFormat: OptionalIntegerFormat[AC131D] = new OptionalIntegerFormat[AC131D](AC131D.apply)
  implicit val ac131EFormat: OptionalIntegerFormat[AC131E] = new OptionalIntegerFormat[AC131E](AC131E.apply)
  implicit val ac132Format: OptionalIntegerFormat[AC132] = new OptionalIntegerFormat[AC132](AC132.apply)
  implicit val ac132AFormat: OptionalIntegerFormat[AC132A] = new OptionalIntegerFormat[AC132A](AC132A.apply)
  implicit val ac132BFormat: OptionalIntegerFormat[AC132B] = new OptionalIntegerFormat[AC132B](AC132B.apply)
  implicit val ac132CFormat: OptionalIntegerFormat[AC132C] = new OptionalIntegerFormat[AC132C](AC132C.apply)
  implicit val ac132DFormat: OptionalIntegerFormat[AC132D] = new OptionalIntegerFormat[AC132D](AC132D.apply)
  implicit val ac132EFormat: OptionalIntegerFormat[AC132E] = new OptionalIntegerFormat[AC132E](AC132E.apply)
  implicit val ac134Format: OptionalIntegerFormat[AC134] = new OptionalIntegerFormat[AC134](AC134.apply)
  implicit val ac135Format: OptionalIntegerFormat[AC135] = new OptionalIntegerFormat[AC135](AC135.apply)
  implicit val ac138Format: OptionalIntegerFormat[AC138] = new OptionalIntegerFormat[AC138](AC138.apply)
  implicit val ac139Format: OptionalIntegerFormat[AC139] = new OptionalIntegerFormat[AC139](AC139.apply)
  implicit val ac136Format: OptionalIntegerFormat[AC136] = new OptionalIntegerFormat[AC136](AC136.apply)
  implicit val ac137Format: OptionalIntegerFormat[AC137] = new OptionalIntegerFormat[AC137](AC137.apply)
  implicit val ac140Format: OptionalIntegerFormat[AC140] = new OptionalIntegerFormat[AC140](AC140.apply)
  implicit val ac141Format: OptionalIntegerFormat[AC141] = new OptionalIntegerFormat[AC141](AC141.apply)
  implicit val ac142Format: OptionalIntegerFormat[AC142] = new OptionalIntegerFormat[AC142](AC142.apply)
  implicit val ac143Format: OptionalIntegerFormat[AC143] = new OptionalIntegerFormat[AC143](AC143.apply)
  implicit val ac144Format: OptionalIntegerFormat[AC144] = new OptionalIntegerFormat[AC144](AC144.apply)
  implicit val ac145Format: OptionalIntegerFormat[AC145] = new OptionalIntegerFormat[AC145](AC145.apply)
  implicit val ac146Format: OptionalIntegerFormat[AC146] = new OptionalIntegerFormat[AC146](AC146.apply)
  implicit val ac147Format: OptionalIntegerFormat[AC147] = new OptionalIntegerFormat[AC147](AC147.apply)
  implicit val ac148Format: OptionalIntegerFormat[AC148] = new OptionalIntegerFormat[AC148](AC148.apply)
  implicit val ac149Format: OptionalIntegerFormat[AC149] = new OptionalIntegerFormat[AC149](AC149.apply)
  implicit val ac150Format: OptionalIntegerFormat[AC150] = new OptionalIntegerFormat[AC150](AC150.apply)
  implicit val ac151Format: OptionalIntegerFormat[AC151] = new OptionalIntegerFormat[AC151](AC151.apply)
  implicit val ac152Format: OptionalIntegerFormat[AC152] = new OptionalIntegerFormat[AC152](AC152.apply)
  implicit val ac153Format: OptionalIntegerFormat[AC153] = new OptionalIntegerFormat[AC153](AC153.apply)
  implicit val ac156Format: OptionalIntegerFormat[AC156] = new OptionalIntegerFormat[AC156](AC156.apply)
  implicit val ac157Format: OptionalIntegerFormat[AC157] = new OptionalIntegerFormat[AC157](AC157.apply)
  implicit val ac158Format: OptionalIntegerFormat[AC158] = new OptionalIntegerFormat[AC158](AC158.apply)
  implicit val ac159Format: OptionalIntegerFormat[AC159] = new OptionalIntegerFormat[AC159](AC159.apply)
  implicit val ac160Format: OptionalIntegerFormat[AC160] = new OptionalIntegerFormat[AC160](AC160.apply)
  implicit val ac161Format: OptionalIntegerFormat[AC161] = new OptionalIntegerFormat[AC161](AC161.apply)
  implicit val ac162Format: OptionalIntegerFormat[AC162] = new OptionalIntegerFormat[AC162](AC162.apply)
  implicit val ac163Format: OptionalIntegerFormat[AC163] = new OptionalIntegerFormat[AC163](AC163.apply)
  implicit val ac212Format: OptionalIntegerFormat[AC212] = new OptionalIntegerFormat[AC212](AC212.apply)
  implicit val ac212AFormat: OptionalIntegerFormat[AC212A] = new OptionalIntegerFormat[AC212A](AC212A.apply)
  implicit val ac212BFormat: OptionalIntegerFormat[AC212B] = new OptionalIntegerFormat[AC212B](AC212B.apply)
  implicit val ac212CFormat: OptionalIntegerFormat[AC212C] = new OptionalIntegerFormat[AC212C](AC212C.apply)
  implicit val ac212DFormat: OptionalIntegerFormat[AC212D] = new OptionalIntegerFormat[AC212D](AC212D.apply)
  implicit val ac212EFormat: OptionalIntegerFormat[AC212E] = new OptionalIntegerFormat[AC212E](AC212E.apply)
  implicit val ac213Format: OptionalIntegerFormat[AC213] = new OptionalIntegerFormat[AC213](AC213.apply)
  implicit val ac213AFormat: OptionalIntegerFormat[AC213A] = new OptionalIntegerFormat[AC213A](AC213A.apply)
  implicit val ac213BFormat: OptionalIntegerFormat[AC213B] = new OptionalIntegerFormat[AC213B](AC213B.apply)
  implicit val ac213CFormat: OptionalIntegerFormat[AC213C] = new OptionalIntegerFormat[AC213C](AC213C.apply)
  implicit val ac213DFormat: OptionalIntegerFormat[AC213D] = new OptionalIntegerFormat[AC213D](AC213D.apply)
  implicit val ac213EFormat: OptionalIntegerFormat[AC213E] = new OptionalIntegerFormat[AC213E](AC213E.apply)
  implicit val ac214Format: OptionalIntegerFormat[AC214] = new OptionalIntegerFormat[AC214](AC214.apply)
  implicit val ac214AFormat: OptionalIntegerFormat[AC214A] = new OptionalIntegerFormat[AC214A](AC214A.apply)
  implicit val ac214BFormat: OptionalIntegerFormat[AC214B] = new OptionalIntegerFormat[AC214B](AC214B.apply)
  implicit val ac214CFormat: OptionalIntegerFormat[AC214C] = new OptionalIntegerFormat[AC214C](AC214C.apply)
  implicit val ac214DFormat: OptionalIntegerFormat[AC214D] = new OptionalIntegerFormat[AC214D](AC214D.apply)
  implicit val ac214EFormat: OptionalIntegerFormat[AC214E] = new OptionalIntegerFormat[AC214E](AC214E.apply)
  implicit val ac217Format: OptionalIntegerFormat[AC217] = new OptionalIntegerFormat[AC217](AC217.apply)
  implicit val ac219Format: OptionalIntegerFormat[AC219] = new OptionalIntegerFormat[AC219](AC219.apply)
  implicit val ac115Format: OptionalIntegerFormat[AC115] = new OptionalIntegerFormat[AC115](AC115.apply)
  implicit val ac115AFormat: OptionalIntegerFormat[AC115A] = new OptionalIntegerFormat[AC115A](AC115A.apply)
  implicit val ac115BFormat: OptionalIntegerFormat[AC115B] = new OptionalIntegerFormat[AC115B](AC115B.apply)
  implicit val ac116Format: OptionalIntegerFormat[AC116] = new OptionalIntegerFormat[AC116](AC116.apply)
  implicit val ac116AFormat: OptionalIntegerFormat[AC116A] = new OptionalIntegerFormat[AC116A](AC116A.apply)
  implicit val ac116BFormat: OptionalIntegerFormat[AC116B] = new OptionalIntegerFormat[AC116B](AC116B.apply)
  implicit val ac117Format: OptionalIntegerFormat[AC117] = new OptionalIntegerFormat[AC117](AC117.apply)
  implicit val ac117AFormat: OptionalIntegerFormat[AC117A] = new OptionalIntegerFormat[AC117A](AC117A.apply)
  implicit val ac117BFormat: OptionalIntegerFormat[AC117B] = new OptionalIntegerFormat[AC117B](AC117B.apply)
  implicit val ac119Format: OptionalIntegerFormat[AC119] = new OptionalIntegerFormat[AC119](AC119.apply)
  implicit val ac119AFormat: OptionalIntegerFormat[AC119A] = new OptionalIntegerFormat[AC119A](AC119A.apply)
  implicit val ac119BFormat: OptionalIntegerFormat[AC119B] = new OptionalIntegerFormat[AC119B](AC119B.apply)
  implicit val ac120Format: OptionalIntegerFormat[AC120] = new OptionalIntegerFormat[AC120](AC120.apply)
  implicit val ac120AFormat: OptionalIntegerFormat[AC120A] = new OptionalIntegerFormat[AC120A](AC120A.apply)
  implicit val ac120BFormat: OptionalIntegerFormat[AC120B] = new OptionalIntegerFormat[AC120B](AC120B.apply)
  implicit val ac121Format: OptionalIntegerFormat[AC121] = new OptionalIntegerFormat[AC121](AC121.apply)
  implicit val ac121AFormat: OptionalIntegerFormat[AC121A] = new OptionalIntegerFormat[AC121A](AC121A.apply)
  implicit val ac121BFormat: OptionalIntegerFormat[AC121B] = new OptionalIntegerFormat[AC121B](AC121B.apply)
  implicit val ac122Format: OptionalIntegerFormat[AC122] = new OptionalIntegerFormat[AC122](AC122.apply)
  implicit val ac122AFormat: OptionalIntegerFormat[AC122A] = new OptionalIntegerFormat[AC122A](AC122A.apply)

  implicit val ac200AFormat: OptionalBooleanFormat[AC200A] = new OptionalBooleanFormat[AC200A](AC200A.apply)
  implicit val ac200Format: OptionalStringFormat[AC200] = new OptionalStringFormat[AC200](AC200.apply)

  implicit val ac122BFormat: OptionalIntegerFormat[AC122B] = new OptionalIntegerFormat[AC122B](AC122B.apply)
  implicit val ac209Format: OptionalIntegerFormat[AC209] = new OptionalIntegerFormat[AC209](AC209.apply)
  implicit val ac209AFormat: OptionalIntegerFormat[AC209A] = new OptionalIntegerFormat[AC209A](AC209A.apply)
  implicit val ac209BFormat: OptionalIntegerFormat[AC209B] = new OptionalIntegerFormat[AC209B](AC209B.apply)
  implicit val ac210Format: OptionalIntegerFormat[AC210] = new OptionalIntegerFormat[AC210](AC210.apply)
  implicit val ac210AFormat: OptionalIntegerFormat[AC210A] = new OptionalIntegerFormat[AC210A](AC210A.apply)
  implicit val ac210BFormat: OptionalIntegerFormat[AC210B] = new OptionalIntegerFormat[AC210B](AC210B.apply)
  implicit val ac211Format: OptionalIntegerFormat[AC211] = new OptionalIntegerFormat[AC211](AC211.apply)
  implicit val ac211AFormat: OptionalIntegerFormat[AC211A] = new OptionalIntegerFormat[AC211A](AC211A.apply)
  implicit val ac211BFormat: OptionalIntegerFormat[AC211B] = new OptionalIntegerFormat[AC211B](AC211B.apply)
  implicit val ac304AFormat: OptionalStringFormat[AC304A] = new OptionalStringFormat[AC304A](AC304A.apply)
  implicit val ac305AFormat: OptionalStringFormat[AC305A] = new OptionalStringFormat[AC305A](AC305A.apply)
  implicit val ac306AFormat: OptionalIntegerFormat[AC306A] = new OptionalIntegerFormat[AC306A](AC306A.apply)
  implicit val ac307AFormat: OptionalIntegerFormat[AC307A] = new OptionalIntegerFormat[AC307A](AC307A.apply)
  implicit val ac308AFormat: OptionalIntegerFormat[AC308A] = new OptionalIntegerFormat[AC308A](AC308A.apply)
  implicit val ac309AFormat: OptionalIntegerFormat[AC309A] = new OptionalIntegerFormat[AC309A](AC309A.apply)
  implicit val ac320Format: OptionalBooleanFormat[AC320] = new OptionalBooleanFormat[AC320](AC320.apply)
  implicit val ac320AFormat: OptionalStringFormat[AC320A] = new OptionalStringFormat[AC320A](AC320A.apply)
  implicit val ac321Format: OptionalStringFormat[AC321] = new OptionalStringFormat[AC321](AC321.apply)
  implicit val ac322Format: OptionalStringFormat[AC322] = new OptionalStringFormat[AC322](AC322.apply)
  implicit val ac323Format: OptionalStringFormat[AC323] = new OptionalStringFormat[AC323](AC323.apply)
  implicit val ac324Format: OptionalStringFormat[AC324] = new OptionalStringFormat[AC324](AC324.apply)
  implicit val ac7110AFormat: OptionalStringFormat[AC7110A] = new OptionalStringFormat[AC7110A](AC7110A.apply)
  implicit val ac138BFormat: OptionalIntegerFormat[AC138B] = new OptionalIntegerFormat[AC138B](AC138B.apply)
  implicit val ac139BFormat: OptionalIntegerFormat[AC139B] = new OptionalIntegerFormat[AC139B](AC139B.apply)
  implicit val ac150BFormat: OptionalIntegerFormat[AC150B] = new OptionalIntegerFormat[AC150B](AC150B.apply)
  implicit val ac151BFormat: OptionalIntegerFormat[AC151B] = new OptionalIntegerFormat[AC151B](AC151B.apply)
  implicit val ac5032Format: OptionalStringFormat[AC5032] = new OptionalStringFormat[AC5032](AC5032.apply)
  implicit val ac5052AFormat: OptionalIntegerFormat[AC5052A] = new OptionalIntegerFormat[AC5052A](AC5052A.apply)
  implicit val ac5052BFormat: OptionalStringFormat[AC5052B] = new OptionalStringFormat[AC5052B](AC5052B.apply)
  implicit val ac5052CFormat: OptionalIntegerFormat[AC5052C] = new OptionalIntegerFormat[AC5052C](AC5052C.apply)
  implicit val ac5058AFormat: OptionalStringFormat[AC5058A] = new OptionalStringFormat[AC5058A](AC5058A.apply)
  implicit val ac5064AFormat: OptionalStringFormat[AC5064A] = new OptionalStringFormat[AC5064A](AC5064A.apply)
  implicit val ac189Format: OptionalIntegerFormat[AC189] = new OptionalIntegerFormat[AC189](AC189.apply)
  implicit val ac5076CFormat: OptionalStringFormat[AC5076C] = new OptionalStringFormat[AC5076C](AC5076C.apply)
  implicit val ac128Format: OptionalIntegerFormat[AC128] = new OptionalIntegerFormat[AC128](AC128.apply)
  implicit val ac128AFormat: OptionalIntegerFormat[AC128A] = new OptionalIntegerFormat[AC128A](AC128A.apply)
  implicit val ac128BFormat: OptionalIntegerFormat[AC128B] = new OptionalIntegerFormat[AC128B](AC128B.apply)
  implicit val ac128CFormat: OptionalIntegerFormat[AC128C] = new OptionalIntegerFormat[AC128C](AC128C.apply)
  implicit val ac128DFormat: OptionalIntegerFormat[AC128D] = new OptionalIntegerFormat[AC128D](AC128D.apply)
  implicit val ac128EFormat: OptionalIntegerFormat[AC128E] = new OptionalIntegerFormat[AC128E](AC128E.apply)
  implicit val ac129Format: OptionalIntegerFormat[AC129] = new OptionalIntegerFormat[AC129](AC129.apply)
  implicit val ac129AFormat: OptionalIntegerFormat[AC129A] = new OptionalIntegerFormat[AC129A](AC129A.apply)
  implicit val ac129BFormat: OptionalIntegerFormat[AC129B] = new OptionalIntegerFormat[AC129B](AC129B.apply)
  implicit val ac129CFormat: OptionalIntegerFormat[AC129C] = new OptionalIntegerFormat[AC129C](AC129C.apply)
  implicit val ac129DFormat: OptionalIntegerFormat[AC129D] = new OptionalIntegerFormat[AC129D](AC129D.apply)
  implicit val ac129EFormat: OptionalIntegerFormat[AC129E] = new OptionalIntegerFormat[AC129E](AC129E.apply)
  implicit val ac133Format: OptionalIntegerFormat[AC133] = new OptionalIntegerFormat[AC133](AC133.apply)
  implicit val ac133AFormat: OptionalIntegerFormat[AC133A] = new OptionalIntegerFormat[AC133A](AC133A.apply)
  implicit val ac133BFormat: OptionalIntegerFormat[AC133B] = new OptionalIntegerFormat[AC133B](AC133B.apply)
  implicit val ac133CFormat: OptionalIntegerFormat[AC133C] = new OptionalIntegerFormat[AC133C](AC133C.apply)
  implicit val ac133DFormat: OptionalIntegerFormat[AC133D] = new OptionalIntegerFormat[AC133D](AC133D.apply)
  implicit val ac133EFormat: OptionalIntegerFormat[AC133E] = new OptionalIntegerFormat[AC133E](AC133E.apply)
  implicit val ac187Format: OptionalBooleanFormat[AC187] = new OptionalBooleanFormat[AC187](AC187.apply)
  implicit val ac188Format: OptionalIntegerFormat[AC188] = new OptionalIntegerFormat[AC188](AC188.apply)
  implicit val ac5133Format: OptionalStringFormat[AC5133] = new OptionalStringFormat[AC5133](AC5133.apply)

  implicit val ac124Format: OptionalIntegerFormat[AC124] = new OptionalIntegerFormat[AC124](AC124.apply)
  implicit val ac124AFormat: OptionalIntegerFormat[AC124A] = new OptionalIntegerFormat[AC124A](AC124A.apply)
  implicit val ac124BFormat: OptionalIntegerFormat[AC124B] = new OptionalIntegerFormat[AC124B](AC124B.apply)
  implicit val ac124CFormat: OptionalIntegerFormat[AC124C] = new OptionalIntegerFormat[AC124C](AC124C.apply)
  implicit val ac124DFormat: OptionalIntegerFormat[AC124D] = new OptionalIntegerFormat[AC124D](AC124D.apply)
  implicit val ac124EFormat: OptionalIntegerFormat[AC124E] = new OptionalIntegerFormat[AC124E](AC124E.apply)
  implicit val ac114Format: OptionalIntegerFormat[AC114] = new OptionalIntegerFormat[AC114](AC114.apply)
  implicit val ac114AFormat: OptionalIntegerFormat[AC114A] = new OptionalIntegerFormat[AC114A](AC114A.apply)
  implicit val ac114BFormat: OptionalIntegerFormat[AC114B] = new OptionalIntegerFormat[AC114B](AC114B.apply)
  implicit val ac118Format: OptionalIntegerFormat[AC118] = new OptionalIntegerFormat[AC118](AC118.apply)
  implicit val ac118AFormat: OptionalIntegerFormat[AC118A] = new OptionalIntegerFormat[AC118A](AC118A.apply)
  implicit val ac118BFormat: OptionalIntegerFormat[AC118B] = new OptionalIntegerFormat[AC118B](AC118B.apply)
  implicit val ac123Format: OptionalIntegerFormat[AC123] = new OptionalIntegerFormat[AC123](AC123.apply)
  implicit val ac123AFormat: OptionalIntegerFormat[AC123A] = new OptionalIntegerFormat[AC123A](AC123A.apply)
  implicit val ac123BFormat: OptionalIntegerFormat[AC123B] = new OptionalIntegerFormat[AC123B](AC123B.apply)
  implicit val ac5123Format: OptionalStringFormat[AC5123] = new OptionalStringFormat[AC5123](AC5123.apply)

  implicit val ac7100Format: OptionalBooleanFormat[AC7100] = new OptionalBooleanFormat(AC7100.apply)

  implicit val ac7200Format: OptionalBooleanFormat[AC7200] = new OptionalBooleanFormat(AC7200.apply)
  implicit val ac7210AFormat: OptionalIntegerFormat[AC7210A] = new OptionalIntegerFormat(AC7210A.apply)
  implicit val ac7210BFormat: OptionalIntegerFormat[AC7210B] = new OptionalIntegerFormat(AC7210B.apply)

  implicit val ac7210Format: OptionalStringFormat[AC7210] = new OptionalStringFormat(AC7210.apply)

  implicit val ac7300Format: OptionalBooleanFormat[AC7300] = new OptionalBooleanFormat(AC7300.apply)
  implicit val ac7400Format: OptionalBooleanFormat[AC7400] = new OptionalBooleanFormat(AC7400.apply)
  implicit val ac7401Format: OptionalStringFormat[AC7401] = new OptionalStringFormat(AC7401.apply)
  implicit val ac7500Format: OptionalBooleanFormat[AC7500] = new OptionalBooleanFormat(AC7500.apply)
  implicit val ac7501Format: OptionalStringFormat[AC7501] = new OptionalStringFormat(AC7501.apply)
  implicit val ac7600Format: OptionalBooleanFormat[AC7600] = new OptionalBooleanFormat(AC7600.apply)
  implicit val ac7601Format: OptionalStringFormat[AC7601] = new OptionalStringFormat(AC7601.apply)
  implicit val ac7800Format: OptionalBooleanFormat[AC7800] = new OptionalBooleanFormat(AC7800.apply)
  implicit val ac7801Format: OptionalBooleanFormat[AC7801] = new OptionalBooleanFormat(AC7801.apply)
  implicit val ac299AFormat: OptionalStringFormat[AC299A] = new OptionalStringFormat(AC299A.apply)
  implicit val ac300AFormat: OptionalStringFormat[AC300A] = new OptionalStringFormat(AC300A.apply)
  implicit val ac301AFormat: OptionalStringFormat[AC301A] = new OptionalStringFormat(AC301A.apply)
  implicit val ac302AFormat: OptionalIntegerFormat[AC302A] = new OptionalIntegerFormat(AC302A.apply)
  implicit val ac303AFormat: OptionalIntegerFormat[AC303A] = new OptionalIntegerFormat(AC303A.apply)
  implicit val ac7806Format: OptionalStringFormat[AC7806] = new OptionalStringFormat(AC7806.apply)

  implicit val ac7900Format: OptionalBooleanFormat[AC7900] = new OptionalBooleanFormat(AC7900.apply)
  implicit val ac7901Format: OptionalStringFormat[AC7901] = new OptionalStringFormat(AC7901.apply)

  implicit val relatedPartyTransactionFormatWIthDefaults: Format[RelatedPartyTransaction] = new Format[RelatedPartyTransaction] {
    val baseFormat: OFormat[RelatedPartyTransaction] = Json.format[RelatedPartyTransaction]

    override def reads(json: JsValue): JsResult[RelatedPartyTransaction] = baseFormat
      .composeWith(withDefault("ac7801", AC7801(None)))
      .composeWith(withDefault("ac299A", AC299A(None)))
      .composeWith(withDefault("ac300A", AC300A(None)))
      .composeWith(withDefault("ac301A", AC301A(None)))
      .composeWith(withDefault("ac302A", AC302A(None)))
      .composeWith(withDefault("ac303A", AC303A(None)))
      .reads(json)

    override def writes(o: RelatedPartyTransaction): JsValue = baseFormat.writes(o)
  }

  implicit val relatedPartyTransactionsFormatWithDefaults: Format[RelatedPartyTransactions] = new Format[RelatedPartyTransactions] {
    val baseFormat: OFormat[RelatedPartyTransactions] = Json.format[RelatedPartyTransactions]

    override def reads(json: JsValue): JsResult[RelatedPartyTransactions] = baseFormat
      .composeWith(withDefault("ac7806", AC7806(None)))
      .reads(json)

    override def writes(o: RelatedPartyTransactions): JsValue = baseFormat.writes(o)
  }

  implicit val loanToDirectorFormatWithDefaults: Format[LoanToDirector] = new Format[LoanToDirector] {
    val baseFormat: OFormat[LoanToDirector] = Json.format[LoanToDirector]

    override def reads(json: JsValue): JsResult[LoanToDirector] = baseFormat
      .composeWith(withDefault("ac304A", AC304A(None)))
      .composeWith(withDefault("ac305A", AC305A(None)))
      .composeWith(withDefault("ac306A", AC306A(None)))
      .composeWith(withDefault("ac307A", AC307A(None)))
      .composeWith(withDefault("ac308A", AC308A(None)))
      .composeWith(withDefault("ac309A", AC309A(None)))
      .reads(json)

    override def writes(o: LoanToDirector): JsValue = baseFormat.writes(o)
  }

  implicit val loansToDirectorsFormatWithDefaults: Format[LoansToDirectors] = new Format[LoansToDirectors] {
    val baseFormat: OFormat[LoansToDirectors] = Json.format[LoansToDirectors]

    override def reads(json: JsValue): JsResult[LoansToDirectors] = baseFormat
      .composeWith(withDefault("ac7501", AC7501(None)))
      .reads(json)

    override def writes(o: LoansToDirectors): JsValue = baseFormat.writes(o)
  }

  implicit val ac8084Format: OptionalBooleanFormat[AC8084] = new OptionalBooleanFormat(AC8084.apply)
  implicit val ac8085Format: OptionalBooleanFormat[AC8085] = new OptionalBooleanFormat(AC8085.apply)

  implicit val acq5021Format: Format[ACQ5021] = new OptionalBooleanFormat[ACQ5021](ACQ5021.apply)
  implicit val acq5022Format: Format[ACQ5022] = new OptionalBooleanFormat[ACQ5022](ACQ5022.apply)
  implicit val acq5031Format: Format[ACQ5031] = new OptionalBooleanFormat[ACQ5031](ACQ5031.apply)
  implicit val acq5032Format: Format[ACQ5032] = new OptionalBooleanFormat[ACQ5032](ACQ5032.apply)
  implicit val acq5033Format: Format[ACQ5033] = new OptionalBooleanFormat[ACQ5033](ACQ5033.apply)
  implicit val acq5034Format: Format[ACQ5034] = new OptionalBooleanFormat[ACQ5034](ACQ5034.apply)
  implicit val acq5035Format: Format[ACQ5035] = new OptionalBooleanFormat[ACQ5035](ACQ5035.apply)


}
