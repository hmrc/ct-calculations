/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.EndDate

package object offPayRollWorking {
  val opwApplies2020 = new LocalDate("2017-04-05")

  def isOPWEnabled(apEndDate: LocalDate) = apEndDate.isAfter(opwApplies2020)
}
