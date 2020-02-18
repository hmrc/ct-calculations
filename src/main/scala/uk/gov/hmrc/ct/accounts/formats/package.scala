/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.ct.box.formats.{DateFormat, OptionalDateFormat, OptionalIntegerFormat, OptionalStringFormat}

package object formats {

  implicit val companyAddressFormat: Format[CompanyAddress] = Json.format[CompanyAddress]

  implicit val ac1Format: Format[AC1] = new OptionalStringFormat[AC1](AC1.apply)
  implicit val ac2Format: Format[AC2] = new OptionalStringFormat[AC2](AC2.apply)
  implicit val ac3Format: Format[AC3] = new DateFormat[AC3](AC3.apply)
  implicit val ac4Format: Format[AC4] = new DateFormat[AC4](AC4.apply)
  implicit val ac12Format: Format[AC12] = new OptionalIntegerFormat[AC12](AC12.apply)

  implicit val ac205Format: Format[AC205] = new OptionalDateFormat[AC205](AC205.apply)
  implicit val ac206Format: Format[AC206] = new OptionalDateFormat[AC206](AC206.apply)
}
