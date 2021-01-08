/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger}

case class B12(value: Int) extends CtBoxIdentifier(name = "Non-trading gains on intangible fixed assets") with CtInteger
