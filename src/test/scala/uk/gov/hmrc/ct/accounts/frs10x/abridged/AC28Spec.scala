package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC28Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC28", AC28.apply)
}