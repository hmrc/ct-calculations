/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600ei.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, CtOptionalString, Input}

case class DIT002(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did the company export goods or services?") with CtOptionalBoolean with Input

object DIT002 {
  def apply(value: Boolean): DIT002 = DIT002(Some(value))
}



