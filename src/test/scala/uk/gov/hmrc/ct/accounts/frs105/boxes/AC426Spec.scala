

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, MockFrs105AccountsRetriever}

class AC426Spec extends AccountsMoneyValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  testAccountsMoneyValidation("AC426", AC426.apply)

}
