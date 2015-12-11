package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600.v3.B480
import uk.gov.hmrc.ct.ct600a.v3.A80


class FilterS419TaxNoneIfZeroSpec extends WordSpec with Matchers {

  "B480" should {
    "be A80 value if A80 != 0" in new FilterS419TaxNoneIfZero {
      val a80 = A80(Some(95))
      filterZero(a80) shouldBe B480(Some(95))
    }
    "be none if A80 == 0" in new FilterS419TaxNoneIfZero {
      val a80 = A80(Some(0))
      filterZero(a80) shouldBe B480(None)
    }
  }
}