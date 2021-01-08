/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class RDQ1(value: Boolean) extends CtBoxIdentifier with CtBoolean with Input
