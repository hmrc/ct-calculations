package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.Matchers
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.accounts.{AC12, AC3, AC4, MockFrs10xAccountsRetriever}
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation}
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany
import uk.gov.hmrc.ct.utils.{Mocks, UnitSpec}
import uk.gov.hmrc.ct.utils.CatoLimits._

class AC24Spec extends UnitSpec with Mocks with Matchers with MockitoSugar with MockFrs10xAccountsRetriever {

  private val emptyTurnover = AC12(Some(0))

  private def makeAC24(value: Int, box: CtBoxIdentifier with CtOptionalInteger = emptyTurnover): AC24 = new AC24(Some(value)) {
    override def getCorrectBox(boxRetriever: BoxRetriever): Box = box
  }

  "AC25 validation" should {
    "validate successfully" when {
      "empty" in {
        doMocks()
        AC24(None).validate(boxRetriever) shouldBe validationSuccess
      }

      "zero" in {
        doMocks()
        AC24(Some(0)).validate(boxRetriever) shouldBe validationSuccess
      }
      "greater than 0 but less than 632000, when combined with AC12" in {
        doMocks()
        val validatedAC24 = makeAC24(5).validate(boxRetriever)

        validatedAC24 shouldBe validationSuccess
      }

      "greater than 0 but less than 632000, when combined with AC16" in {
        doMocks()
        val validatedAC24 = makeAC24(5, AC16(Some(0))).validate(boxRetriever)

        validatedAC24 shouldBe validationSuccess
      }
    }

    "fail" when {
      "when negative" in {
        doMocks()
        val validatedAC24 = makeAC24(-1).validate(boxRetriever).head.errorMessageKey

        validatedAC24.contains("mustBeZeroOrPositive") shouldBe true
      }

      "more than 632000" in {
        doMocks()
        val validatedAC24: CtValidation = makeAC24(turnoverHMRCMaximumValue + 1).validate(boxRetriever).head

        validatedAC24.errorMessageKey.contains(".hmrc.turnover.AC12.above.max") shouldBe true
        validatedAC24.args shouldBe Some(List("0", turnoverHMRCMaximumWithCommas))
      }


      "more than 632000 when combined with AC12" in {
        doMocks()
        val turnover = AC12(Some(2))
        val validatedAC24 = makeAC24(turnoverHMRCMaximumValue - 1, turnover).validate(boxRetriever).head

        validatedAC24.errorMessageKey.contains(".hmrc.turnover.AC12.above.max") shouldBe true
        validatedAC24.args shouldBe Some(List("0", turnoverHMRCMaximumWithCommas))
      }

      "more than 632000 when combined with AC16" in {
        doMocks()
        val grossProfitOrLoss = AC16(Some(2))
        val validatedAC24 = makeAC24(turnoverHMRCMaximumValue - 1, grossProfitOrLoss).validate(boxRetriever).head

        validatedAC24.errorMessageKey.contains(".hmrc.turnover.AC16.above.max") shouldBe true
        validatedAC24.args shouldBe Some(List("0", turnoverHMRCMaximumWithCommas))
      }
    }
  }

  private def doMocks(): Unit = {
    when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
    when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
    when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
  }
}
