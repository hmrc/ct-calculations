package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC21Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC21", AC21.apply)
}