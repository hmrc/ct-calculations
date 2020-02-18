/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.approval

import play.api.libs.json._
import uk.gov.hmrc.ct.accounts.approval.boxes._
import uk.gov.hmrc.ct.box.formats.{OptionalBooleanFormat, OptionalDateFormat, OptionalStringFormat, StringFormat}

package object formats {

  private def withDefault[A](key:String, default:A)(implicit writes:Writes[A]) = __.json.update((__ \ key).json.copyFrom((__ \ key).json.pick orElse Reads.pure(Json.toJson(default))))

  implicit val ac8092Format = new OptionalStringFormat[AC8092](AC8092.apply)
  implicit val ac8091Format = new OptionalBooleanFormat[AC8091](AC8091.apply)
  implicit val ac198AFormat = new OptionalDateFormat[AC198A](AC198A.apply)
  implicit val ac199AFormat = new StringFormat[AC199A](AC199A.apply)

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
}
