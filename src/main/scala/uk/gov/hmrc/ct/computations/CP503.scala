package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP503(value: Option[Int]) extends CtBoxIdentifier(name = "Income from property expenses") with CtOptionalInteger with Input

