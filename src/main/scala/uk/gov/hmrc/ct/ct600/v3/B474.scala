/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.covidSupport.CP125b

case class B474(value: Option[Int]) extends CtBoxIdentifier("JRB overpayments") with CtOptionalInteger

object B474 extends Linked[CP125b, B474] {

  override def apply(source: CP125b): B474 = B474(source.value)
}