package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC411(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Cost of raw materials and consumables")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
