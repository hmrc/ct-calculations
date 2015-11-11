package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC22(value: Option[Int]) extends CtBoxIdentifier(name = "Current Other operating income")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
