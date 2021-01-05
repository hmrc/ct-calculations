/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class HMRCAmendment(value: Boolean) extends CtBoxIdentifier("HMRC Amendment") with CtBoolean with Input
