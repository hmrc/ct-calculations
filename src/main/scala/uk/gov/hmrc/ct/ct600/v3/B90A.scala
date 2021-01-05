/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._

case class B90A(value: Option[String]) extends CtBoxIdentifier("Reason for lack of accounts.") with CtOptionalString with Input {
}
