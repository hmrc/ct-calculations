/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class LPQ06(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you intend to file your return before 30 June 2014?") with CtOptionalBoolean with Input
