

package uk.gov.hmrc.ct.box

import org.joda.time.LocalDate

trait StartDate extends CtDate {
  self: CtBoxIdentifier =>

  def value: LocalDate
}
