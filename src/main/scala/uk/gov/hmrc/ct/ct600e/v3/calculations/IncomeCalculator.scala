/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3.calculations

import uk.gov.hmrc.ct.box.retriever.BoxRetriever._
import uk.gov.hmrc.ct.ct600e.v3._

trait IncomeCalculator {

  def calculateTotalIncome(e50: E50, e55: E55, e60: E60, e65: E65, e70: E70, e75: E75, e80: E80, e85: E85): E90 = {
    val incomeFields = Seq(e50, e55, e60, e65, e70, e75, e80, e85)

    if (anyHaveValue(incomeFields:_ *))
      E90(Some(incomeFields.map(_.orZero).sum))
    else E90(None)
  }
}
