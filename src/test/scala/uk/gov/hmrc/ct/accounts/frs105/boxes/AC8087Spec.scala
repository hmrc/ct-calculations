/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs105AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC8087Spec extends WordSpec
                 with MockitoSugar
                 with Matchers
                 with BeforeAndAfter
                 with MockFrs105AccountsRetriever {

  "AC8087" should {

    "fail validation if is empty" in {
      AC8087(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC8087"), "error.AC8087.required"))
    }

    "pass validation if is not empty" in {
      AC8087(Some(true)).validate(boxRetriever) shouldBe Set.empty
    }

  }

}
