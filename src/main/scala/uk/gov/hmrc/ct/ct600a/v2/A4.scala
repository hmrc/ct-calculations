/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A4(value: Option[Int]) extends CtBoxIdentifier(name = "A4 - Amount repaid - sum of all iterations of amount of loan repayed (after period end but equal or less than 9 months from period end)") with CtOptionalInteger

object A4 extends Calculated[A4, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A4 = {
    calculateA4(fieldValueRetriever.cp2(), fieldValueRetriever.lp02())
  }
}
