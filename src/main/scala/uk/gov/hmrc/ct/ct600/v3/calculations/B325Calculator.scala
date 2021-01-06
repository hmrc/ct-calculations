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

package uk.gov.hmrc.ct.ct600.v3.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.CP291
import uk.gov.hmrc.ct.ct600.v3._

trait B325Calculator extends CtTypeConverters {


  def calculateB325(b350: B350, b400: B400, b330: B330, b380: B380, b315: B315): B325 = {

    def isTwoFinancialYears: Boolean = {

      if(b330.isPositive && b380.hasValue)
        true
      else
        false
    }

    if(isTwoFinancialYears && (b350 + b400) <= b315.value){

      B325(Some(b350 + b400))
    }
    else if(!isTwoFinancialYears && b350 <= b315.value){

      B325(Some(b350))
    }
    else{

      B325(Some(0))
    }

  }
}
