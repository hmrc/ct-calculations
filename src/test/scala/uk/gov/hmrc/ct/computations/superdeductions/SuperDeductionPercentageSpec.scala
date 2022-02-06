package uk.gov.hmrc.ct.computations.superdeductions

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AC3, AC4, AC5, AC6}
import uk.gov.hmrc.ct.computations.{HmrcAccountingPeriod, calculations}

class SuperDeductionPercentageSpec extends WordSpec with Matchers {
  val superDeductionPeriod = SuperDeductionPeriod(AC5(new LocalDate(2021,4,1)), AC6(new LocalDate(2023,3,31)))
  "SuperDeductionPercentage" should {
    "be 0 if filing period does not overlap super deduction period " in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(new LocalDate(2019,4,1)), AC4(new LocalDate(2020,3,31)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(0)
    }

    "be 1.30 if filing period is between  super deduction period " in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(new LocalDate(2021,5,1)), AC4(new LocalDate(2022,4,30)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(BigDecimal(1.30000))
    }

    "be 1.14959 if filing period overlaps  super deduction period by 182 days" in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(new LocalDate(2022,10,1)), AC4(new LocalDate(2023,9,30)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(BigDecimal(1.14959))
    }

    "be 1.07397 if filing period overlaps  super deduction period by 90 days" in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(new LocalDate(2023,1,1)), AC4(new LocalDate(2023,12,31)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(1.07397)
    }


    "be 1.04849 if filing period overlaps  super deduction period by 59 days" in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(new LocalDate(2023,2,1)), AC4(new LocalDate(2024,1,31)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(1.04849)
    }


    "be correct for leap year" in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(new LocalDate(2023,3,10)), AC4(new LocalDate(2024,3,9)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(1.01803)
    }
  }
}
