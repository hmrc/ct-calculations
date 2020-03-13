/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class LPQ04(value: Option[Boolean]) extends CtBoxIdentifier(name = "Is the company controlled by 5 or fewer participators none of whom are directors?") with CtOptionalBoolean with Input
