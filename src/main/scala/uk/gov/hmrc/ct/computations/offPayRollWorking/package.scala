/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.EndDate

package object offPayRollWorking {

  val opwApplies2020 = LocalDate.parse("2020-04-06")

  def isOPWEnabled(apEndDate: EndDate) = apEndDate.value.isAfter(opwApplies2020)
}
