/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class E20(value: Option[Boolean]) extends CtBoxIdentifier("All income and gains are exempt from tax and have been, or will be, applied for charitable or qualifying purposes only") with CtOptionalBoolean with Input
