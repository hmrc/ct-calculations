/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP247


case class B108(value: Option[Int]) extends CtBoxIdentifier("Balancing charges (Machinery & Plant - main pool)") with CtOptionalInteger

object B108 extends Linked[CP247, B108] {

  override def apply(source: CP247): B108 = B108(source.value)
}
