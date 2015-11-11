package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC420(value: Option[Int]) extends CtBoxIdentifier(name = "Current Depreciation and other amounts written off assets")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
