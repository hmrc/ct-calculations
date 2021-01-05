/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, Input, StartDate}

case class E3(value: LocalDate) extends CtBoxIdentifier("Accounting Period start date") with StartDate with Input
