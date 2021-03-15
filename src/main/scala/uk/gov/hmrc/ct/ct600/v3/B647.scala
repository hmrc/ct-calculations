/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.covidSupport.CP121

case class B647(value: Option[Int]) extends CtBoxIdentifier("Eat Out to Help Out Scheme reimbursed discounts included as taxable income") with CtOptionalInteger

object B647 extends Linked[CP121, B647] {

  override def apply(source: CP121): B647 = B647(source.value)
}