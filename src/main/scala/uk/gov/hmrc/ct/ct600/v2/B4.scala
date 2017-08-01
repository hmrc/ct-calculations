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

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP257


case class B4(value: Option[Int]) extends CtBoxIdentifier("Trading losses brought forward set against trading profits") with CtOptionalInteger

object B4 extends Linked[CP257, B4] {

  override def apply(source: CP257): B4 = B4(source.value)
}
