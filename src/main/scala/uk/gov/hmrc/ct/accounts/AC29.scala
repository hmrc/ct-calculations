package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC29(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Interest receivable and similar income")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
