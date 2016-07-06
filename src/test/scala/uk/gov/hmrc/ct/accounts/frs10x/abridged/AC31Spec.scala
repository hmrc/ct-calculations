package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC31Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC31", AC31.apply)
}