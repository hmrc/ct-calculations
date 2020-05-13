/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._

case class B6(value: Option[Boolean]) extends CtBoxIdentifier("SME") with CtOptionalBoolean with Input
