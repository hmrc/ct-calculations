/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.box._

case class AC7600(value: Option[Boolean]) extends CtBoxIdentifier(name = "Enter Changes in presentation and prior period adjustments note?")
  with CtOptionalBoolean
  with Input
