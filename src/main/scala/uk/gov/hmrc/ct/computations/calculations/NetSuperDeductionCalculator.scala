/*
 * Copyright 2022 HM Revenue & Customs
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
import scala.math.BigDecimal

trait NetSuperDeductionCalculator extends CtTypeConverters {

  def netSuperDeductionClaim(cp677: CP677, cp678: CP678): CP679 = {
    if (cp677.getOrElse(BigDecimal(0)).compareTo(cp678.getOrElse(BigDecimal(0))) >=0)
      CP679(Some(cp677.minus(cp678)))
    else
      CP679(None)
    }

  def netSuperDeductionBalancingCharge(cp677: CP677, cp678: CP678): CP680 = {
    if (cp677.getOrElse(BigDecimal(0)).compareTo(cp678.getOrElse(BigDecimal(0))) < 0)
      CP680(Some(cp678.minus(cp677)))
    else
      CP680(None)
    }
}
