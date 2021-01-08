/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class E1010(value: Option[Boolean]) extends CtBoxIdentifier("Claiming exemption all or part") with CtOptionalBoolean with Input
