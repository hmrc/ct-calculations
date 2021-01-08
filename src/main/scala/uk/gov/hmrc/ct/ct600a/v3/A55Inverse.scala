/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever


case class A55Inverse(value: Option[Int]) extends CtBoxIdentifier(name = "A55Inverse - Loans made during the return period which have been repaid more than nine months after the end of the period and relief is NOT YET DUE")
with CtOptionalInteger

object A55Inverse extends Calculated[A55Inverse, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A55Inverse = {
    import fieldValueRetriever._
    calculateA55Inverse(cp2(), loansToParticipators(), lpq07())
  }
}
