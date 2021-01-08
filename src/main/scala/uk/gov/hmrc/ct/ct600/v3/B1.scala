/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}

case class B1(value: String) extends CtBoxIdentifier("Company Name") with CtString with Input
