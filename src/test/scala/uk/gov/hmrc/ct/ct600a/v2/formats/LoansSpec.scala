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

package uk.gov.hmrc.ct.ct600a.v2.formats

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json
import uk.gov.hmrc.ct.ct600a.v2.{Loan, LP02}

class LoansSpec extends WordSpec with Matchers {

  implicit val formatter = Json.format[LP02Holder]

  "LP02 to json" should {
    "create valid json for single item in list" in {
      val loan = Loan(id = "l-01", name = "Loan 1", amount = 1999, repaid = false, lastRepaymentDate = None, totalAmountRepaid = None, endDateOfRepaymentAP = None)
      val json = Json.toJson(LP02Holder(LP02(Some(List(loan)))))
      json.toString shouldBe """{"lp02":{"loans":[{"id":"l-01","name":"Loan 1","amount":1999,"repaid":false}]}}"""
    }
    "create valid json for multiple items in list" in {
      val loan = Loan(id = "l-01", name = "Loan 1", amount = 1999, repaid = false, lastRepaymentDate = None, totalAmountRepaid = None, endDateOfRepaymentAP = None)
      val json = Json.toJson(LP02Holder(LP02(Some(List(loan, loan.copy(amount = 2000))))))
      json.toString shouldBe """{"lp02":{"loans":[{"id":"l-01","name":"Loan 1","amount":1999,"repaid":false},{"id":"l-01","name":"Loan 1","amount":2000,"repaid":false}]}}"""
    }
    "create valid json for empty list" in {
      val loan = Loan(id = "l-01", name = "Loan 1", amount = 1999, repaid = false, lastRepaymentDate = None, totalAmountRepaid = None, endDateOfRepaymentAP = None)
      val json = Json.toJson(LP02Holder(LP02(Some(List.empty))))
      json.toString shouldBe """{"lp02":{"loans":[]}}"""
    }
    "create valid json for None" in {
      val json = Json.toJson(LP02Holder(LP02(None)))
      json.toString shouldBe """{"lp02":{}}"""
    }
  }

  "LP02.asBoxString to json" should {
    "create valid json for single item in list" in {
      val loan = Loan(id = "l-01", name = "Loan 1", amount = 1999, repaid = false, lastRepaymentDate = None, totalAmountRepaid = None, endDateOfRepaymentAP = None)
      val json = LP02(Some(List(loan))).asBoxString
      json.toString shouldBe """Some({"loans":[{"id":"l-01","name":"Loan 1","amount":1999,"repaid":false}]})"""
    }
    "create valid json for multiple items in list" in {
      val loan = Loan(id = "l-01", name = "Loan 1", amount = 1999, repaid = false, lastRepaymentDate = None, totalAmountRepaid = None, endDateOfRepaymentAP = None)
      val json = LP02(Some(List(loan, loan.copy(amount = 2000)))).asBoxString
      json.toString shouldBe """Some({"loans":[{"id":"l-01","name":"Loan 1","amount":1999,"repaid":false},{"id":"l-01","name":"Loan 1","amount":2000,"repaid":false}]})"""
    }
    "create valid json for empty list" in {
      val loan = Loan(id = "l-01", name = "Loan 1", amount = 1999, repaid = false, lastRepaymentDate = None, totalAmountRepaid = None, endDateOfRepaymentAP = None)
      val json = LP02(Some(List.empty)).asBoxString
      json.toString shouldBe """Some({"loans":[]})"""
    }
    "create valid json for None" in {
      val json = LP02(None).asBoxString
      json.toString shouldBe "Some({})"
    }
  }

  "LP02 from json" should {
    "create loan from valid json" in {
      val json = Json.parse("""{"lp02":{"loans":[{"id":"l-01","name":"Loan 1","amount":1999,"repaid":false}]}}""")
      val loan = Loan(id = "l-01", name = "Loan 1", amount = 1999, repaid = false, lastRepaymentDate = None, totalAmountRepaid = None, endDateOfRepaymentAP = None)
      val holder = LP02Holder(LP02(Some(List(loan))))

      Json.fromJson[LP02Holder](json).get shouldBe holder
    }
    "create loan from valid json with no loans" in {
      val json = Json.parse("""{"lp02":{"loans":[]}}""")
      val holder = LP02Holder(LP02(Some(List())))

      Json.fromJson[LP02Holder](json).get shouldBe holder
    }
    "create loan from valid json with empty lp02" in {
      val json = Json.parse("""{"lp02":{}}""")
      val holder = LP02Holder(LP02(None))

      Json.fromJson[LP02Holder](json).get shouldBe holder
    }
  }

}

case class LP02Holder(lp02: LP02)
