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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import play.api.libs.json._
import uk.gov.hmrc.ct.accounts.frs10x.abridged.relatedPartyTransactions._
import uk.gov.hmrc.ct.box.formats.{OptionalBooleanFormat, OptionalIntegerFormat, OptionalStringFormat}

package object formats {
  private def withDefault[A](key:String, default:A)(implicit writes:Writes[A]) = __.json.update((__ \ key).json.copyFrom((__ \ key).json.pick orElse Reads.pure(Json.toJson(default))))

  implicit val ac16Format = new OptionalIntegerFormat[AC16](AC16.apply)
  implicit val ac17Format = new OptionalIntegerFormat[AC17](AC17.apply)
  implicit val ac18Format = new OptionalIntegerFormat[AC18](AC18.apply)
  implicit val ac19Format = new OptionalIntegerFormat[AC19](AC19.apply)
  implicit val ac20Format = new OptionalIntegerFormat[AC20](AC20.apply)
  implicit val ac21Format = new OptionalIntegerFormat[AC21](AC21.apply)
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
  implicit val ac126Format = new OptionalIntegerFormat[AC126](AC126.apply)
  implicit val ac130Format = new OptionalIntegerFormat[AC130](AC130.apply)
  implicit val ac131Format = new OptionalIntegerFormat[AC131](AC131.apply)
  implicit val ac132Format = new OptionalIntegerFormat[AC132](AC132.apply)
  implicit val ac212Format = new OptionalIntegerFormat[AC212](AC212.apply)
  implicit val ac213Format = new OptionalIntegerFormat[AC213](AC213.apply)
  implicit val ac214Format = new OptionalIntegerFormat[AC214](AC214.apply)
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
  implicit val ac320Format = new OptionalBooleanFormat[AC320](AC320.apply)
  implicit val ac320AFormat = new OptionalStringFormat[AC320A](AC320A.apply)
  implicit val ac321Format = new OptionalStringFormat[AC321](AC321.apply)
  implicit val ac322Format = new OptionalStringFormat[AC322](AC322.apply)
  implicit val ac323Format = new OptionalStringFormat[AC323](AC323.apply)
  implicit val ac324Format = new OptionalStringFormat[AC324](AC324.apply)
  implicit val ac325AFormat = new OptionalStringFormat[AC325A](AC325A.apply)
  implicit val ac1076Format = new OptionalIntegerFormat[AC1076](AC1076.apply)
  implicit val ac1077Format = new OptionalIntegerFormat[AC1077](AC1077.apply)
  implicit val ac1178Format = new OptionalIntegerFormat[AC1178](AC1178.apply)
  implicit val ac1179Format = new OptionalIntegerFormat[AC1179](AC1179.apply)
  implicit val ac5032Format = new OptionalStringFormat[AC5032](AC5032.apply)
  implicit val ac5052AFormat = new OptionalIntegerFormat[AC5052A](AC5052A.apply)
  implicit val ac5052BFormat = new OptionalStringFormat[AC5052B](AC5052B.apply)
  implicit val ac5052CFormat = new OptionalIntegerFormat[AC5052C](AC5052C.apply)
  implicit val ac5058AFormat = new OptionalStringFormat[AC5058A](AC5058A.apply)
  implicit val ac5064AFormat = new OptionalStringFormat[AC5064A](AC5064A.apply)
  implicit val ac5076AFormat = new OptionalIntegerFormat[AC5076A](AC5076A.apply)
  implicit val ac5076CFormat = new OptionalStringFormat[AC5076C](AC5076C.apply)
  implicit val ac5131Format = new OptionalIntegerFormat[AC5131](AC5131.apply)
  implicit val ac5132Format = new OptionalIntegerFormat[AC5132](AC5132.apply)
  implicit val ac5133Format = new OptionalStringFormat[AC5133](AC5133.apply)

  implicit val ac5217Format = new OptionalIntegerFormat[AC5217](AC5217.apply)
  implicit val ac5117Format = new OptionalIntegerFormat[AC5117](AC5117.apply)
  implicit val ac5121Format = new OptionalIntegerFormat[AC5121](AC5121.apply)
  implicit val ac5122Format = new OptionalIntegerFormat[AC5122](AC5122.apply)
  implicit val ac5123Format = new OptionalStringFormat[AC5123](AC5123.apply)

  implicit val ac7100Format = new OptionalBooleanFormat(AC7100.apply)

  implicit val ac7200Format = new OptionalBooleanFormat(AC7200.apply)
  implicit val ac7210AFormat = new OptionalIntegerFormat(AC7210A.apply)
  implicit val ac7210BFormat = new OptionalIntegerFormat(AC7210B.apply)

  implicit val ac7300Format = new OptionalBooleanFormat(AC7300.apply)
  implicit val ac7400Format = new OptionalBooleanFormat(AC7400.apply)
  implicit val ac7401Format = new OptionalStringFormat(AC7401.apply)
  implicit val ac7500Format = new OptionalBooleanFormat(AC7500.apply)
  implicit val ac7600Format = new OptionalBooleanFormat(AC7600.apply)
  implicit val ac7601Format = new OptionalStringFormat(AC7601.apply)
  implicit val ac7800Format = new OptionalBooleanFormat(AC7800.apply)
  implicit val ac7801Format = new OptionalBooleanFormat(AC7801.apply)
  implicit val ac7802Format = new OptionalStringFormat(AC7802.apply)
  implicit val ac7803Format = new OptionalStringFormat(AC7803.apply)
  implicit val ac7804Format = new OptionalIntegerFormat(AC7804.apply)
  implicit val ac7805Format = new OptionalIntegerFormat(AC7805.apply)
  implicit val ac7806Format = new OptionalStringFormat(AC7806.apply)
  implicit val ac7900Format = new OptionalBooleanFormat(AC7900.apply)
  implicit val ac7901Format = new OptionalStringFormat(AC7901.apply)

  implicit val relatedPartyTransactionFormatWIthDefaults = new Format[RelatedPartyTransaction] {
    val baseFormat = Json.format[RelatedPartyTransaction]

    override def reads(json: JsValue): JsResult[RelatedPartyTransaction] = baseFormat
      .compose(withDefault("ac7801", AC7801(None)))
      .compose(withDefault("ac7802", AC7802(None)))
      .compose(withDefault("ac7803", AC7803(None)))
      .compose(withDefault("ac7804", AC7804(None)))
      .compose(withDefault("ac7805", AC7805(None)))
      .reads(json)

    override def writes(o: RelatedPartyTransaction): JsValue = baseFormat.writes(o)
  }

  implicit val relatedPartyTransactionsFormatWIthDefaults = new Format[RelatedPartyTransactions] {
    val baseFormat = Json.format[RelatedPartyTransactions]

    override def reads(json: JsValue): JsResult[RelatedPartyTransactions] = baseFormat
      .compose(withDefault("ac7806", AC7806(None)))
      .reads(json)

    override def writes(o: RelatedPartyTransactions): JsValue = baseFormat.writes(o)
  }
  
  implicit val ac8081Format = new OptionalBooleanFormat(AC8081.apply)
  implicit val ac8082Format = new OptionalBooleanFormat(AC8082.apply)
  implicit val ac8083Format = new OptionalBooleanFormat(AC8083.apply)
  implicit val ac8084Format = new OptionalBooleanFormat(AC8084.apply)
  implicit val ac8085Format = new OptionalBooleanFormat(AC8085.apply)
  implicit val ac8088Format = new OptionalBooleanFormat(AC8088.apply)

  }

