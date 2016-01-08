package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class E50(value: Option[Int]) extends CtBoxIdentifier("Income Total turnover from exempt charitable trading activities") with CtOptionalInteger with Input

