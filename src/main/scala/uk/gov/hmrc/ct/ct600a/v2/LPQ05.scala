/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class LPQ05(value: Option[Boolean]) extends CtBoxIdentifier(name = "Is the company controlled by one or more participators who are also directors?") with CtOptionalBoolean with Input
