/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class E1000(value: Option[String]) extends CtBoxIdentifier("Repayment reference") with CtOptionalString with Input
