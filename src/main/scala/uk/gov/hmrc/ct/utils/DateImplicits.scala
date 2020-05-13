

package uk.gov.hmrc.ct.utils

import org.joda.time.LocalDate

object DateImplicits {

  implicit class DateOperators(d:LocalDate){

    def >(other:LocalDate) = d.isAfter(other)
    def <(other:LocalDate) = d.isBefore(other)
    def >=(other:LocalDate) = d.isEqual(other) || d.isAfter(other)
    def <=(other:LocalDate) = d.isEqual(other) || d.isBefore(other)
  }

}
