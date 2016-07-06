package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC19Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC19", AC19.apply)
}
