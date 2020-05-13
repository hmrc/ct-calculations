

package uk.gov.hmrc.ct.accounts.frs102.validation

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait DirectorsReportEnabledCalculator {
  def calculateDirectorsReportEnabled(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Boolean = {
    val isCoHoFiling = boxRetriever.companiesHouseFiling().value
    val isHmrcFiling = boxRetriever.hmrcFiling().value
    val isMicroEntityFiling = boxRetriever.microEntityFiling().value
    val answeredYesToCoHoDirectorsReportQuestion = boxRetriever.ac8021().orFalse
    val answeredYesToHmrcDirectorsReportQuestion = boxRetriever.ac8023().orFalse

    (isCoHoFiling, isHmrcFiling) match {
      case (true, false) => answeredYesToCoHoDirectorsReportQuestion
      case _ => !isMicroEntityFiling || answeredYesToHmrcDirectorsReportQuestion
    }
  }
}
