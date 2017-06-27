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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.{AbridgedFiling, CompaniesHouseFiling, FilingCompanyType, HMRCFiling}
import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.domain.CompanyTypes

class AC13Spec extends AccountsMoneyValidationFixture[FullAccountsBoxRetriever]
               with AccountsPreviousPeriodValidationFixture[FullAccountsBoxRetriever]
               with MockFullAccountsRetriever {

  testAccountsMoneyValidationWithMin("AC13", 0, AC13.apply, true, false)

  testAccountsPreviousPoAValidation("AC13", AC13.apply)

  "AC13" should {
    val testValues = Set(
      (Some(-1), "return an error if negative", true),
      (Some(0), "return no error if zero", false),
      (Some(1), "return no error if positive", false)
    )

    testValues.foreach { case (ac13Value: Option[Int], message: String, shouldError: Boolean) =>
      s"$message" in {
        val boxRetriever = mock[FullAccountsBoxRetriever]
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate("2015-01-01"))))
        val validationResult = AC13(ac13Value).validate(boxRetriever)
        if (shouldError)
          validationResult shouldBe Set(CtValidation(Some("AC13"), "error.AC13.mustBeZeroOrPositive"))
        else
          validationResult shouldBe empty
      }
    }
  }
}
