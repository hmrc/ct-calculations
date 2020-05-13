

package uk.gov.hmrc.ct.box

import org.joda.time.LocalDate

trait EndDate extends CtDate {
  self: CtBoxIdentifier =>

  def value: LocalDate
}
