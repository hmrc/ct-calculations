/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtValue, Input}

case class CompanyAddress(addressLine1: Option[String] = None,
                          addressLine2: Option[String] = None,
                          addressLine3: Option[String] = None,
                          addressLine4: Option[String] = None,
                          city: Option[String] = None,
                          county: Option[String] = None,
                          postCode: Option[String] = None,
                          country: Option[String] = None)
  extends CtBoxIdentifier(name = "Registered Company Address")
  with CtValue[CompanyAddress]
  with Input {

  override def value: CompanyAddress = this
}
