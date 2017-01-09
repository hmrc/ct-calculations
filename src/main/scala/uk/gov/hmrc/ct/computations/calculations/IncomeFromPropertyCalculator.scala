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

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait IncomeFromPropertyCalculator extends CtTypeConverters {

  def netIncomeFromProperty(cp507: CP507, cp508: CP508): CP509 = {
    CP509(cp507 - cp508)
  }

  def totalIncomeFromProperty(cp509: CP509, cp510: CP510): CP511 = {
    CP511(cp509 + cp510)
  }
}
