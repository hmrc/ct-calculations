/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box

abstract class CtBoxIdentifier(val name: String = "Unknown"){
  def id:String = this.getClass.getSimpleName
}
