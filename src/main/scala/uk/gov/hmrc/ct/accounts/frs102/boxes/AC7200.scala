/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.box._

case class AC7200(value: Option[Boolean]) extends CtBoxIdentifier(name = "Enter Dividends note?")
  with CtOptionalBoolean
  with Input
