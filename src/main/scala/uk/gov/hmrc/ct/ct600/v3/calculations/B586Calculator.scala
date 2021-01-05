/*
 * Copyright 2020 HM Revenue & Customs
 *
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
