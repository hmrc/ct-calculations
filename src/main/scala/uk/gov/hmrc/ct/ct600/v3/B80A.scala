/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._

case class B80A(value: Option[Boolean]) extends CtBoxIdentifier("Is a repayment due for this period") with CtOptionalBoolean with Input {
}
