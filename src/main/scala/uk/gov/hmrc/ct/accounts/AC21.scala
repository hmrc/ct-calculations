package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC21(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Administrative Expenses")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
