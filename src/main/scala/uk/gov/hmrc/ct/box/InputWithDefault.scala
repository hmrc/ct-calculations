/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box

trait InputWithDefault[T] {

  self: CtValue[Option[T]] =>

  def defaultValue: Option[T]

  def inputValue: Option[T]

  def inputProvided: Boolean = inputValue.nonEmpty

  def value: Option[T] = inputValue orElse defaultValue
}
