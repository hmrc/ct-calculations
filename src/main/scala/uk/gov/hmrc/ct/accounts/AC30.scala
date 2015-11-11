package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC30(value: Option[Int]) extends CtBoxIdentifier(name = "Current Interest payable and similar charges")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
