/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}


case class AC188(value: Option[Int]) extends CtBoxIdentifier(name = "Revaluation reserve (previous PoA)") with CtOptionalInteger

object AC188 extends Linked[AC77, AC188] {

  override def apply(source: AC77): AC188 = AC188(source.value)
}
