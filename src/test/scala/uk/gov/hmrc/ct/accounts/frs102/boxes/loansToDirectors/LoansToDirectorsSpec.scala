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

package uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes.AC7500
import uk.gov.hmrc.ct.accounts.frs102.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC205, AC206}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class LoansToDirectorsSpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfterEach {
  import LoansToDirectorsMockSetup._

  val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever]

  val validLoan = LoanToDirector(
    uuid = "uuid",
    ac304A = AC304A(Some("director")),
    ac305A = AC305A(Some("description")),
    ac306A = AC306A(Some(99)),
    ac307A = AC307A(None),
    ac308A = AC308A(None),
    ac309A = AC309A(None)
  )
  
  "LoansToDirectors" should {
    "validate successfully when no validation errors are present" in {
      setupDefaults(mockBoxRetriever)

      val loans = LoansToDirectors(loans = List(validLoan), ac7501 = AC7501(None))

      loans.validate(mockBoxRetriever) shouldBe empty
    }

    "return cannot exist error when there are errors but AC7500 not set to true" in {
      setupDefaults(mockBoxRetriever)
      when(mockBoxRetriever.ac7500()).thenReturn(AC7500(None))

      val loan = LoanToDirector(
        uuid = "uuid",
        ac304A = AC304A(Some("director 1")),
        ac305A = AC305A(Some("description ^")),
        ac306A = AC306A(Some(-1)),
        ac307A = AC307A(Some(-2)),
        ac308A = AC308A(Some(-3)),
        ac309A = AC309A(None)
      )
      val loans = LoansToDirectors(loans = List(loan), ac7501 = AC7501(None))

      loans.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("LoansToDirectors"), "error.LoansToDirectors.cannot.exist"))
    }

    "errors against correct loan and contextualised #1" in {
      setupDefaults(mockBoxRetriever)

      val loan = LoanToDirector(
        uuid = "uuid",
        ac304A = AC304A(None),
        ac305A = AC305A(Some("description ^")),
        ac306A = AC306A(Some(-100000000)),
        ac307A = AC307A(Some(-100000000)),
        ac308A = AC308A(Some(-100000000)),
        ac309A = AC309A(None)
      )
      val loans = LoansToDirectors(loans = List(loan), ac7501 = AC7501(Some("^^")))

      loans.validate(mockBoxRetriever) shouldBe Set(
        CtValidation(Some("LoansToDirectors"), "error.compoundList.loans.0.AC304A.required", None),
        CtValidation(Some("LoansToDirectors"), "error.compoundList.loans.0.AC305A.regexFailure",Some(List("^"))),
        CtValidation(Some("LoansToDirectors"), "error.compoundList.loans.0.AC306A.below.min", Some(List("-99999999", "99999999"))),
        CtValidation(Some("LoansToDirectors"), "error.compoundList.loans.0.AC307A.below.min", Some(List("-99999999", "99999999"))),
        CtValidation(Some("LoansToDirectors"), "error.compoundList.loans.0.AC308A.below.min", Some(List("-99999999", "99999999"))),
        CtValidation(Some("LoansToDirectors"), "error.AC7501.regexFailure" ,Some(List("^")))
      )
    }

    "errors against correct loan and contextualised #2" in {
      setupDefaults(mockBoxRetriever)

      val transaction2 = LoanToDirector(
        uuid = "uuid",
        ac304A = AC304A(None),
        ac305A = AC305A(None),
        ac306A = AC306A(None),
        ac307A = AC307A(None),
        ac308A = AC308A(None),
        ac309A = AC309A(None)
      )
      val loans = LoansToDirectors(loans = List(validLoan , transaction2), ac7501 = AC7501(None))

      loans.validate(mockBoxRetriever) shouldBe Set(
        CtValidation(Some("LoansToDirectors"), "error.compoundList.loans.1.AC304A.required", None),
        CtValidation(Some("LoansToDirectors"), "error.compoundList.loans.1.AC305A.required", None),
        CtValidation(None, "error.LoansToDirectors.compoundList.loans.1.one.field.required", None)
      )
     }

    "errors against correct loan and contextualised #3 - only global error" in {
      setupDefaults(mockBoxRetriever)

      val transaction2 = LoanToDirector(
        uuid = "uuid",
        ac304A = AC304A(Some("director")),
        ac305A = AC305A(Some("description")),
        ac306A = AC306A(None),
        ac307A = AC307A(None),
        ac308A = AC308A(None),
        ac309A = AC309A(None)
      )
      val loans = LoansToDirectors(loans = List(validLoan , transaction2), ac7501 = AC7501(None))

      loans.validate(mockBoxRetriever) shouldBe Set(
        CtValidation(None, "error.LoansToDirectors.compoundList.loans.1.one.field.required", None)
      )
    }

    "range error when no loans" in {
      setupDefaults(mockBoxRetriever)

      val loans = LoansToDirectors(loans = List.empty, ac7501 = AC7501(None))

      loans.validate(mockBoxRetriever) shouldBe Set(CtValidation(None,"error.LoansToDirectors.atLeast1",None))
    }

    "range error when too many loans" in {
      setupDefaults(mockBoxRetriever)

      val loans = LoansToDirectors(loans = List.tabulate(20)(index => validLoan), ac7501 = AC7501(None))
      loans.validate(mockBoxRetriever) shouldBe empty

      val tooManyTransactions = LoansToDirectors(loans = List.tabulate(21)(index => validLoan), ac7501 = AC7501(None))
      tooManyTransactions.validate(mockBoxRetriever) shouldBe Set(CtValidation(None,"error.LoansToDirectors.atMost20",None))
    }
  }

  "evalAC309A should return loans with AC309A calculated according to its dependent values" in {
    val loan = LoanToDirector(
      uuid = "uuid",
      ac304A = AC304A(Some("director 1")),
      ac305A = AC305A(Some("description")),
      ac306A = AC306A(Some(11)),
      ac307A = AC307A(Some(12)),
      ac308A = AC308A(Some(15)),
      ac309A = AC309A(None)
    )
    val loans = LoansToDirectors(loans = List(loan), ac7501 = AC7501(None))

    loans.calculateAC309A() shouldBe LoansToDirectors(List(
      LoanToDirector("uuid",
        AC304A(Some("director 1")),
        AC305A(Some("description")),
        AC306A(Some(11)),
        AC307A(Some(12)),
        AC308A(Some(15)),
        AC309A(Some(8)))
    ), AC7501(None))
  }
}

object LoansToDirectorsMockSetup extends MockitoSugar {

  def setupDefaults(mockBoxRetriever: AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever) = {
    // previous POA responses
    when(mockBoxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2014, 4, 6))))
    when(mockBoxRetriever.ac206()).thenReturn(AC206(Some(new LocalDate(2015, 4, 5))))

    when(mockBoxRetriever.ac7500()).thenReturn(AC7500(Some(true)))
  }
}
