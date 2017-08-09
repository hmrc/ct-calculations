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

import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait LossesCarriedForwardsCalculator extends CtTypeConverters {

  def lossesCarriedForwardsCalculation(cp281: CP281,
                                       cp118: CP118,
                                       cp283: CP283,
                                       cp998: CP998,
                                       cp287: CP287,
                                       cp997: CP997): CP288 =
    CP288(Some((cp281 + cp118 - cp283 - cp998 - cp997 - cp287) max 0))

}
