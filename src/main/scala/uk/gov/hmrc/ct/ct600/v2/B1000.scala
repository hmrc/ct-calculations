/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}

case class B1000(value: String) extends CtBoxIdentifier("Company name") with CtString with Input
