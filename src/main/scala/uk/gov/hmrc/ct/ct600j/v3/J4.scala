/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtDate, Linked}
import uk.gov.hmrc.ct.ct600.v3.B35


case class J4(value: LocalDate) extends CtBoxIdentifier(name = "AP End date") with CtDate

object J4 extends Linked[B35, J4] {

  override def apply(source: B35): J4 = J4(source.value)
}
