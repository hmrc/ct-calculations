package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC18(value: Option[Int]) extends CtBoxIdentifier(name = "Current Distribution costs")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
