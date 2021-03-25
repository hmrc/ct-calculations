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

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.{CP7, CP983}

case class B145(value: Option[Int]) extends CtBoxIdentifier(name = "Total turnover from trade") with CtOptionalInteger

object B145{
  def apply(source: CP7, source2: CP983): B145 = {
    (source.value, source2.value) match {
      case (None, None) => B145(None)
      case (s, s2) => B145(Some(s.getOrElse(0) + s2.getOrElse(0)))
    }
  }
}
