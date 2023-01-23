/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v3.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.ct600.v3._

trait B586Calculator extends CtTypeConverters{

  def calculateB586(b360: B360, b410: B410, b330: B330, b380: B380): B586 = {

    (b330, b380) match {

      case (B330(_), B380(_)) =>
        B586(b360.value.map(_ + b410.orZero))
      case _ =>
        B586(b360.value)
    }

  }

}
