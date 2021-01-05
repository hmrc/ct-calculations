/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.accounts.AC1
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Linked}

case class B1001(value: Option[String]) extends CtBoxIdentifier("CRN") with CtOptionalString

object B1001 extends Linked[AC1, B1001] {
  override def apply(source: AC1): B1001 = {
    B1001(source.value)
  }
}
