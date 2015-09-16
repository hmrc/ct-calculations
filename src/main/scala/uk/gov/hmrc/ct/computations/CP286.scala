package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP286(value: Option[Int]) extends CtBoxIdentifier(name = "Losses claimed from a later AP") with CtOptionalInteger with Input
