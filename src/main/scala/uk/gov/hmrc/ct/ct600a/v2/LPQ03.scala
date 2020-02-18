/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class LPQ03(value: Option[Boolean]) extends CtBoxIdentifier(name = "During this accounting period, did the company make any loans to participators or their associates that were not repaid") with CtOptionalBoolean with Input
