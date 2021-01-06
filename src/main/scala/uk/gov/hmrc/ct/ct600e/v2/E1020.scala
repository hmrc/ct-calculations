/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}

case class E1020(value: String) extends CtBoxIdentifier("Company name") with CtString with Input
