/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.box._

case class AC7800(value: Option[Boolean]) extends CtBoxIdentifier(name = "Enter Related party transactions note?")
  with CtOptionalBoolean
  with Input
