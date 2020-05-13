

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class B735(value: Option[Int]) extends CtBoxIdentifier("") with CtOptionalInteger with Input
