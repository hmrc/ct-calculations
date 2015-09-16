package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{Input, CtDate, CtBoxIdentifier}

case class AC4(value: LocalDate) extends CtBoxIdentifier("Current Period of Accounts End Date") with CtDate with Input
