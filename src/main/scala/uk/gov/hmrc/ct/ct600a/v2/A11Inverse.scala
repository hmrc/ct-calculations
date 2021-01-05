/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever


case class A11Inverse(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "A11Inverse - Relief not yet due for loans repaid, released or written off more than nine months after the end of the period")
with CtOptionalBigDecimal

object A11Inverse extends Calculated[A11Inverse, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A11Inverse = {
    calculateA11Inverse(fieldValueRetriever.a10Inverse())
  }
}
