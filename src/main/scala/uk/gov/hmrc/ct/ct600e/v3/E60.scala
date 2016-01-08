package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class E60(value: Option[Int]) extends CtBoxIdentifier("Income UK land and buildings – excluding any amounts included on form CT600") with CtOptionalInteger with Input

