/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.EndDate
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.B7
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever

package object losses {

  val lossReform2017 = LocalDate.parse("2017-03-31")

  def lossReform2017Applies(apEndDate: EndDate): Boolean = apEndDate.value.isAfter(lossReform2017)

  def cp997NIExceedsNonTradingProfit(retriever: ComputationsBoxRetriever): Boolean = retriever.cato01() < retriever.cp997NI().orZero

  def northernIrelandJourneyActive(retriever: AboutThisReturnBoxRetriever): Boolean = northernIrelandJourneyActive(retriever.b7())

  def northernIrelandJourneyActive(b7: B7): Boolean = b7.isTrue

  private val ecblStart = new LocalDate("2020-03-31")
  private val ecblEnd = new LocalDate("2022-04-01")

  def doesPeriodCoverECBL(endDate: LocalDate): Boolean = checkForDatePeriod(endDate, Some(ecblStart), Some(ecblEnd))

  private def checkForDatePeriod(endDate: LocalDate, constraintStart: Option[LocalDate], constraintEnd: Option[LocalDate]) = {
    constraintStart.forall(cs => endDate.isAfter(cs)) && constraintEnd.forall(ce => endDate.isBefore(ce))
  }

}