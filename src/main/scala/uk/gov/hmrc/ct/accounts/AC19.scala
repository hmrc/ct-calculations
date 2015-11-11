package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC19(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Distribution costs")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
