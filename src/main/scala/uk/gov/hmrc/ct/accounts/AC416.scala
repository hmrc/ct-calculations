package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC416(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Staff costs")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
