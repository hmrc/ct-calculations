/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.box._

case class AC7991(value: Option[Boolean]) extends CtBoxIdentifier(name = "Enter Commitments by way of guarantee note?")
  with CtOptionalBoolean
  with Input
