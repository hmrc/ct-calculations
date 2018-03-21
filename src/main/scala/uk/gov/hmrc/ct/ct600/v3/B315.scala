/*
 * Copyright 2018 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

// was B37
case class B315(value: Int) extends CtBoxIdentifier(name = "Profits chargeable to corporation tax") with CtInteger

object B315 extends Calculated[B315,CT600BoxRetriever] {

  override def calculate(boxRetriever: CT600BoxRetriever): B315 = {


    def isTwoFinancialYears: Boolean = boxRetriever.b330().isPositive && boxRetriever.b380().hasValue


    if(isTwoFinancialYears && boxRetriever.b5().isTrue){

      B315(boxRetriever.b335().value + boxRetriever.b350().value + boxRetriever.b385().value + boxRetriever.b400().value)
    }
    else if(!isTwoFinancialYears && boxRetriever.b5().isTrue){

      B315(boxRetriever.b335().value + boxRetriever.b350().value)
    }
    else{

      B315(boxRetriever.cp295().value)
    }


  }

}