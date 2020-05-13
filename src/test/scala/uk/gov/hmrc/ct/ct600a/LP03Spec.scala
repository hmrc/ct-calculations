/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600a.v2.formats.Loans
import uk.gov.hmrc.ct.ct600a.v2.{WriteOff, LP03}

class LP03Spec extends WordSpec with Matchers {

  "LP03 to json" should {
    "create valid json for a single writeoff using only required fields " in {
      val lp03 = LP03(writeOffs = Some(List(WriteOff("123", 1, new LocalDate(2012, 3, 1)))))
      Loans.toJsonString(lp03) shouldEqual """{"writeOffs":[{"loanId":"123","amountWrittenOff":1,"dateWrittenOff":"2012-03-01"}]}"""
    }

    "create valid json for multiple loans" in {
      val lp03 = LP03(writeOffs =  Some(List(WriteOff("123", 1, new LocalDate(2012, 3, 1)), WriteOff("456", 1, new LocalDate(2014, 3, 1))) ))
      Loans.toJsonString(lp03) shouldEqual """{"writeOffs":[{"loanId":"123","amountWrittenOff":1,"dateWrittenOff":"2012-03-01"},{"loanId":"456","amountWrittenOff":1,"dateWrittenOff":"2014-03-01"}]}"""
    }
    "create valid json for empty list of loans and not blow up!" in {
      val lp03 = LP03()
      Loans.toJsonString(lp03) shouldEqual """{}"""
    }

    "be added to another LP03" in {
      val lp03a = LP03(writeOffs = Some(List(WriteOff("123", 1, new LocalDate(2014, 4, 4)))))
      val lp03b = LP03(writeOffs = Some(List(WriteOff("456", 1, new LocalDate(2012, 3, 1)))))
      val expected = LP03(writeOffs = Some(List(WriteOff("123", 1, new LocalDate(2014, 4, 4)), WriteOff("456", 1, new LocalDate(2012, 3, 1)))))
      lp03a + lp03b shouldBe expected
    }

  }

  "LP03 from json" should {
    "create LP03 with single write off " in {
      Loans.lp03FromJsonString("""{"writeOffs":[{"loanId":"123","amountWrittenOff":1,"dateWrittenOff":"2012-03-01"}]}""") shouldBe LP03(writeOffs = Some(List(WriteOff("123", 1, new LocalDate(2012, 3, 1)))))
    }
    "create LP03 with multiple write offs " in {
      val expected = LP03(Some(List(WriteOff("123", 1, new LocalDate(2012, 3, 1)), WriteOff("456", 1, new LocalDate(2014, 3, 1)))))
      Loans.lp03FromJsonString("""{"writeOffs":[{"loanId":"123","amountWrittenOff":1,"dateWrittenOff":"2012-03-01"},{"loanId":"456","amountWrittenOff":1,"dateWrittenOff":"2014-03-01"}]}""") shouldBe expected
    }

    "create valid json for empty list of loans and not blow up 2!" in {
      Loans.lp03FromJsonString("{}") shouldEqual LP03()
    }
  }

}
