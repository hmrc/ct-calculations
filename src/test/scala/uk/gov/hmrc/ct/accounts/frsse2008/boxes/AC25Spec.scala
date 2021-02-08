package uk.gov.hmrc.ct.accounts.frsse2008.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.Matchers
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.accounts.frsse2008.{AC17, AC25}
import uk.gov.hmrc.ct.accounts.{AC3, AC4, MockFrsse2008AccountsBoxRetriever}
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation}
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany
import uk.gov.hmrc.ct.utils.CatoLimits._
import uk.gov.hmrc.ct.utils.{Mocks, UnitSpec}

class AC25Spec extends UnitSpec with Mocks with Matchers with MockitoSugar with MockFrsse2008AccountsBoxRetriever {

  private val emptyTurnover = AC17(Some(0))

  private def makeAC25(value: Int, box: CtBoxIdentifier with CtOptionalInteger = emptyTurnover): AC25 = new AC25(Some(value)) {
    override def getCorrectBox(boxRetriever: BoxRetriever): Box = box
  }

  "AC25 validation" should {
    "validate successfully" when {
      "empty" in {
        doMocks()
        AC25(None).validate(boxRetriever) shouldBe validationSuccess
      }

      "zero" in {
        doMocks()
        AC25(Some(0)).validate(boxRetriever) shouldBe validationSuccess
      }
      "greater than 0 but less than 632000, when combined with AC13" in {
        doMocks()
        val validatedAC25 = makeAC25(5).validate(boxRetriever)

        validatedAC25 shouldBe validationSuccess
      }
    }
    "fail" when {
      "negative" in {
        doMocks()
        val validatedAC25 = makeAC25(-1).validate(boxRetriever).head.errorMessageKey

        validatedAC25.contains("mustBeZeroOrPositive") shouldBe true
      }

      "more than 632000" in {
        doMocks()
        val validatedAC25: CtValidation = makeAC25(turnoverHMRCMaximumValue + 1).validate(boxRetriever).head

        validatedAC25.errorMessageKey.contains(".hmrc.turnover.AC17.above.max") shouldBe true
        validatedAC25.args shouldBe Some(List("0", turnoverHMRCMaximumWithCommas))
      }


      "more than 632000 when combined with AC17" in {
        doMocks()
        val turnover = AC17(Some(2))
        val validatedAC25 = makeAC25(turnoverHMRCMaximumValue - 1, turnover).validate(boxRetriever).head

        validatedAC25.errorMessageKey.contains(".hmrc.turnover.AC17.above.max") shouldBe true
        validatedAC25.args shouldBe Some(List("0", turnoverHMRCMaximumWithCommas))
      }
    }
  }

  private def doMocks(): Unit = {
    when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
    when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
    when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
  }
}