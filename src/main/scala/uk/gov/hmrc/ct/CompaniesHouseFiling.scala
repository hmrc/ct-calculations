package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Input, CtBoolean, CtBoxIdentifier}

case class CompaniesHouseFiling(value: Boolean) extends CtBoxIdentifier("Companies House Filing") with CtBoolean with Input