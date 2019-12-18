package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.StartDate

package object sba {

  val sbaApplies2019 = LocalDate.parse("2018-10-29")

  def sbaApplies(apStartDate: StartDate): Boolean = apStartDate.value.isAfter(sbaApplies2019)


}
