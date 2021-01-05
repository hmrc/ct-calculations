/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString}

case class B1573(value: Option[String]) extends CtBoxIdentifier("Address Line 3") with CtOptionalString
