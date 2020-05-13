

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class CompaniesHouseSubmitted(value: Boolean) extends CtBoxIdentifier("Companies House Filing Submitted") with CtBoolean with Input
