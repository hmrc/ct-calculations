/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2


import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, Input, StartDate}

case class E1021(value: LocalDate) extends CtBoxIdentifier("Accounting Period Start Date") with StartDate with Input
