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

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Linked}

case class A1(value: Option[Boolean]) extends CtBoxIdentifier(name = "A1 Loans made during the period released or written off before the end of the period") with CtOptionalBoolean

object A1 extends Linked[LPQ09, A1] {

  override def apply(source: LPQ09): A1 = A1(source.value)
}
