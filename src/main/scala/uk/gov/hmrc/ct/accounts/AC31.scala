package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, MustBeNoneOrZeroOrPositive}

case class AC31(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Interest payable and similar charges")
                                    with CtOptionalInteger with MustBeNoneOrZeroOrPositive with Input
