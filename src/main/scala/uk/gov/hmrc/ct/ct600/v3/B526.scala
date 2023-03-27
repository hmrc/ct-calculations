/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, Linked}
import uk.gov.hmrc.ct.computations.covidSupport.CP126


case class B526(value: BigDecimal) extends CtBoxIdentifier("Coronavirus support schemes overpayment now due") with CtBigDecimal

object B526 extends Linked[CP126, B526] {

  override def apply(source: CP126): B526 = B526(source.value)
}