package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class E80(value: Option[Int]) extends CtBoxIdentifier("Income Gifts of real property received") with CtOptionalInteger with Input

