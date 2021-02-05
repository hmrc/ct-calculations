/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.stubs.StubbedFrs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.{AC15, AC16, AC17, AC24}
import uk.gov.hmrc.ct.accounts.frsAny.boxes.AC14

class AC435Spec extends AccountsMoneyValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  "AC435 calculates from boxes correctly" in {
    val testAc14 = 124
    val testAc15 = 126
    val testAc16 = 127
    val testAc17 = 128
    val testAc12 = 123
    val testAc24 = 124
    val testAc405 = 234
    val testAc410 = 345
    val testAc415 = 456
    val testAc420 = 567
    val testAc425 = 678
    val testAc34 = 789
    val testAc401 = 890
    val testAc403 = 901
    val boxRetriever = new StubbedFrs105AccountsBoxRetriever {
      override def ac12 = AC12(testAc12)
      override def ac14(): AC14 = AC14(Some(testAc14))
      override def ac15(): AC15 = AC15(Some(testAc15))
      override def ac16(): AC16 = AC16(Some(testAc16))
      override def ac17(): AC17 = AC17(Some(testAc17))
      override def ac24 = AC24(Some(testAc24))
      override def ac405 = AC405(Some(testAc405))
      override def ac410 = AC410(Some(testAc410))
      override def ac415 = AC415(Some(testAc415))
      override def ac420 = AC420(Some(testAc420))
      override def ac425 = AC425(Some(testAc425))
      override def ac34 = AC34(Some(testAc34))
      override def ac401 = AC401(testAc401)
      override def ac403 = AC403(Some(testAc403))
    }

    val ac435 = AC435.calculate(boxRetriever)

    ac435.value shouldBe Some(
        testAc12 +
        testAc24 +
        testAc405 +
        testAc401 -
        testAc410 -
        testAc415 -
        testAc420 -
        testAc425 -
        testAc34 -
        testAc403
    )
  }
}
