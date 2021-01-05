/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.CtDate

package object capitalAllowanceAndSBA {

  val sbaApplies2019 = LocalDate.parse("2018-10-29")

  def sbaApplies(apDate: CtDate): Boolean = apDate.value.isAfter(sbaApplies2019)


}
