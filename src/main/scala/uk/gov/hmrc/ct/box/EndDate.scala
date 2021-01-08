/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box

import org.joda.time.LocalDate

trait EndDate extends CtDate {
  self: CtBoxIdentifier =>

  def value: LocalDate
}
