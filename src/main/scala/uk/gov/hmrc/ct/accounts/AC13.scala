package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{Input, MustBeNoneOrZeroOrPositive, CtOptionalInteger, CtBoxIdentifier}

case class AC13(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Turnover/Sales")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
