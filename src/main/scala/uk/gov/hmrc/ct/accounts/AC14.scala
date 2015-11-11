package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC14(value: Option[Int]) extends CtBoxIdentifier(name = "Current Cost of sales")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
