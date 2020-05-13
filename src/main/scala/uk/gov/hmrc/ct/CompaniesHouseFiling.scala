

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class CompaniesHouseFiling(value: Boolean) extends CtBoxIdentifier("Companies House Filing") with CtBoolean with Input
