/*
 * Copyright 2021 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Linked}
import uk.gov.hmrc.ct.ct600.v3.B3


case class A2(value: String) extends CtBoxIdentifier(name = "Tax reference") with CtString

object A2 extends Linked[B3, A2] {

  override def apply(source: B3): A2 = A2(source.value)
}
