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

import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Linked}


case class B4(value: String) extends CtBoxIdentifier(name = "Company Type") with CtString

object B4 extends Linked[FilingCompanyType, B4] {

  override def apply(source: FilingCompanyType): B4 = B4(source.value.toString)
}
