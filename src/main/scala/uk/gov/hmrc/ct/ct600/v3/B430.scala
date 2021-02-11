/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.losses._
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

// was b63
case class B430(value: BigDecimal) extends CtBoxIdentifier("Corporation Tax") with CtBigDecimal

object B430 extends CorporationTaxCalculator with Calculated[B430, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B430 = {

    if(northernIrelandJourneyActive(fieldValueRetriever)){

      calculateCorportationTaxForNIJourney(
        fieldValueRetriever.b345(),
        fieldValueRetriever.b360(),
        fieldValueRetriever.b395(),
        fieldValueRetriever.b410()
      )

    }else{

      calculateCorporationTax(fieldValueRetriever.b345(), fieldValueRetriever.b395())
    }

  }

}
