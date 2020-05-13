

package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, Input, StartDate}

case class AC3(value: LocalDate) extends CtBoxIdentifier("Current Period of Accounts Start Date") with StartDate with Input
