/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A9Inverse(value: Option[Int]) extends CtBoxIdentifier(name = "A9Inverse - Loans made during the return period which have been released/written off more than 9 months after period end date where relief is NOT YET DUE")
with CtOptionalInteger

object A9Inverse extends Calculated[A9Inverse, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A9Inverse = {
    import fieldValueRetriever._
    calculateA9Inverse(cp2(), lp03(), lpq07())
  }
}
