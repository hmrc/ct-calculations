/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class AbridgedFiling(value: Boolean) extends CtBoxIdentifier("Abridged Filing") with CtBoolean with Input
