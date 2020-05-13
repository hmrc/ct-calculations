

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class E8(value: Option[Int]) extends CtBoxIdentifier("Deed of covenant") with CtOptionalInteger with Input
