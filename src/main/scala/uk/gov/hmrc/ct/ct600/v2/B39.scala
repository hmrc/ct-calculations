

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class B39(value: Option[Int]) extends CtBoxIdentifier("Number of associated companies in this period") with CtOptionalInteger with Input
