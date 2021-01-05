/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class B149(value: Option[String]) extends CtBoxIdentifier("Name of bank or building society") with CtOptionalString with Input
