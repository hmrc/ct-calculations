package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC38(value: Option[Int]) extends CtBoxIdentifier(name = "Current Dividends")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
