

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.{MockFullAccountsRetriever, AccountsPreviousPeriodValidationFixture, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever

class AC15Spec extends AccountsMoneyValidationFixture[FullAccountsBoxRetriever] with AccountsPreviousPeriodValidationFixture[FullAccountsBoxRetriever] with MockFullAccountsRetriever {

  testAccountsMoneyValidationWithMin("AC15", 0, AC15.apply)

  testAccountsPreviousPoAValidation("AC15", AC15.apply)
}
