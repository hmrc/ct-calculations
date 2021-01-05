/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class B140(value: Option[Int]) extends CtBoxIdentifier("Yes, I want HMRC to retain repayments of up to X £") with CtOptionalInteger with Input
