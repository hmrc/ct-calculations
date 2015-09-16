package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}


case class LP04(value: Option[Int]) extends CtBoxIdentifier(name = "Amount of other loans.") with CtOptionalInteger with Input
