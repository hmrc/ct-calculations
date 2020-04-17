/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.accounts.AC403
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP981(value: Option[Int]) extends CtBoxIdentifier(name = "Deductions from Off-payroll working (IR35)") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever ): Set[CtValidation] = {

    validateZeroOrPositiveInteger(this)

  }


}

object CP981 extends Linked[AC403, CP981] {
  override def apply(source: AC403): CP981 = CP981(source.value)
}
