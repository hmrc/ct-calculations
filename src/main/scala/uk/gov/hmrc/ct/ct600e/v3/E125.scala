/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600e.v3.calculations.ExpenditureCalculator
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E125(value: Option[Int]) extends CtBoxIdentifier("Expenditure: Total of boxes E95 to E120") with CtOptionalInteger

object E125 extends Calculated[E125, CT600EBoxRetriever] with ExpenditureCalculator {
  override def calculate(boxRetriever: CT600EBoxRetriever): E125 = {
    calculateTotalExpenditure(
      e95 = boxRetriever.e95(),
      e100 = boxRetriever.e100(),
      e105 = boxRetriever.e105(),
      e110 = boxRetriever.e110(),
      e115 = boxRetriever.e115(),
      e120 = boxRetriever.e120()
    )
  }
}
