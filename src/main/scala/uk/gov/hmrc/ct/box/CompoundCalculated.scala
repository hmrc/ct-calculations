/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box

trait CompoundCalculated[T, C] {

   def calculate(container: C): T
}
