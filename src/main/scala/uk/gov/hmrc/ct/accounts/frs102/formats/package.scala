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

package uk.gov.hmrc.ct.accounts.frs102

import play.api.libs.json.{JsResult, JsValue, Reads, _}
import uk.gov.hmrc.ct.accounts.frs102.abridged.accountsApproval._
import uk.gov.hmrc.ct.accounts.frs102.abridged.loansToDirectors._
import uk.gov.hmrc.ct.accounts.frs102.abridged.relatedPartyTransactions._
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.formats._

package object formats {

  private def withDefault[A](key:String, default:A)(implicit writes:Writes[A]) = __.json.update((__ \ key).json.copyFrom((__ \ key).json.pick orElse Reads.pure(Json.toJson(default))))

  implicit val ac13Format = new OptionalIntegerFormat[AC13](AC13.apply)
  implicit val ac14Format = new OptionalIntegerFormat[AC14](AC14.apply)
  implicit val ac15Format = new OptionalIntegerFormat[AC15](AC15.apply)
  implicit val ac16Format = new OptionalIntegerFormat[AC16](AC16.apply)
  implicit val ac17Format = new OptionalIntegerFormat[AC17](AC17.apply)
  implicit val ac18Format = new OptionalIntegerFormat[AC18](AC18.apply)
  implicit val ac19Format = new OptionalIntegerFormat[AC19](AC19.apply)
  implicit val ac20Format = new OptionalIntegerFormat[AC20](AC20.apply)
  implicit val ac21Format = new OptionalIntegerFormat[AC21](AC21.apply)
  implicit val ac22Format = new OptionalIntegerFormat[AC22](AC22.apply)
  implicit val ac23Format = new OptionalIntegerFormat[AC23](AC23.apply)
  implicit val ac28Format = new OptionalIntegerFormat[AC28](AC28.apply)
  implicit val ac29Format = new OptionalIntegerFormat[AC29](AC29.apply)
  implicit val ac30Format = new OptionalIntegerFormat[AC30](AC30.apply)
  implicit val ac31Format = new OptionalIntegerFormat[AC31](AC31.apply)
  implicit val ac34Format = new OptionalIntegerFormat[AC34](AC34.apply)
  implicit val ac35Format = new OptionalIntegerFormat[AC35](AC35.apply)
  implicit val ac42Format = new OptionalIntegerFormat[AC42](AC42.apply)
  implicit val ac43Format = new OptionalIntegerFormat[AC43](AC43.apply)
  implicit val ac44Format = new OptionalIntegerFormat[AC44](AC44.apply)
  implicit val ac45Format = new OptionalIntegerFormat[AC45](AC45.apply)
  implicit val ac50Format = new OptionalIntegerFormat[AC50](AC50.apply)
  implicit val ac51Format = new OptionalIntegerFormat[AC51](AC51.apply)
  implicit val ac52Format = new OptionalIntegerFormat[AC52](AC52.apply)
  implicit val ac53Format = new OptionalIntegerFormat[AC53](AC53.apply)
  implicit val ac54Format = new OptionalIntegerFormat[AC54](AC54.apply)
  implicit val ac55Format = new OptionalIntegerFormat[AC55](AC55.apply)
  implicit val ac58Format = new OptionalIntegerFormat[AC58](AC58.apply)
  implicit val ac59Format = new OptionalIntegerFormat[AC59](AC59.apply)
  implicit val ac64Format = new OptionalIntegerFormat[AC64](AC64.apply)
  implicit val ac65Format = new OptionalIntegerFormat[AC65](AC65.apply)
  implicit val ac66Format = new OptionalIntegerFormat[AC66](AC66.apply)
  implicit val ac67Format = new OptionalIntegerFormat[AC67](AC67.apply)
  implicit val ac70Format = new OptionalIntegerFormat[AC70](AC70.apply)
  implicit val ac71Format = new OptionalIntegerFormat[AC71](AC71.apply)
  implicit val ac74Format = new OptionalIntegerFormat[AC74](AC74.apply)
  implicit val ac75Format = new OptionalIntegerFormat[AC75](AC75.apply)
  implicit val ac76Format = new OptionalIntegerFormat[AC76](AC76.apply)
  implicit val ac77Format = new OptionalIntegerFormat[AC77](AC77.apply)
  implicit val ac106Format = new OptionalIntegerFormat[AC106](AC106.apply)
  implicit val ac106AFormat = new OptionalStringFormat[AC106A](AC106A.apply)
  implicit val ac107Format = new OptionalIntegerFormat[AC107](AC107.apply)
  implicit val ac125Format = new OptionalIntegerFormat[AC125](AC125.apply)
  implicit val ac125AFormat = new OptionalIntegerFormat[AC125A](AC125A.apply)
  implicit val ac125BFormat = new OptionalIntegerFormat[AC125B](AC125B.apply)
  implicit val ac125CFormat = new OptionalIntegerFormat[AC125C](AC125C.apply)
  implicit val ac125DFormat = new OptionalIntegerFormat[AC125D](AC125D.apply)
  implicit val ac125EFormat = new OptionalIntegerFormat[AC125E](AC125E.apply)
  implicit val ac126Format = new OptionalIntegerFormat[AC126](AC126.apply)
  implicit val ac126AFormat = new OptionalIntegerFormat[AC126A](AC126A.apply)
  implicit val ac126BFormat = new OptionalIntegerFormat[AC126B](AC126B.apply)
  implicit val ac126CFormat = new OptionalIntegerFormat[AC126C](AC126C.apply)
  implicit val ac126DFormat = new OptionalIntegerFormat[AC126D](AC126D.apply)
  implicit val ac126EFormat = new OptionalIntegerFormat[AC126E](AC126E.apply)
  implicit val ac127Format = new OptionalIntegerFormat[AC127](AC127.apply)
  implicit val ac127AFormat = new OptionalIntegerFormat[AC127A](AC127A.apply)
  implicit val ac127BFormat = new OptionalIntegerFormat[AC127B](AC127B.apply)
  implicit val ac127CFormat = new OptionalIntegerFormat[AC127C](AC127C.apply)
  implicit val ac127DFormat = new OptionalIntegerFormat[AC127D](AC127D.apply)
  implicit val ac127EFormat = new OptionalIntegerFormat[AC127E](AC127E.apply)
  implicit val ac130Format = new OptionalIntegerFormat[AC130](AC130.apply)
  implicit val ac130AFormat = new OptionalIntegerFormat[AC130A](AC130A.apply)
  implicit val ac130BFormat = new OptionalIntegerFormat[AC130B](AC130B.apply)
  implicit val ac130CFormat = new OptionalIntegerFormat[AC130C](AC130C.apply)
  implicit val ac130DFormat = new OptionalIntegerFormat[AC130D](AC130D.apply)
  implicit val ac130EFormat = new OptionalIntegerFormat[AC130E](AC130E.apply)
  implicit val ac131Format = new OptionalIntegerFormat[AC131](AC131.apply)
  implicit val ac131AFormat = new OptionalIntegerFormat[AC131A](AC131A.apply)
  implicit val ac131BFormat = new OptionalIntegerFormat[AC131B](AC131B.apply)
  implicit val ac131CFormat = new OptionalIntegerFormat[AC131C](AC131C.apply)
  implicit val ac131DFormat = new OptionalIntegerFormat[AC131D](AC131D.apply)
  implicit val ac131EFormat = new OptionalIntegerFormat[AC131E](AC131E.apply)
  implicit val ac132Format = new OptionalIntegerFormat[AC132](AC132.apply)
  implicit val ac132AFormat = new OptionalIntegerFormat[AC132A](AC132A.apply)
  implicit val ac132BFormat = new OptionalIntegerFormat[AC132B](AC132B.apply)
  implicit val ac132CFormat = new OptionalIntegerFormat[AC132C](AC132C.apply)
  implicit val ac132DFormat = new OptionalIntegerFormat[AC132D](AC132D.apply)
  implicit val ac132EFormat = new OptionalIntegerFormat[AC132E](AC132E.apply)
  implicit val ac198AFormat = new OptionalDateFormat[AC198A](AC198A.apply)
  implicit val ac199AFormat = new StringFormat[AC199A](AC199A.apply)
  implicit val ac8092Format = new OptionalStringFormat[AC8092](AC8092.apply)
  implicit val ac8091Format = new OptionalBooleanFormat[AC8091](AC8091.apply)
  implicit val ac212Format = new OptionalIntegerFormat[AC212](AC212.apply)
  implicit val ac212AFormat = new OptionalIntegerFormat[AC212A](AC212A.apply)
  implicit val ac212BFormat = new OptionalIntegerFormat[AC212B](AC212B.apply)
  implicit val ac212CFormat = new OptionalIntegerFormat[AC212C](AC212C.apply)
  implicit val ac212DFormat = new OptionalIntegerFormat[AC212D](AC212D.apply)
  implicit val ac212EFormat = new OptionalIntegerFormat[AC212E](AC212E.apply)
  implicit val ac213Format = new OptionalIntegerFormat[AC213](AC213.apply)
  implicit val ac213AFormat = new OptionalIntegerFormat[AC213A](AC213A.apply)
  implicit val ac213BFormat = new OptionalIntegerFormat[AC213B](AC213B.apply)
  implicit val ac213CFormat = new OptionalIntegerFormat[AC213C](AC213C.apply)
  implicit val ac213DFormat = new OptionalIntegerFormat[AC213D](AC213D.apply)
  implicit val ac213EFormat = new OptionalIntegerFormat[AC213E](AC213E.apply)
  implicit val ac214Format = new OptionalIntegerFormat[AC214](AC214.apply)
  implicit val ac214AFormat = new OptionalIntegerFormat[AC214A](AC214A.apply)
  implicit val ac214BFormat = new OptionalIntegerFormat[AC214B](AC214B.apply)
  implicit val ac214CFormat = new OptionalIntegerFormat[AC214C](AC214C.apply)
  implicit val ac214DFormat = new OptionalIntegerFormat[AC214D](AC214D.apply)
  implicit val ac214EFormat = new OptionalIntegerFormat[AC214E](AC214E.apply)
  implicit val ac217Format = new OptionalIntegerFormat[AC217](AC217.apply)
  implicit val ac219Format = new OptionalIntegerFormat[AC219](AC219.apply)
  implicit val ac115Format = new OptionalIntegerFormat[AC115](AC115.apply)
  implicit val ac116Format = new OptionalIntegerFormat[AC116](AC116.apply)
  implicit val ac117Format = new OptionalIntegerFormat[AC117](AC117.apply)
  implicit val ac119Format = new OptionalIntegerFormat[AC119](AC119.apply)
  implicit val ac120Format = new OptionalIntegerFormat[AC120](AC120.apply)
  implicit val ac121Format = new OptionalIntegerFormat[AC121](AC121.apply)
  implicit val ac122Format = new OptionalIntegerFormat[AC122](AC122.apply)
  implicit val ac209Format = new OptionalIntegerFormat[AC209](AC209.apply)
  implicit val ac210Format = new OptionalIntegerFormat[AC210](AC210.apply)
  implicit val ac211Format = new OptionalIntegerFormat[AC211](AC211.apply)
  implicit val ac304AFormat = new OptionalStringFormat[AC304A](AC304A.apply)
  implicit val ac305AFormat = new OptionalStringFormat[AC305A](AC305A.apply)
  implicit val ac306AFormat = new OptionalIntegerFormat[AC306A](AC306A.apply)
  implicit val ac307AFormat = new OptionalIntegerFormat[AC307A](AC307A.apply)
  implicit val ac308AFormat = new OptionalIntegerFormat[AC308A](AC308A.apply)
  implicit val ac309AFormat = new OptionalIntegerFormat[AC309A](AC309A.apply)
  implicit val ac320Format = new OptionalBooleanFormat[AC320](AC320.apply)
  implicit val ac320AFormat = new OptionalStringFormat[AC320A](AC320A.apply)
  implicit val ac321Format = new OptionalStringFormat[AC321](AC321.apply)
  implicit val ac322Format = new OptionalStringFormat[AC322](AC322.apply)
  implicit val ac323Format = new OptionalStringFormat[AC323](AC323.apply)
  implicit val ac324Format = new OptionalStringFormat[AC324](AC324.apply)
  implicit val ac7110AFormat = new OptionalStringFormat[AC7110A](AC7110A.apply)
  implicit val ac465Format = new OptionalIntegerFormat[AC465](AC465.apply)
  implicit val ac466Format = new OptionalIntegerFormat[AC466](AC466.apply)
  implicit val ac470Format = new OptionalIntegerFormat[AC470](AC470.apply)
  implicit val ac471Format = new OptionalIntegerFormat[AC471](AC471.apply)
  implicit val ac5032Format = new OptionalStringFormat[AC5032](AC5032.apply)
  implicit val ac5052AFormat = new OptionalIntegerFormat[AC5052A](AC5052A.apply)
  implicit val ac5052BFormat = new OptionalStringFormat[AC5052B](AC5052B.apply)
  implicit val ac5052CFormat = new OptionalIntegerFormat[AC5052C](AC5052C.apply)
  implicit val ac5058AFormat = new OptionalStringFormat[AC5058A](AC5058A.apply)
  implicit val ac5064AFormat = new OptionalStringFormat[AC5064A](AC5064A.apply)
  implicit val ac189Format = new OptionalIntegerFormat[AC189](AC189.apply)
  implicit val ac5076CFormat = new OptionalStringFormat[AC5076C](AC5076C.apply)
  implicit val ac128Format = new OptionalIntegerFormat[AC128](AC128.apply)
  implicit val ac128AFormat = new OptionalIntegerFormat[AC128A](AC128A.apply)
  implicit val ac128BFormat = new OptionalIntegerFormat[AC128B](AC128B.apply)
  implicit val ac128CFormat = new OptionalIntegerFormat[AC128C](AC128C.apply)
  implicit val ac128DFormat = new OptionalIntegerFormat[AC128D](AC128D.apply)
  implicit val ac128EFormat = new OptionalIntegerFormat[AC128E](AC128E.apply)
  implicit val ac129Format = new OptionalIntegerFormat[AC129](AC129.apply)
  implicit val ac129AFormat = new OptionalIntegerFormat[AC129A](AC129A.apply)
  implicit val ac129BFormat = new OptionalIntegerFormat[AC129B](AC129B.apply)
  implicit val ac129CFormat = new OptionalIntegerFormat[AC129C](AC129C.apply)
  implicit val ac129DFormat = new OptionalIntegerFormat[AC129D](AC129D.apply)
  implicit val ac129EFormat = new OptionalIntegerFormat[AC129E](AC129E.apply)
  implicit val ac133Format = new OptionalIntegerFormat[AC133](AC133.apply)
  implicit val ac133AFormat = new OptionalIntegerFormat[AC133A](AC133A.apply)
  implicit val ac133BFormat = new OptionalIntegerFormat[AC133B](AC133B.apply)
  implicit val ac133CFormat = new OptionalIntegerFormat[AC133C](AC133C.apply)
  implicit val ac133DFormat = new OptionalIntegerFormat[AC133D](AC133D.apply)
  implicit val ac133EFormat = new OptionalIntegerFormat[AC133E](AC133E.apply)
  implicit val ac188Format = new OptionalIntegerFormat[AC188](AC188.apply)
  implicit val ac5133Format = new OptionalStringFormat[AC5133](AC5133.apply)

  implicit val ac124Format = new OptionalIntegerFormat[AC124](AC124.apply)
  implicit val ac124AFormat = new OptionalIntegerFormat[AC124A](AC124A.apply)
  implicit val ac124BFormat = new OptionalIntegerFormat[AC124B](AC124B.apply)
  implicit val ac124CFormat = new OptionalIntegerFormat[AC124C](AC124C.apply)
  implicit val ac124DFormat = new OptionalIntegerFormat[AC124D](AC124D.apply)
  implicit val ac124EFormat = new OptionalIntegerFormat[AC124E](AC124E.apply)
  implicit val ac114Format = new OptionalIntegerFormat[AC114](AC114.apply)
  implicit val ac118Format = new OptionalIntegerFormat[AC118](AC118.apply)
  implicit val ac123Format = new OptionalIntegerFormat[AC123](AC123.apply)
  implicit val ac5123Format = new OptionalStringFormat[AC5123](AC5123.apply)

  implicit val ac7100Format = new OptionalBooleanFormat(AC7100.apply)

  implicit val ac7200Format = new OptionalBooleanFormat(AC7200.apply)
  implicit val ac7210AFormat = new OptionalIntegerFormat(AC7210A.apply)
  implicit val ac7210BFormat = new OptionalIntegerFormat(AC7210B.apply)

  implicit val ac7300Format = new OptionalBooleanFormat(AC7300.apply)
  implicit val ac7400Format = new OptionalBooleanFormat(AC7400.apply)
  implicit val ac7401Format = new OptionalStringFormat(AC7401.apply)
  implicit val ac7500Format = new OptionalBooleanFormat(AC7500.apply)
  implicit val ac7501Format = new OptionalStringFormat(AC7501.apply)
  implicit val ac7600Format = new OptionalBooleanFormat(AC7600.apply)
  implicit val ac7601Format = new OptionalStringFormat(AC7601.apply)
  implicit val ac7800Format = new OptionalBooleanFormat(AC7800.apply)
  implicit val ac7801Format = new OptionalBooleanFormat(AC7801.apply)
  implicit val ac299AFormat = new OptionalStringFormat(AC299A.apply)
  implicit val ac300AFormat = new OptionalStringFormat(AC300A.apply)
  implicit val ac301AFormat = new OptionalStringFormat(AC301A.apply)
  implicit val ac302AFormat = new OptionalIntegerFormat(AC302A.apply)
  implicit val ac303AFormat = new OptionalIntegerFormat(AC303A.apply)
  implicit val ac7806Format = new OptionalStringFormat(AC7806.apply)

  implicit val ac7900Format = new OptionalBooleanFormat(AC7900.apply)
  implicit val ac7901Format = new OptionalStringFormat(AC7901.apply)

  implicit val relatedPartyTransactionFormatWIthDefaults = new Format[RelatedPartyTransaction] {
    val baseFormat = Json.format[RelatedPartyTransaction]

    override def reads(json: JsValue): JsResult[RelatedPartyTransaction] = baseFormat
      .compose(withDefault("ac7801", AC7801(None)))
      .compose(withDefault("ac299A", AC299A(None)))
      .compose(withDefault("ac300A", AC300A(None)))
      .compose(withDefault("ac301A", AC301A(None)))
      .compose(withDefault("ac302A", AC302A(None)))
      .compose(withDefault("ac303A", AC303A(None)))
      .reads(json)

    override def writes(o: RelatedPartyTransaction): JsValue = baseFormat.writes(o)
  }

  implicit val relatedPartyTransactionsFormatWithDefaults = new Format[RelatedPartyTransactions] {
    val baseFormat = Json.format[RelatedPartyTransactions]

    override def reads(json: JsValue): JsResult[RelatedPartyTransactions] = baseFormat
      .compose(withDefault("ac7806", AC7806(None)))
      .reads(json)

    override def writes(o: RelatedPartyTransactions): JsValue = baseFormat.writes(o)
  }

  implicit val coHoAccountsApprovalFormatWithDefaults = new Format[CompaniesHouseAccountsApproval] {
    val baseFormat = Json.format[CompaniesHouseAccountsApproval]

    override def reads(json: JsValue): JsResult[CompaniesHouseAccountsApproval] = baseFormat
      .compose(withDefault("ac8091", AC8091(None)))
      .compose(withDefault("ac198A", AC198A(None)))
      .reads(json)

    override def writes(o: CompaniesHouseAccountsApproval): JsValue = baseFormat.writes(o)
  }

  implicit val hmrcAccountsApprovalFormatWithDefaults = new Format[HmrcAccountsApproval] {
    val baseFormat = Json.format[HmrcAccountsApproval]

    override def reads(json: JsValue): JsResult[HmrcAccountsApproval] = baseFormat
      .compose(withDefault("ac8091", AC8091(None)))
      .compose(withDefault("ac198A", AC198A(None)))
      .reads(json)

    override def writes(o: HmrcAccountsApproval): JsValue = baseFormat.writes(o)
  }

  implicit val loanToDirectorFormatWithDefaults = new Format[LoanToDirector] {
    val baseFormat = Json.format[LoanToDirector]

    override def reads(json: JsValue): JsResult[LoanToDirector] = baseFormat
      .compose(withDefault("ac304A", AC304A(None)))
      .compose(withDefault("ac305A", AC305A(None)))
      .compose(withDefault("ac306A", AC306A(None)))
      .compose(withDefault("ac307A", AC307A(None)))
      .compose(withDefault("ac308A", AC308A(None)))
      .compose(withDefault("ac309A", AC309A(None)))
      .reads(json)

    override def writes(o: LoanToDirector): JsValue = baseFormat.writes(o)
  }

  implicit val loansToDirectorsFormatWithDefaults = new Format[LoansToDirectors] {
    val baseFormat = Json.format[LoansToDirectors]

    override def reads(json: JsValue): JsResult[LoansToDirectors] = baseFormat
      .compose(withDefault("ac7501", AC7501(None)))
      .reads(json)

    override def writes(o: LoansToDirectors): JsValue = baseFormat.writes(o)
  }

  implicit val ac8081Format = new OptionalBooleanFormat(AC8081.apply)
  implicit val ac8082Format = new OptionalBooleanFormat(AC8082.apply)
  implicit val ac8083Format = new OptionalBooleanFormat(AC8083.apply)
  implicit val ac8084Format = new OptionalBooleanFormat(AC8084.apply)
  implicit val ac8085Format = new OptionalBooleanFormat(AC8085.apply)
  implicit val ac8088Format = new OptionalBooleanFormat(AC8088.apply)

  implicit val ac8021Format: Format[AC8021] = new OptionalBooleanFormat[AC8021](AC8021.apply)
  implicit val ac8023Format: Format[AC8023] = new OptionalBooleanFormat[AC8023](AC8023.apply)
  implicit val ac8051Format: Format[AC8051] = new OptionalStringFormat[AC8051](AC8051.apply)
  implicit val ac8052Format: Format[AC8052] = new OptionalStringFormat[AC8052](AC8052.apply)
  implicit val ac8053Format: Format[AC8053] = new OptionalStringFormat[AC8053](AC8053.apply)
  implicit val ac8054Format: Format[AC8054] = new OptionalStringFormat[AC8054](AC8054.apply)
  implicit val ac8899Format: Format[AC8899] = new OptionalBooleanFormat[AC8899](AC8899.apply)
  implicit val acq8161Format: Format[ACQ8161] = new OptionalBooleanFormat[ACQ8161](ACQ8161.apply)

  implicit val directorFormat = Json.format[Director]
  implicit val directorsFormat: Format[Directors] = Json.format[Directors]
  implicit val acAC8033Format: Format[AC8033] = new OptionalStringFormat[AC8033](AC8033.apply)
  implicit val acq8003Format: Format[ACQ8003] = new OptionalBooleanFormat[ACQ8003](ACQ8003.apply)
  implicit val acq8009Format: Format[ACQ8009] = new OptionalBooleanFormat[ACQ8009](ACQ8009.apply)

}
