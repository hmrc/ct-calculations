/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}

case class E5(value: String) extends CtBoxIdentifier("Charity/CASC repayment reference") with CtString with Input
