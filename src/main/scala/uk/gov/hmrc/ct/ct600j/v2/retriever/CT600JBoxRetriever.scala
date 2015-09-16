package uk.gov.hmrc.ct.ct600j.v2.retriever

import uk.gov.hmrc.ct.box.retriever.BoxValues
import uk.gov.hmrc.ct.ct600j.v2.TAQ01

object CT600JBoxRetriever extends BoxValues[CT600JBoxRetriever]

trait CT600JBoxRetriever {

  def retrieveTAQ01(): TAQ01
}
