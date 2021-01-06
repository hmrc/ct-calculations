/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever


case class A65Inverse(value: Option[Int]) extends CtBoxIdentifier(name = "A65Inverse - Total amount of loans made during the return period which have been repaid, released, or written off more than nine months after the end of the period and relief is NOT YET DUE")
with CtOptionalInteger

object A65Inverse extends Calculated[A65Inverse, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A65Inverse = {
    import fieldValueRetriever._
    calculateA65Inverse(a55Inverse(), a60Inverse())
  }
}
