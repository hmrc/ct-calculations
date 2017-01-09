/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.ct600a

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600a.v2.formats.Loans
import uk.gov.hmrc.ct.ct600a.v2.{Loan, LP02}

class LP02Spec extends WordSpec with Matchers {

  val someId = "123"
  
  "LP02 to json" should {
    "create valid json for a single loan using only required fields " in {
      val lp02 = LP02(Some(List(new Loan(someId, "Bilbo", 123))))
      Loans.toJsonString(lp02) shouldEqual """{"loans":[{"id":"123","name":"Bilbo","amount":123,"repaid":false}]}"""
    }
    "create valid json for a single loan using all available fields" in {
      val lp02 = LP02(Some(List(new Loan(someId, "Bilbo", 123, false, Some(new LocalDate("1939-09-01")), Some(1)))))
      Loans.toJsonString(lp02) shouldEqual """{"loans":[{"id":"123","name":"Bilbo","amount":123,"repaid":false,"lastRepaymentDate":"1939-09-01","totalAmountRepaid":1}]}"""
    }
    "create valid json for multiple loans" in {
      val lp02 = LP02(Some(List(new Loan(someId, "Bilbo", 123, false), new Loan(someId, "Frodo", 456, false), new Loan(someId, "Gandalf", 789, false))))
      Loans.toJsonString(lp02) shouldEqual """{"loans":[{"id":"123","name":"Bilbo","amount":123,"repaid":false},{"id":"123","name":"Frodo","amount":456,"repaid":false},{"id":"123","name":"Gandalf","amount":789,"repaid":false}]}"""
    }
    "create valid json for empty list of loans and not blow up!" in {
      val lp02 = LP02(None)
      Loans.toJsonString(lp02) shouldEqual """{}"""
    }

    "be added to another LP02" in {
      val lp02a = LP02(Some(List(new Loan(someId, "Bilbo", 123))))
      val lp02b = LP02(Some(List(new Loan(someId, "Bob", 4567))))
      val expected = LP02(Some(List(new Loan(someId, "Bilbo", 123), new Loan(someId, "Bob", 4567))))
      lp02a + lp02b shouldBe expected
    }

  }


  "LP02 from json" should {
    "create LP02 with single participant " in {
      Loans.lp02FromJsonString("""{"loans":[{"id":"123","name":"Bilbo","amount":123,"repaid":false}]}""") shouldBe LP02(Some(List(new Loan(someId, "Bilbo", 123, false))))
    }
    "create LP02 with mulitple loans " in {
      Loans.lp02FromJsonString("""{"loans":[{"id":"123","name":"Bilbo","amount":123,"repaid":false},{"id":"123","name":"Frodo","amount":456,"repaid":false},{"id":"123","name":"Gandalf","amount":789,"repaid":false}]}""") shouldBe LP02(Some(List(new Loan(someId, "Bilbo", 123, false), new Loan(someId, "Frodo", 456, false), new Loan(someId, "Gandalf", 789, false))))
    }
    "create LP02 with empty participators list " in {
      Loans.lp02FromJsonString("""{}""") shouldBe LP02(None)
    }

  }

}
