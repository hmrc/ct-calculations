/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A12(value: Option[Int]) extends CtBoxIdentifier(name = "A12 - Total of all loans outstanding at end of return period - including all loans outstanding at the end of the return period, whether they were made in this period or an earlier one")
 with CtOptionalInteger

object A12 extends Calculated[A12, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A12 = {
    calculateA12(fieldValueRetriever.a2(), fieldValueRetriever.lp04())
  }
}
