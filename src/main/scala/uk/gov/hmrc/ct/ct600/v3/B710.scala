/*
 * Copyright 2024 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP247

case class B710(value: Option[Int]) extends CtBoxIdentifier("Balancing charges in main pool") with CtOptionalInteger

object B710 extends Linked[CP247, B710] {
  override def apply(source: CP247): B710 = {
    val b710Value = source.value match {
      case Some(0) => None
      case sourceValue => sourceValue
    }
    B710(b710Value)
  }
}
