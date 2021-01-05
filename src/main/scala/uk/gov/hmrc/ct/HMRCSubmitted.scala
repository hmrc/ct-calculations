/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class HMRCSubmitted(value: Boolean) extends CtBoxIdentifier("HMRC filing submitted") with CtBoolean with Input
