package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC34Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC34", AC34.apply)
}