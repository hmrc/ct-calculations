package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}

case class ProductName(value: String) extends CtBoxIdentifier("Product Name") with CtString with Input

