/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.box._

case class AC12(value: Option[Int]) extends CtBoxIdentifier(name = "Current Turnover/Sales") with CtOptionalInteger with Input

object AC12 {

  def apply(value: Int): AC12 = AC12(Some(value))
}