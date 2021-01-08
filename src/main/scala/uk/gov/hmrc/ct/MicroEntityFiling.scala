/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class MicroEntityFiling(value: Boolean) extends CtBoxIdentifier("Micro Entity Filing") with CtBoolean with Input
