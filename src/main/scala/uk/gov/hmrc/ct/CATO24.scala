/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class CATO24(value: Option[Boolean]) extends CtBoxIdentifier(name = "Off payroll working") with CtOptionalBoolean with Input
