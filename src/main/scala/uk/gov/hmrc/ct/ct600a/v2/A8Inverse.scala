/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever


case class A8Inverse(value: Option[Int]) extends CtBoxIdentifier(name = "A8Inverse - Loans made during the return period which have been repaid more than nine months after the end of the period and relief is NOT YET DUE")
with CtOptionalInteger

object A8Inverse extends Calculated[A8Inverse, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A8Inverse = {
    import fieldValueRetriever._
    calculateA8Inverse(cp2(), lp02(), lpq07())
  }
}
