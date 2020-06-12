/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC8085Spec extends WordSpec
                 with MockitoSugar
                 with Matchers
                 with BeforeAndAfter
                 with MockFrs102AccountsRetriever {

  "AC8085" should {

    "fail validation if is empty" in {
      AC8085(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC8085"), "error.AC8085.required"))
    }

    "pass validation if is not empty" in {
      AC8085(Some(true)).validate(boxRetriever) shouldBe Set.empty
    }

  }

}
