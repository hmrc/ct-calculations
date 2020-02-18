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

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

/*
  This was labelled as deprecated("" ,"01-03-2016 or earlier").
  This was used for a filing period before the date provided.
 */
case class CP81Input(value: Option[Int]) extends CtBoxIdentifier(name = "Expenditure qualifying for first year allowance (FYA)") with CtOptionalInteger with Input

object CP81Input {

  def apply(int: Int): CP81Input = CP81Input(Some(int))
}
