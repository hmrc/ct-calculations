/*
 * Copyright 2020 HM Revenue & Customs
 *
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
