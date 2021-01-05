/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A8(value: Option[Int]) extends CtBoxIdentifier(name = "A8 - Information about loans made during the return period which have been repaid more than nine months after the end of the period and relief is due now")
 with CtOptionalInteger

object A8 extends Calculated[A8, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A8 = {
    import fieldValueRetriever._
    calculateA8(cp2(), lp02(), lpq07())
  }
}
