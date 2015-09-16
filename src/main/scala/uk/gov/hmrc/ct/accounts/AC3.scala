package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{Input, CtDate, CtBoxIdentifier}

case class AC3(value: LocalDate) extends CtBoxIdentifier("Current Period of Accounts Start Date") with CtDate with Input
