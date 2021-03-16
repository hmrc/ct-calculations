/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3


import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.covidSupport.{CP125a}

case class B476(value: Option[Int]) extends CtBoxIdentifier("EOTHO overpayments") with CtOptionalInteger

object B476 extends Linked[CP125a, B476] {

  override def apply(source: CP125a): B476 = B476(source.value)
}