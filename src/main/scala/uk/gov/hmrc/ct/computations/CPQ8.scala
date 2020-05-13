

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.AllowancesQuestionsValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ8(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did the company cease trading?")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with AllowancesQuestionsValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {


    if(isSBALive(boxRetriever.cp2())) {
      Set.empty[CtValidation]
    }
    else
      {
        validateAgainstCPQ7(boxRetriever, "CPQ8", value)
      }
  }

}