/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._

case class B8(value: Option[Boolean]) extends CtBoxIdentifier("Special circumstances") with CtOptionalBoolean with Input
