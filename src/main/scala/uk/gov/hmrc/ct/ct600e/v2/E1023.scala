/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}

case class E1023(value: String) extends CtBoxIdentifier("UTR") with CtString with Input
