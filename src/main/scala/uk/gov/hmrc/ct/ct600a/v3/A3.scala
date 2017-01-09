/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600a.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtDate, Linked}
import uk.gov.hmrc.ct.ct600.v3.B30


case class A3(value: LocalDate) extends CtBoxIdentifier(name = "AP Start date") with CtDate

object A3 extends Linked[B30, A3] {

  override def apply(source: B30): A3 = A3(source.value)
}
