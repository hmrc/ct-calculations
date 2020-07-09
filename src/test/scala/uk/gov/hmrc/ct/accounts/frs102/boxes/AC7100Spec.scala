/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7100Spec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[Frs10xDirectorsBoxRetriever]

  "AC7100" should {
    "pass validation if true" in {
      AC7100(Some(true)).validate(boxRetriever) shouldBe empty
    }
    "fail validation if false" in {
      AC7100(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7100"), "error.AC7100.required.true"))
    }
    "fail validation if not answered" in {
      AC7100(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7100"), "error.AC7100.required.true"))
    }
  }

}
