/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class B1574(value: Option[String]) extends CtBoxIdentifier("Address Line 4") with CtOptionalString with Input
