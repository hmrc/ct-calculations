

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, MockFrs105AccountsRetriever}

class AC64Spec extends AccountsMoneyValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  testAccountsMoneyValidationWithMin("AC64", minValue = 0, AC64)

}
