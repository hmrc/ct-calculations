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


trait TotalAdditionsCalculator extends CtTypeConverters {

  def totalAdditionsCalculation(cp46: CP46,
                                cp47: CP47,
                                cp48: CP48,
                                cp49: CP49,
                                cp51: CP51,
                                cp52: CP52,
                                cp53: CP53): CP54 = {
    CP54(cp46 + cp47 + cp48 + cp49 + cp51 + cp52 + cp53)
  }
}
