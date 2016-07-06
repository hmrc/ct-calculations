package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC35Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC35", AC35.apply)
}
