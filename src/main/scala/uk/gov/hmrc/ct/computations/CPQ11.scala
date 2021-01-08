/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class CPQ11(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did you have machinery or plant?")
  with CtOptionalBoolean
  with Input

