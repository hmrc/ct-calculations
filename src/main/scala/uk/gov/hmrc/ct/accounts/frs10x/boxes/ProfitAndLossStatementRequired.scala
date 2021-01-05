/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}

case class ProfitAndLossStatementRequired(value: Boolean) extends CtBoxIdentifier(name = "Dormancy profit and loss statement required") with CtBoolean

object ProfitAndLossStatementRequired extends Calculated[ProfitAndLossStatementRequired, Frs10xDormancyBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever] {

  override def calculate(fieldValueRetriever: Frs10xDormancyBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever): ProfitAndLossStatementRequired = {
    val dormant = fieldValueRetriever.acq8999().orFalse
    val cohoOnly = !fieldValueRetriever.hmrcFiling().value

    val result = dormant && !(cohoOnly && !fieldValueRetriever.acq8161().orFalse)
    ProfitAndLossStatementRequired(result)
  }
}
