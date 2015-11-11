package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC406(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Other Income")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
