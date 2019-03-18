/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, Linked}

case class B78(value: BigDecimal) extends CtBoxIdentifier("Net Corporation Tax liability") with CtBigDecimal

object B78 extends Linked[B65, B78] {

  override def apply(source: B65): B78 = B78(source.value)
}
