/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.MockFrs10xAccountsRetriever
import uk.gov.hmrc.ct.utils.UnitSpec

class AC25Spec extends UnitSpec with MockFrs10xAccountsRetriever {

  "AC25 validation" should {
    "validate successfully" when {
      "ac25 is a positive integer" in {
        AC25(Some(666)).validate(boxRetriever) shouldBe validationSuccess
      }
    }
    "fail validation" when {
      "ac25 is a negative integer" in {
        AC25(Some(-1)).validate(boxRetriever) shouldBe mustBeZeroOrPositiveError("AC25")
      }
    }
  }
}
