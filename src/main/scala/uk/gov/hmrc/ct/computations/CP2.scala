/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, EndDate, Input}

case class CP2(value: LocalDate) extends CtBoxIdentifier(name = "End date of accounting period") with EndDate with Input
