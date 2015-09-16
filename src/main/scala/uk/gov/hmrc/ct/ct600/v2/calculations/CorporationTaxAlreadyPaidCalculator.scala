package uk.gov.hmrc.ct.ct600.v2.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.ct600.v2.{B86, B91, B92, B93}

trait CorporationTaxAlreadyPaidCalculator extends CtTypeConverters {

  def corporationTaxOutstanding(taxPayable: B86,
                                corporationTaxAlreadyPaid: B91): B92 = {
    B92((taxPayable minus corporationTaxAlreadyPaid.value) max 0)
  }

  def corporationTaxOverpaid(taxPayable: B86,
                             corporationTaxAlreadyPaid: B91): B93 = {
    B93(((taxPayable minus corporationTaxAlreadyPaid.value) min 0).abs )
  }
}

case class CorporationTaxAlreadyPaidParameters(taxPayable: B86,
                                               corporationTaxAlreadyPaid: B91)
