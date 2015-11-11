package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC23(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Other operating income")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
