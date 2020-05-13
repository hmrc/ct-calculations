

package uk.gov.hmrc.ct.accounts.frs10x.retriever

import uk.gov.hmrc.ct.accounts.frs10x.boxes.ACQ8161
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs10xFilingQuestionsBoxRetriever extends AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def acq8161(): ACQ8161
}
