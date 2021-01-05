/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger}

case class B17(value: Int) extends CtBoxIdentifier(name = "Allowable losses including losses brought forward") with CtInteger
