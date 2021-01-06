/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7801Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter with MockFrs102AccountsRetriever {

  "AC301A" should {
    "be mandatory" in {
      AC7801(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7801"), "error.AC7801.required", None))
    }
  }
}
