package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC17Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC17", AC17.apply)
}
