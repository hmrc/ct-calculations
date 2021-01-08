/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box

trait Linked[S <: CtValue[_], T <: CtBoxIdentifier] {
  def apply(source: S): T
}
