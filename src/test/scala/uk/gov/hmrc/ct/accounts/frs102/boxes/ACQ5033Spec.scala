

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.BoxesFixture
import uk.gov.hmrc.ct.box.CtValidation


class ACQ5033Spec extends WordSpec with Matchers with BoxesFixture {

  "ACQ5033" should {

    "for Full Accounts pass validation" when {

      "all no value" in {
        ac44noValue
        ac45noValue
        acq5031noValue
        acq5032noValue
        acq5034noValue
        acq5035noValue

        ACQ5033(None).validate(boxRetriever) shouldBe empty
      }
    }

    "for Full Accounts fail validation" when {

      val cannotExistError = Set(CtValidation(Some("ACQ5033"),"error.ACQ5033.cannot.exist",None))

      "ac44,ac45 have no value and acq5033 has value" in {
        ac44noValue
        ac45noValue
        acq5031noValue
        acq5032noValue
        acq5034noValue
        acq5035noValue

        ACQ5033(Some(false)).validate(boxRetriever) shouldBe cannotExistError
      }

    }
  }
}
