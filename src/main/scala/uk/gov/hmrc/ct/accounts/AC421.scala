package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC421(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Depreciation and other amounts written off assets")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
