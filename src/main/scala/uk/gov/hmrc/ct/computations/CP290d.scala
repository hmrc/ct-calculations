/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP290d(value: Option[Int]) extends CtBoxIdentifier(name = "Post 1/4/17 main land losses brought forward against Northern Ireland TP") with CtOptionalInteger

object CP290d extends Linked[CP283d, CP290d] {
  def apply(source: CP283d): CP290d = CP290d(source.value)
}
