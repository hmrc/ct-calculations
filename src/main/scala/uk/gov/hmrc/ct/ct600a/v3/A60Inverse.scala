/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A60Inverse(value: Option[Int]) extends CtBoxIdentifier(name = "A60Inverse - Loans made during the return period which have been released/written off more than 9 months after period end date where relief is NOT YET DUE")
with CtOptionalInteger

object A60Inverse extends Calculated[A60Inverse, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A60Inverse = {
    import fieldValueRetriever._
    calculateA60Inverse(cp2(), loansToParticipators(), lpq07())
  }
}
