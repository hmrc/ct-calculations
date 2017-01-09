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

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.accounts.AC1
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Linked}


case class B2(value: Option[String]) extends CtBoxIdentifier(name = "Company Registration Number (CRN)") with CtOptionalString

object B2 extends Linked[AC1, B2] {

  override def apply(source: AC1): B2 = B2(source.value)
}
