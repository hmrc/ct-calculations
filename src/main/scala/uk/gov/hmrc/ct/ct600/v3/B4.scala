/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Linked}


case class B4(value: String) extends CtBoxIdentifier(name = "Company Type") with CtString

object B4 extends Linked[FilingCompanyType, B4] {

  override def apply(source: FilingCompanyType): B4 = B4(source.value.toString)
}
