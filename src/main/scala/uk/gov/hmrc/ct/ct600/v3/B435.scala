/*
 * Copyright 2024 HM Revenue & Customs
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

import uk.gov.hmrc.ct.CATO05
import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, Linked}

// was B64
case class B435(value: BigDecimal) extends CtBoxIdentifier("Marginal Relief") with CtBigDecimal


object B435 extends Linked[CATO05, B435] {

  override def apply(source: CATO05): B435 = B435(source.value)
}