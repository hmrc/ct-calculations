

package uk.gov.hmrc.ct.ct600.v2.validation

import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600.v2.retriever.ReturnStatementsBoxRetriever

trait RSQ7MutuallyExclusiveWithRSQ8 {

  private def error(boxId: String) = CtValidation(Some(boxId), s"error.$boxId.mutuallyExclusive")

  def validateMutualExclusivity(boxRetriever: ReturnStatementsBoxRetriever): Set[CtValidation] =
    (boxRetriever.rsq7().value, boxRetriever.rsq8().value) match {
      case (Some(rsq7), Some(rsq8)) if rsq7 && rsq8 => Set(error("RSQ7"), error("RSQ8"))
      case _ => Set.empty
    }

}
