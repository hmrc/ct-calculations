package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP510(value: Option[Int]) extends CtBoxIdentifier(name = "Unallowable expenses") with CtOptionalInteger with Input



