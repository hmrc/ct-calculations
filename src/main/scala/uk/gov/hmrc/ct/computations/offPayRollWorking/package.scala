/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.EndDate

package object offPayRollWorking {
  val opwApplies2020 = new LocalDate("06-04-2017")

  def isOPWEnabled(apEndDate: EndDate) = apEndDate.value.isAfter(opwApplies2020)
}
