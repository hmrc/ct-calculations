/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class B156(value: Option[String]) extends CtBoxIdentifier("Full Name of Nominee") with CtOptionalString with Input
