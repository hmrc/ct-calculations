package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC410(value: Option[Int]) extends CtBoxIdentifier(name = "Current Cost of raw materials and consumables")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
