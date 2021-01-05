/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._

case class B85A(value: Option[Boolean]) extends CtBoxIdentifier("Is a repayment due for different period") with CtOptionalBoolean with Input {
}
