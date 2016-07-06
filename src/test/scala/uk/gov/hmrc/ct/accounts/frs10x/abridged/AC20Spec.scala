package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC20Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC20", AC20.apply)
}