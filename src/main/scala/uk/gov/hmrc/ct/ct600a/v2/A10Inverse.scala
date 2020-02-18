/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever


case class A10Inverse(value: Option[Int]) extends CtBoxIdentifier(name = "A10Inverse - Total amount of loans made during the return period which have been repaid, released, or written off more than nine months after the end of the period and relief is NOT YET DUE")
with CtOptionalInteger

object A10Inverse extends Calculated[A10Inverse, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A10Inverse = {
    import fieldValueRetriever._
    calculateA10Inverse(a8Inverse(), a9Inverse())
  }
}
