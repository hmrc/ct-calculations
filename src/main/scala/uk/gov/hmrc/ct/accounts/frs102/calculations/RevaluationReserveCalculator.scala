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

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.frs102.boxes._

trait RevaluationReserveCalculator {

  def calculateAC190(ac76: AC76, ac77: AC77, ac189: AC189): AC190 = {
    (ac76.value, ac77.value, ac189.value) match {
      case (Some(_), None, None) =>  AC190(Some(0))
      case (_, None, None) => AC190(None)
      case _ => AC190(Some(ac77.orZero + ac189.orZero))
    }
  }

}
