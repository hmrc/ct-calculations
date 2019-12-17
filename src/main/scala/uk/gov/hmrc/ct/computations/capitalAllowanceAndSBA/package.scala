package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{EndDate, StartDate}

package object capitalAllowanceAndSBA {

  val sbaApplies2019 = LocalDate.parse("2018-10-29")

  def sbaApplies(apStartDate: StartDate): Boolean = apStartDate.value.isAfter(sbaApplies2019)


}
