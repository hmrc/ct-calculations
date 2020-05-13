

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xDirectorsBoxRetriever, Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}


case class NotTradedStatementRequired(value: Boolean) extends CtBoxIdentifier(name = "Dormancy not traded statement required") with CtBoolean

object NotTradedStatementRequired extends Calculated[NotTradedStatementRequired, Frs10xDormancyBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever] {

  override def calculate(fieldValueRetriever: Frs10xDormancyBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever): NotTradedStatementRequired = {
    val dormant = fieldValueRetriever.acq8999().orFalse
    val cohoOnly = !fieldValueRetriever.hmrcFiling().value

    val result = dormant && !(cohoOnly && !fieldValueRetriever.acq8161().orFalse && !fieldValueRetriever.ac8021().orFalse)
    NotTradedStatementRequired(result)
  }
}
