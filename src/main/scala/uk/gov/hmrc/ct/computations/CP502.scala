package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP502(value: Option[Int]) extends CtBoxIdentifier("Ancillary income") with CtOptionalInteger with Input
