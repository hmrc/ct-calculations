package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class E70(value: Option[Int]) extends CtBoxIdentifier("Income From other charities – excluding any amounts included on form CT600") with CtOptionalInteger with Input

