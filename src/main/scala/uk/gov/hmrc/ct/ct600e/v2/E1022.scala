/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, EndDate, Input}

case class E1022(value: LocalDate) extends CtBoxIdentifier("Accounting Period End Date") with EndDate with Input
