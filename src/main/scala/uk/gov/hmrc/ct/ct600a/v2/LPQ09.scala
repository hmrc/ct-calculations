/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class LPQ09(value: Option[Boolean]) extends CtBoxIdentifier(name = "Were any loans written off or released during this period?") with CtOptionalBoolean with Input
