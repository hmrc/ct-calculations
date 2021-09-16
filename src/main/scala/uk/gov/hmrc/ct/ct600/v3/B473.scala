/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.covidSupport.CP124

case class B473(value: Option[Int]) extends CtBoxIdentifier("CJRS and JSS overpayment already assessed or voluntary disclosed") with CtOptionalInteger

object B473 extends Linked[CP124, B473] {

  override def apply(source: CP124): B473= B473(source.value)
}