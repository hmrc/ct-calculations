package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO02
import uk.gov.hmrc.ct.box.{Input, CtOptionalInteger, CtValidation}
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class MyStubbedComputationsRetriever(lec01: List[Car] = List(),
                                  cpq8: Option[Boolean] = None,
                                  cp78: Option[Int] = None,
                                  cp81Input: Option[Int] = None,
                                  cp82: Option[Int] = None,
                                  cp83: Option[Int] = None,
                                  cp84: Option[Int] = None,
                                  cp87Input: Option[Int] = None,
                                  cp88: Option[Int] = None,
                                  cp89: Option[Int] = None,
                                  cp666: Option[Int] = None,
                                  cp667: Option[Int] = None,
                                  cp668: Option[Int] = None,
                                  cp672: Option[Int] = None,
                                  cp673: Option[Int] = None,
                                  cp674: Option[Int] = None,
                                  cato02: Int = 0,
                                  cpAux1: Int = 0
                                   ) extends StubbedComputationsBoxRetriever {

  override def retrieveLEC01: LEC01 = LEC01(lec01)

  override def retrieveCPQ8: CPQ8 = CPQ8(cpq8)

  override def retrieveCP78: CP78 = CP78(cp78)

  override def retrieveCP666: CP666 = CP666(cp666)

  override def retrieveCP81Input: CP81Input = CP81Input(cp81Input)

  override def retrieveCP82: CP82 = CP82(cp82)

  override def retrieveCP83: CP83 = CP83(cp83)

  override def retrieveCP84: CP84 = CP84(cp84)

  override def retrieveCP667: CP667 = CP667(cp667)

  override def retrieveCP672: CP672 = CP672(cp672)

  override def retrieveCP673: CP673 = CP673(cp673)

  override def retrieveCP674: CP674 = CP674(cp674)

  override def retrieveCP87Input: CP87Input = CP87Input(cp87Input)

  override def retrieveCP88: CP88 = CP88(cp88)

  override def retrieveCP89: CP89 = CP89(cp89)

  override def retrieveCP668: CP668 = CP668(cp668)

  override def retrieveCATO02: CATO02 = CATO02(cato02)

  override def retrieveCPAux1: CPAux1 = CPAux1(cpAux1)
}


class MachineryAndPlantValidationSpec extends WordSpec with Matchers {
  val stubBocRetriever = new MyStubbedComputationsRetriever

  "CP78 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP78(Some(0)).validate(stubBocRetriever) shouldBe Set()
      CP78(None).validate(stubBocRetriever) shouldBe Set()
      CP78(Some(-1)).validate(stubBocRetriever) shouldBe Set(CtValidation(boxId = Some("CP78"), errorMessageKey = "error.CP78.mustBeZeroOrPositive"))
    }
  }

  "CP666 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP666(Some(0)).validate(stubBocRetriever) shouldBe Set()
      CP666(None).validate(stubBocRetriever) shouldBe Set()
      CP666(Some(-1)).validate(stubBocRetriever) shouldBe Set(CtValidation(boxId = Some("CP666"), errorMessageKey = "error.CP666.mustBeZeroOrPositive"))
    }
  }

  "CP81Input " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP81Input(Some(0)).validate(stubBocRetriever) shouldBe Set()
      CP81Input(None).validate(stubBocRetriever) shouldBe Set()
      CP81Input(Some(-1)).validate(stubBocRetriever) shouldBe Set(CtValidation(boxId = Some("CP81Input"), errorMessageKey = "error.CP81Input.mustBeZeroOrPositive"))
    }
  }

  "CP82 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP82(Some(0)).validate(stubBocRetriever) shouldBe Set()
      CP82(None).validate(stubBocRetriever) shouldBe Set()
      CP82(Some(-1)).validate(stubBocRetriever) shouldBe Set(CtValidation(boxId = Some("CP82"), errorMessageKey = "error.CP82.mustBeZeroOrPositive"))
    }
  }

  "CP83 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP83(Some(0)).validate(stubBocRetriever) shouldBe Set()
      CP83(None).validate(stubBocRetriever) shouldBe Set()
      CP83(Some(-1)).validate(stubBocRetriever) shouldBe Set(CtValidation(boxId = Some("CP83"), errorMessageKey = "error.CP83.mustBeZeroOrPositive"))
    }
  }

  "CP674 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP674(Some(0)).validate(stubBocRetriever) shouldBe Set()
      CP674(None).validate(stubBocRetriever) shouldBe Set()
      CP674(Some(-1)).validate(stubBocRetriever) shouldBe Set(CtValidation(boxId = Some("CP674"), errorMessageKey = "error.CP674.mustBeZeroOrPositive"))
    }
  }

  "CP84 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP84(Some(0)).validate(stubBocRetriever) shouldBe Set()
      CP84(None).validate(stubBocRetriever) shouldBe Set()
      CP84(Some(-1)).validate(stubBocRetriever) shouldBe Set(CtValidation(boxId = Some("CP84"), errorMessageKey = "error.CP84.mustBeZeroOrPositive"))
    }
  }

  "CP673 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP673(Some(0)).validate(stubBocRetriever) shouldBe Set()
      CP673(None).validate(stubBocRetriever) shouldBe Set()
      CP673(Some(-1)).validate(stubBocRetriever) shouldBe Set(CtValidation(boxId = Some("CP673"), errorMessageKey = "error.CP673.mustBeZeroOrPositive"))
    }
  }

  "CP667 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP667(Some(0)).validate(stubBocRetriever) shouldBe Set()
      CP667(None).validate(stubBocRetriever) shouldBe Set()
      CP667(Some(-1)).validate(stubBocRetriever) shouldBe Set(CtValidation(boxId = Some("CP667"), errorMessageKey = "error.CP667.mustBeZeroOrPositive"))
    }
  }

  "CP672 " should {
    "validate if present and non-negative or if not present, otherwise fail" in {
      CP672(Some(0)).validate(stubBocRetriever) shouldBe Set()
      CP672(None).validate(stubBocRetriever) shouldBe Set()
      CP672(Some(-1)).validate(stubBocRetriever) shouldBe Set(CtValidation(boxId = Some("CP672"), errorMessageKey = "error.CP672.mustBeZeroOrPositive"))
    }
  }

  "ccccccc " should {
    "ccccc" in {
      CP89 // MultipleConditionalMapping(optional(moneyNonNegative()), mainPoolClaimedNotGreaterThanMaxMainPool(filing))
      CP668 // MultipleConditionalMapping(optional(moneyNonNegative()), specialRatePoolClaimedNotGreaterThanMaxSpecialPool(filing)
    }
  }

  "CP87Input, given is trading and first Year Allowance Not Greater Than Max FYA" should {
    "validate if present and non-negative, otherwise fail" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP87Input(Some(0)).validate(stubTestComputationsRetriever) shouldBe Set()
      CP87Input(Some(-1)).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP87Input"), errorMessageKey = "error.CP87Input.mustBeZeroOrPositive"))
    }
  }

  "CP87Input, given is non-negative" should {
      "validate correctly when not greater than CP81  CPaux1" in {
        val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
          cpq8 = Some(false),
          cp81Input = Some(49),
          cpAux1 = 51)

        CP87Input(Some(100)).validate(stubTestComputationsRetriever) shouldBe Set()
      }

      "fail validation when greater than CP81  CPaux1" in {
        val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
          cpq8 = Some(false),
          cp81Input = Some(49),
          cpAux1 = 51)

        CP87Input(Some(101)).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP87Input"), errorMessageKey = "error.CP87Input.firstYearAllowanceClaimExceedsAllowance"))
      }

      "validate because FYA defaults to 0 when not entered" in {
        val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
          cpq8 = Some(true),
          cp81Input = Some(49),
          cpAux1 = 51)

        CP87Input(None).validate(stubTestComputationsRetriever) shouldBe Set()
      }

    "fail validation when trading but no value entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP87Input(None).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP87Input"), errorMessageKey = "error.CP87Input.fieldMustHaveValueIfTrading"))
    }
  }

  "CP88(annual investment allowance claimed)" should {

    "fail to validate when negative" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever()

      CP88(Some(-1)).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP88"), errorMessageKey = "error.CP88.mustBeZeroOrPositive"))
    }

    "validate correctly when not greater than the minimum of CATO02 (maxAIA) and CP83 (expenditureQualifyingAnnualInvestmentAllowance)" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cp81Input = Some(11),
        cato02 = 10
      )

      CP88(Some(10)).validate(stubTestComputationsRetriever) shouldBe Set()
    }

    "fails validation when greater than the minimum of CATO02 (maxAIA) and CP83 (expenditureQualifyingAnnualInvestmentAllowance)" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cp81Input = Some(11),
        cato02 = 10
      )

      CP88(Some(11)).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP88"), errorMessageKey = "error.CP88.annualInvestmentAllowanceExceeded"))
    }

    "fails validation when CATO02 (maxAIA) is the minimum" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(
        cp81Input = Some(10),
        cato02 = 11
      )

      CP88(Some(11)).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP88"), errorMessageKey = "error.CP88.annualInvestmentAllowanceExceeded"))
    }

    "fail validation when trading but no value entered" in {
      val stubTestComputationsRetriever = new MyStubbedComputationsRetriever(cpq8 = Some(false))

      CP88(None).validate(stubTestComputationsRetriever) shouldBe Set(CtValidation(boxId = Some("CP88"), errorMessageKey = "error.CP88.fieldMustHaveValueIfTrading"))
    }
  }
}
