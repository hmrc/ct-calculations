/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, EndDate, Input}

case class AC4(value: LocalDate) extends CtBoxIdentifier("Current Period of Accounts End Date") with EndDate with Input
