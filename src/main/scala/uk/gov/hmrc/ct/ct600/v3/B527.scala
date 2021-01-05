/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._

case class B527(value: Option[BigDecimal]) extends CtBoxIdentifier("Restitution tax") with CtOptionalBigDecimal with Input
