/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class B38(value: Option[Int]) extends CtBoxIdentifier("Franked investment") with CtOptionalInteger with Input
