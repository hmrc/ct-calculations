/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A9(value: Option[Int]) extends CtBoxIdentifier(name = "A9 - Date that part/whole loan released/written off more than 9 months after period end date and date completing return is later than resolution date")
 with CtOptionalInteger

object A9 extends Calculated[A9, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A9 = {
    import fieldValueRetriever._
    calculateA9(cp2(), lp03(), lpq07())
  }
}
