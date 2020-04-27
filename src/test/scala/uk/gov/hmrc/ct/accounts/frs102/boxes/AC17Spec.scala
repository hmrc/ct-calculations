/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs102.stubs.StubbedFullAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts._

class AC17Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with AccountsPreviousPeriodValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  testAccountsMoneyValidation("AC17", AC17.apply)

  testAccountsPreviousPoAValidation("AC17", AC17.apply)

  "AC17 calculates from boxes correctly" in {
    val testAc13 = 123
    val testAc15 = 456
    val testAc402 = 678
    val testAc404 = 910
    val boxRetriever = new StubbedFullAccountsBoxRetriever {
      override def ac13 = AC13(Some(testAc13))
      override def ac15 = AC15(Some(testAc15))
      override def ac402 = AC402(testAc402)
      override def ac404 = AC404(testAc404)
    }

    val ac16 = AC17.calculate(boxRetriever)

    ac16.value shouldBe Some(testAc13 + testAc402 - testAc15 - testAc404)
  }
}
