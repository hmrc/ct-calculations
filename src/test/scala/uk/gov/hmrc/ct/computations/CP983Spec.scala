/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP983Spec extends WordSpec with Matchers with MockitoSugar {

  "CP983 creation" should {
    "pulls value from AC401" in {
      val testValue = 101
      val ac401 = AC401(Some(testValue))
      val cp983 = CP983(ac401)

      cp983.value shouldBe testValue
    }

    "defaults to zero if AC401 empty" in {
      val emptyAc401 = AC401(None)
      val cp983 = CP983(emptyAc401)

      cp983.value shouldBe 0
    }
  }
}
