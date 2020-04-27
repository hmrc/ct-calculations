/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs102.stubs.StubbedFullAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts._

class AC16Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  testAccountsMoneyValidation("AC16", AC16.apply)

  "AC16 calculates from boxes correctly" in {
    val testAc12 = 123
    val testAc14 = 456
    val testAc401 = 678
    val testAc403 = 910
    val boxRetriever = new StubbedFullAccountsBoxRetriever {
      override def ac12 = AC12(testAc12)
      override def ac14 = AC14(Some(testAc14))
      override def ac401 = AC401(testAc401)
      override def ac403 = AC403(testAc403)
    }

    val ac16 = AC16.calculate(boxRetriever)

    ac16.value shouldBe Some(testAc12 + testAc401 - testAc14 - testAc403)
  }
}
