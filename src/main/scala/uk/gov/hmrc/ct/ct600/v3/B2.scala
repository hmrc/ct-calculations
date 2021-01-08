/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.accounts.AC1
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Linked}


case class B2(value: Option[String]) extends CtBoxIdentifier(name = "Company Registration Number (CRN)") with CtOptionalString

object B2 extends Linked[AC1, B2] {

  override def apply(source: AC1): B2 = B2(source.value)
}
