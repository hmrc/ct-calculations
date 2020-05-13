

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.BoxesFixture
import uk.gov.hmrc.ct.box.CtValidation


class ACQ5034Spec extends WordSpec with Matchers with BoxesFixture {

  "ACQ5034" should {

    "for Full Accounts pass validation" when {

      "all no value" in {
        ac44noValue
        ac45noValue
        acq5031noValue
        acq5032noValue
        acq5033noValue
        acq5035noValue

        ACQ5034(None).validate(boxRetriever) shouldBe empty
      }
    }

    "for Full Accounts fail validation" when {

      val cannotExistError = Set(CtValidation(Some("ACQ5034"),"error.ACQ5034.cannot.exist",None))

      "ac44,ac45 have no value and acq5034 has value" in {
        ac44noValue
        ac45noValue
        acq5031noValue
        acq5032noValue
        acq5033noValue
        acq5035noValue

        ACQ5034(Some(false)).validate(boxRetriever) shouldBe cannotExistError
      }

    }
  }
}
