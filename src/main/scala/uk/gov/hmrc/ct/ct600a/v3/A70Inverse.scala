/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever


case class A70Inverse(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "A70Inverse - Relief not yet due for loans repaid, released or written off more than nine months after the end of the period")
with CtOptionalBigDecimal

object A70Inverse extends Calculated[A70Inverse, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A70Inverse = {
    calculateA70Inverse(fieldValueRetriever.a65Inverse(), fieldValueRetriever.loansToParticipators(), fieldValueRetriever.cp2(), fieldValueRetriever.lpq07())
  }
}
