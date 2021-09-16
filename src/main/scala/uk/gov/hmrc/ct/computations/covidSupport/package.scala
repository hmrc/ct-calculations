/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate

package object covidSupport {
  //Dates exclusive
  private val eothoStart = new LocalDate("2020-08-02")
  private val eothoEnd = new LocalDate("2020-09-01")
  private val covidStart = new LocalDate("2020-02-29")
  //Covid end TBC

  def doesPeriodCoverCovid(startDate: LocalDate, endDate: LocalDate): Boolean = checkPeriodWithinConstraints(startDate, endDate, Some(covidStart), None)
  def doesPeriodCoverEotho(startDate: LocalDate, endDate: LocalDate): Boolean = checkPeriodWithinConstraints(startDate, endDate, Some(eothoStart), Some(eothoEnd))

  private def checkPeriodWithinConstraints(startDate: LocalDate, endDate: LocalDate, constraintStart: Option[LocalDate], constraintEnd: Option[LocalDate]) = {
    constraintStart.forall(cs => endDate.isAfter(cs)) && constraintEnd.forall(ce => startDate.isBefore(ce))
  }
}
