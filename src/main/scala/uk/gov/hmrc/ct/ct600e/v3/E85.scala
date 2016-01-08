package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class E85(value: Option[Int]) extends CtBoxIdentifier("Income Other sources") with CtOptionalInteger with Input

