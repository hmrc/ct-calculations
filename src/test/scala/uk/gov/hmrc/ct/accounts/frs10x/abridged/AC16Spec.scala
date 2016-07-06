package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC16Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC16", AC16.apply)
}
