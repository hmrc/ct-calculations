/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.domain

import org.joda.time.LocalDate

object ValidationConstants {

  val MIN_MONEY_AMOUNT_ALLOWED = 1
  val MAX_MONEY_AMOUNT_ALLOWED = 99999999

  val ERROR_ARGS_DATE_FORMAT = "d MMMM YYYY"

  val EARLIEST_AP_END_DATE_CUTOFF = new LocalDate(2008, 3, 31)

  def toErrorArgsFormat(date: LocalDate) = date.toString(ERROR_ARGS_DATE_FORMAT)

}
