package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC30Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC30", AC30.apply)
}