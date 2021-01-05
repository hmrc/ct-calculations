/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A10(value: Option[Int]) extends CtBoxIdentifier(name = "A10 - Total amount of loans made during the return period which have been repaid, released, or written off more than nine months after the end of the period and relief is due now")
 with CtOptionalInteger

object A10 extends Calculated[A10, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A10 = {
    calculateA10(fieldValueRetriever.a8(), fieldValueRetriever.a9())
  }
}
