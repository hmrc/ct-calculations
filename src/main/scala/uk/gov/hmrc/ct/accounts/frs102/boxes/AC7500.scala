/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.box._

case class AC7500(value: Option[Boolean]) extends CtBoxIdentifier(name = "Enter loans to directors note?")
  with CtOptionalBoolean
  with Input
