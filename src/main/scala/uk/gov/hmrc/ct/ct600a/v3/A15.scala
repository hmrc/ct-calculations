/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A15(value: Option[Int]) extends CtBoxIdentifier(name = "A15 Total Loans made during the return period which have not been repaid, released or written off before the end of the period") with CtOptionalInteger

object A15 extends Calculated[A15, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A15 = {
    calculateA15(fieldValueRetriever.loansToParticipators())
  }
}
