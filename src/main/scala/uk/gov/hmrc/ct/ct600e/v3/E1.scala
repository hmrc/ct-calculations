

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}

case class E1(value: String) extends CtBoxIdentifier("Company Name (name of charity or CASC)") with CtString with Input
