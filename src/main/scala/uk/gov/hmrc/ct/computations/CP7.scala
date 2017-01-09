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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._

case class CP7(inputValue: Option[Int], defaultValue: Option[Int]) extends CtBoxIdentifier(name = "Turnover/Sales") with CtOptionalInteger with MustBeNoneOrZeroOrPositive with InputWithDefault[Int]

object CP7 extends Linked[AP2, CP7] {

  def apply(inputValue: Option[Int]): CP7 = CP7(inputValue = inputValue, defaultValue = None)

  override def apply(source: AP2): CP7 = CP7(None, source.value)
}
