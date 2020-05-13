

package uk.gov.hmrc.ct.accounts.frs102.validation

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, Validators}
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait DirectorsReportExistenceValidation extends Validators {

  def validateDirectorsReportCannotExist(boxId: String,
                                         includeDirectorsReportValue: Option[Boolean],
                                         boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever)(): Set[CtValidation] = {
    failIf(includeDirectorsReportValue.contains(false) && directorsReportPopulated(boxRetriever)) {
      Set(CtValidation(None, s"error.$boxId.directorsReport.cannot.exist"))
    }
  }

  def directorsReportPopulated(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Boolean = {
    import boxRetriever._

    directors().directors.nonEmpty ||
      anyHaveValue(
        acQ8003(),
        ac8033(),
        acQ8009(),
        ac8051(),
        ac8052(),
        ac8053(),
        ac8054(),
        ac8899()
      )
  }
}
