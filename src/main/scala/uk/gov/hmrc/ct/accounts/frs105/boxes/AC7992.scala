/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.box._

case class AC7992(value: Option[Boolean]) extends CtBoxIdentifier(name = "Enter Advances and credits note?")
  with CtOptionalBoolean
  with Input
