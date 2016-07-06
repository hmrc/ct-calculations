package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.AccountsMoneyValidationFixture

class AC29Spec extends AccountsMoneyValidationFixture {

  testAccountsMoneyValidation("AC29", AC29.apply)
}
