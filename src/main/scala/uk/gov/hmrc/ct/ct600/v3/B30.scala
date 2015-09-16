/*
 * Copyright 2015 HM Revenue & Customs
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

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtDate, CtBoxIdentifier, Linked}
import uk.gov.hmrc.ct.computations.CP1

case class B30(value: LocalDate) extends CtBoxIdentifier(name = "AP Start date") with CtDate

object B30 extends Linked[CP1, B30] {

  override def apply(source: CP1): B30 = B30(source.value)
}
