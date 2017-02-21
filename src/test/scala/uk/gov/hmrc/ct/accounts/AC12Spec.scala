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

package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.TableFor6
import org.scalatest.prop.Tables.Table
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.domain.CompanyTypes
import uk.gov.hmrc.ct.domain.CompanyTypes.{CompanyType, UkTradingCompany}
import uk.gov.hmrc.ct.{AbridgedFiling, CompaniesHouseFiling, FilingCompanyType, HMRCFiling}

class AC12Spec extends WordSpec with Matchers with MockitoSugar {

  "AC12 validation" should {

    "validate mandatory check" when {

      val testTable = Table(
        ("poaStartDate",   "poaEndDate",  "abridgedFiling",  "ac12Value",  "required", "message"),
        ("2015-01-01",     "2015-12-31",     false,             None,      false,      "1 year PoA pre FRS empty NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              None,      false,      "1 year PoA pre FRS empty abridged"),
        ("2015-01-01",     "2015-12-01",     false,             None,      false,      "short PoA pre FRS empty NOT abridged"),
        ("2015-01-01",     "2015-12-01",     true,              None,      false,      "short PoA pre FRS empty abridged"),
        ("2015-01-01",     "2016-12-01",     false,             None,      false,      "long PoA pre FRS empty NOT abridged"),
        ("2015-01-01",     "2016-12-01",     true,              None,      false,      "long PoA pre FRS empty abridged"),
        
        ("2016-01-01",     "2016-12-31",     false,             None,      false,      "1 year PoA post FRS empty NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              None,      false,      "1 year PoA post FRS empty abridged"),
        ("2016-01-01",     "2016-12-01",     false,             None,      false,      "short PoA post FRS empty NOT abridged"),
        ("2016-01-01",     "2016-12-01",     true,              None,      false,      "short PoA post FRS empty abridged"),
        ("2016-01-01",     "2017-12-01",     false,             None,      false,      "long PoA post FRS empty NOT abridged"),
        ("2016-01-01",     "2017-12-01",     true,              None,      true,      "long PoA post FRS empty abridged"),
        ("2016-01-01",     "2017-12-01",     true,              Some(1),   false,      "long PoA post FRS populated abridged")
      )

      (CompanyTypes.AllCompanyTypes -- CompanyTypes.AllCharityTypes).foreach { companyType =>

        s"check validation when empty for $companyType" in {

          forAll(testTable) { (startDateString: String, endDateString: String, abridgedFiling: Boolean, ac12Value: Option[Int], required: Boolean, message: String) =>
            val boxRetriever = mock[TestBoxRetriever]

            when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(startDateString)))
            when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(endDateString)))
            when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
            when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
            when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(abridgedFiling))
            when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))

            val validationResult = AC12(ac12Value).validate(boxRetriever)
            if (required)
              validationResult shouldBe Set(CtValidation(Some("AC12"), "error.AC12.required"))
            else
              validationResult shouldBe empty
          }
        }
      }
    }

    "HMRC only non-charity filing" when {
      assertHmrcInvolvedCompanyValidation(isHmrcFiling = true, isCoHoFiling = false)
    }

    "HMRC only charity/CASC filing" when {

      val isHmrcFiling = true
      val isCoHoFiling = false

      val testTable = Table(
        ("poaStartDate",   "poaEndDate",     "abridgedFiling",  "ac12Value",         "errorKey",                                "message"),
        ("2015-01-01",     "2015-12-31",     false,             0,                   "",                                        "1 year PoA pre FRS with zero value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              0,                   "",                                        "1 year PoA pre FRS with zero value abridged"),
        ("2015-01-01",     "2015-12-31",     false,             1,                   "",                                        "1 year PoA pre FRS with valid value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              1,                   "",                                        "1 year PoA pre FRS with valid value abridged"),
        ("2015-01-01",     "2015-12-31",     false,             6500000,              "",                                        "1 year PoA pre FRS with max value on threshold NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              6500000,              "",                                        "1 year PoA pre FRS with max value on threshold abridged"),
        ("2015-01-01",     "2015-12-31",     false,             -6500000,             "",                                        "1 year PoA pre FRS with min value on threshold NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              -6500000,             "",                                        "1 year PoA pre FRS with min value on threshold abridged"),
        ("2015-01-01",     "2015-12-31",     false,             6500001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA pre FRS with above max value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              6500001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA pre FRS with above max value abridged"),
        ("2015-01-01",     "2015-12-31",     false,             -6500001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA pre FRS with below min value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              -6500001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA pre FRS with below min value abridged"),
        
        ("2017-01-01",     "2017-12-31",     false,             0,                   "",                                        "1 year PoA post FRS with zero value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              0,                   "",                                        "1 year PoA post FRS with zero value abridged"),
        ("2017-01-01",     "2017-12-31",     false,             1,                   "",                                        "1 year PoA post FRS with valid value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              1,                   "",                                        "1 year PoA post FRS with valid value abridged"),
        ("2017-01-01",     "2017-12-31",     false,             6500000,              "",                                        "1 year PoA post FRS with max value on threshold NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              6500000,              "",                                        "1 year PoA post FRS with max value on threshold abridged"),
        ("2017-01-01",     "2017-12-31",     false,             -6500000,             "",                                        "1 year PoA post FRS with min value on threshold NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              -6500000,             "",                                        "1 year PoA post FRS with min value on threshold abridged"),
        ("2017-01-01",     "2017-12-31",     false,             6500001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA post FRS with above max value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              6500001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA post FRS with above max value abridged"),
        ("2017-01-01",     "2017-12-31",     false,             -6500001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA post FRS with below min value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              -6500001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA post FRS with below min value abridged"),

        ("2016-01-01",     "2016-12-31",     false,             0,                   "",                                        "1 year PoA FRS with zero value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              0,                   "",                                        "1 year PoA FRS with zero value abridged"),
        ("2016-01-01",     "2016-12-31",     false,             1,                   "",                                        "1 year PoA FRS with valid value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              1,                   "",                                        "1 year PoA FRS with valid value abridged"),
        ("2016-01-01",     "2016-12-31",     false,             6500000,              "",                                        "1 year PoA FRS with max value on threshold NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              6500000,              "",                                        "1 year PoA FRS with max value on threshold abridged"),
        ("2016-01-01",     "2016-12-31",     false,             -6500000,             "",                                        "1 year PoA FRS with min value on threshold NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              -6500000,             "",                                        "1 year PoA FRS with min value on threshold abridged"),
        ("2016-01-01",     "2016-12-31",     false,             6500001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA FRS with above max value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              6500001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA FRS with above max value abridged"),
        ("2016-01-01",     "2016-12-31",     false,             -6500001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA FRS with below min value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              -6500001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA FRS with below min value abridged"),


        ("2015-07-01",     "2016-12-31",     false,             0,                   "",                                        "18 month with leap year pre PoA FRS with zero value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              0,                   "",                                        "18 month with leap year pre PoA FRS with zero value abridged"),
        ("2015-07-01",     "2016-12-31",     false,             1,                   "",                                        "18 month with leap year pre PoA FRS with valid value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              1,                   "",                                        "18 month with leap year pre PoA FRS with valid value abridged"),
        ("2015-07-01",     "2016-12-31",     false,             9767759,              "",                                        "18 month with leap year pre PoA FRS with max value on threshold NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              9767759,              "",                                        "18 month with leap year pre PoA FRS with max value on threshold abridged"),
        ("2015-07-01",     "2016-12-31",     false,             -9767759,             "",                                        "18 month with leap year pre PoA FRS with min value on threshold NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              -9767759,             "",                                        "18 month with leap year pre PoA FRS with min value on threshold abridged"),
        ("2015-07-01",     "2016-12-31",     false,             9767760,              "error.AC12.hmrc.turnover.above.max",      "18 month with leap year pre PoA FRS with above max value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              9767760,              "error.AC12.hmrc.turnover.above.max",      "18 month with leap year pre PoA FRS with above max value abridged"),
        ("2015-07-01",     "2016-12-31",     false,             -9767760,             "error.AC12.hmrc.turnover.below.min",      "18 month with leap year pre PoA FRS with below min value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              -9767760,             "error.AC12.hmrc.turnover.below.min",      "18 month with leap year pre PoA FRS with below min value abridged"),

        ("2019-07-01",     "2020-12-31",     false,             0,                   "",                                        "18 month with leap year PoA post FRS with zero value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              0,                   "",                                        "18 month with leap year PoA post FRS with zero value abridged"),
        ("2019-07-01",     "2020-12-31",     false,             1,                   "",                                        "18 month with leap year PoA post FRS with valid value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              1,                   "",                                        "18 month with leap year PoA post FRS with valid value abridged"),
        ("2019-07-01",     "2020-12-31",     false,             9767759,              "",                                        "18 month with leap year PoA post FRS with max value on threshold NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              9767759,              "",                                        "18 month with leap year PoA post FRS with max value on threshold abridged"),
        ("2019-07-01",     "2020-12-31",     false,             -9767759,             "",                                        "18 month with leap year PoA post FRS with min value on threshold NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              -9767759,             "",                                        "18 month with leap year PoA post FRS with min value on threshold abridged"),
        ("2019-07-01",     "2020-12-31",     false,             9767760,              "error.AC12.hmrc.turnover.above.max",      "18 month with leap year PoA post FRS with above max value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              9767760,              "error.AC12.hmrc.turnover.above.max",      "18 month with leap year PoA post FRS with above max value abridged"),
        ("2019-07-01",     "2020-12-31",     false,             -9767760,             "error.AC12.hmrc.turnover.below.min",      "18 month with leap year PoA post FRS with below min value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              -9767760,             "error.AC12.hmrc.turnover.below.min",      "18 month with leap year PoA post FRS with below min value abridged"),

        ("2014-07-01",     "2015-12-31",     false,             0,                   "",                                        "18 month with non leap year pre PoA FRS with zero value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              0,                   "",                                        "18 month with non leap year pre PoA FRS with zero value abridged"),
        ("2014-07-01",     "2015-12-31",     false,             1,                   "",                                        "18 month with non leap year pre PoA FRS with valid value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              1,                   "",                                        "18 month with non leap year pre PoA FRS with valid value abridged"),
        ("2014-07-01",     "2015-12-31",     false,             9776712,              "",                                        "18 month with non leap year pre PoA FRS with max value on threshold NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              9776712,              "",                                        "18 month with non leap year pre PoA FRS with max value on threshold abridged"),
        ("2014-07-01",     "2015-12-31",     false,             -9776712,             "",                                        "18 month with non leap year pre PoA FRS with min value on threshold NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              -9776712,             "",                                        "18 month with non leap year pre PoA FRS with min value on threshold abridged"),
        ("2014-07-01",     "2015-12-31",     false,             9776713,              "error.AC12.hmrc.turnover.above.max",      "18 month with non leap year pre PoA FRS with above max value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              9776713,              "error.AC12.hmrc.turnover.above.max",      "18 month with non leap year pre PoA FRS with above max value abridged"),
        ("2014-07-01",     "2015-12-31",     false,             -9776713,             "error.AC12.hmrc.turnover.below.min",      "18 month with non leap year pre PoA FRS with below min value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              -9776713,             "error.AC12.hmrc.turnover.below.min",      "18 month with non leap year pre PoA FRS with below min value abridged"),

        ("2017-07-01",     "2018-12-31",     false,             0,                   "",                                        "18 month with non leap year PoA post FRS with zero value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              0,                   "",                                        "18 month with non leap year PoA post FRS with zero value abridged"),
        ("2017-07-01",     "2018-12-31",     false,             1,                   "",                                        "18 month with non leap year PoA post FRS with valid value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              1,                   "",                                        "18 month with non leap year PoA post FRS with valid value abridged"),
        ("2017-07-01",     "2018-12-31",     false,             9776712,              "",                                        "18 month with non leap year PoA post FRS with max value on threshold NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              9776712,              "",                                        "18 month with non leap year PoA post FRS with max value on threshold abridged"),
        ("2017-07-01",     "2018-12-31",     false,             -9776712,             "",                                        "18 month with non leap year PoA post FRS with min value on threshold NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              -9776712,             "",                                        "18 month with non leap year PoA post FRS with min value on threshold abridged"),
        ("2017-07-01",     "2018-12-31",     false,             9776713,              "error.AC12.hmrc.turnover.above.max",      "18 month with non leap year PoA post FRS with above max value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              9776713,              "error.AC12.hmrc.turnover.above.max",      "18 month with non leap year PoA post FRS with above max value abridged"),
        ("2017-07-01",     "2018-12-31",     false,             -9776713,             "error.AC12.hmrc.turnover.below.min",      "18 month with non leap year PoA post FRS with below min value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              -9776713,             "error.AC12.hmrc.turnover.below.min",      "18 month with non leap year PoA post FRS with below min value abridged"),


        ("2015-12-01",     "2016-05-31",     false,             0,                   "",                                        "6 month with leap year pre PoA FRS with zero value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              0,                   "",                                        "6 month with leap year pre PoA FRS with zero value abridged"),
        ("2015-12-01",     "2016-05-31",     false,             1,                   "",                                        "6 month with leap year pre PoA FRS with valid value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              1,                   "",                                        "6 month with leap year pre PoA FRS with valid value abridged"),
        ("2015-12-01",     "2016-05-31",     false,             3250000,              "",                                        "6 month with leap year pre PoA FRS with max value on threshold NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              3250000,              "",                                        "6 month with leap year pre PoA FRS with max value on threshold abridged"),
        ("2015-12-01",     "2016-05-31",     false,             -3250000,             "",                                        "6 month with leap year pre PoA FRS with min value on threshold NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              -3250000,             "",                                        "6 month with leap year pre PoA FRS with min value on threshold abridged"),
        ("2015-12-01",     "2016-05-31",     false,             3250001,              "error.AC12.hmrc.turnover.above.max",      "6 month with leap year pre PoA FRS with above max value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              3250001,              "error.AC12.hmrc.turnover.above.max",      "6 month with leap year pre PoA FRS with above max value abridged"),
        ("2015-12-01",     "2016-05-31",     false,             -3250001,             "error.AC12.hmrc.turnover.below.min",      "6 month with leap year pre PoA FRS with below min value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              -3250001,             "error.AC12.hmrc.turnover.below.min",      "6 month with leap year pre PoA FRS with below min value abridged"),

        ("2020-01-01",     "2020-06-30",     false,             0,                   "",                                        "6 month with leap year PoA post FRS with zero value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              0,                   "",                                        "6 month with leap year PoA post FRS with zero value abridged"),
        ("2020-01-01",     "2020-06-30",     false,             1,                   "",                                        "6 month with leap year PoA post FRS with valid value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              1,                   "",                                        "6 month with leap year PoA post FRS with valid value abridged"),
        ("2020-01-01",     "2020-06-30",     false,             3232240,              "",                                        "6 month with leap year PoA post FRS with max value on threshold NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              3232240,              "",                                        "6 month with leap year PoA post FRS with max value on threshold abridged"),
        ("2020-01-01",     "2020-06-30",     false,             -3232240,             "",                                        "6 month with leap year PoA post FRS with min value on threshold NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              -3232240,             "",                                        "6 month with leap year PoA post FRS with min value on threshold abridged"),
        ("2020-01-01",     "2020-06-30",     false,             3232241,              "error.AC12.hmrc.turnover.above.max",      "6 month with leap year PoA post FRS with above max value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              3232241,              "error.AC12.hmrc.turnover.above.max",      "6 month with leap year PoA post FRS with above max value abridged"),
        ("2020-01-01",     "2020-06-30",     false,             -3232241,             "error.AC12.hmrc.turnover.below.min",      "6 month with leap year PoA post FRS with below min value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              -3232241,             "error.AC12.hmrc.turnover.below.min",      "6 month with leap year PoA post FRS with below min value abridged"),

        ("2015-07-01",     "2015-12-31",     false,             0,                   "",                                        "6 month with non leap year pre PoA FRS with zero value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              0,                   "",                                        "6 month with non leap year pre PoA FRS with zero value abridged"),
        ("2015-07-01",     "2015-12-31",     false,             1,                   "",                                        "6 month with non leap year pre PoA FRS with valid value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              1,                   "",                                        "6 month with non leap year pre PoA FRS with valid value abridged"),
        ("2015-07-01",     "2015-12-31",     false,             3276712,              "",                                        "6 month with non leap year pre PoA FRS with max value on threshold NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              3276712,              "",                                        "6 month with non leap year pre PoA FRS with max value on threshold abridged"),
        ("2015-07-01",     "2015-12-31",     false,             -3276712,             "",                                        "6 month with non leap year pre PoA FRS with min value on threshold NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              -3276712,             "",                                        "6 month with non leap year pre PoA FRS with min value on threshold abridged"),
        ("2015-07-01",     "2015-12-31",     false,             3276713,              "error.AC12.hmrc.turnover.above.max",      "6 month with non leap year pre PoA FRS with above max value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              3276713,              "error.AC12.hmrc.turnover.above.max",      "6 month with non leap year pre PoA FRS with above max value abridged"),
        ("2015-07-01",     "2015-12-31",     false,             -3276713,             "error.AC12.hmrc.turnover.below.min",      "6 month with non leap year pre PoA FRS with below min value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              -3276713,             "error.AC12.hmrc.turnover.below.min",      "6 month with non leap year pre PoA FRS with below min value abridged"),

        ("2017-07-01",     "2017-12-31",     false,             0,                   "",                                        "6 month with non leap year PoA post FRS with zero value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              0,                   "",                                        "6 month with non leap year PoA post FRS with zero value abridged"),
        ("2017-07-01",     "2017-12-31",     false,             1,                   "",                                        "6 month with non leap year PoA post FRS with valid value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              1,                   "",                                        "6 month with non leap year PoA post FRS with valid value abridged"),
        ("2017-07-01",     "2017-12-31",     false,             3276712,              "",                                        "6 month with non leap year PoA post FRS with max value on threshold NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              3276712,              "",                                        "6 month with non leap year PoA post FRS with max value on threshold abridged"),
        ("2017-07-01",     "2017-12-31",     false,             -3276712,             "",                                        "6 month with non leap year PoA post FRS with min value on threshold NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              -3276712,             "",                                        "6 month with non leap year PoA post FRS with min value on threshold abridged"),
        ("2017-07-01",     "2017-12-31",     false,             3276713,              "error.AC12.hmrc.turnover.above.max",      "6 month with non leap year PoA post FRS with above max value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              3276713,              "error.AC12.hmrc.turnover.above.max",      "6 month with non leap year PoA post FRS with above max value abridged"),
        ("2017-07-01",     "2017-12-31",     false,             -3276713,             "error.AC12.hmrc.turnover.below.min",      "6 month with non leap year PoA post FRS with below min value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              -3276713,             "error.AC12.hmrc.turnover.below.min",      "6 month with non leap year PoA post FRS with below min value abridged"),

        ("2016-03-01",     "2017-03-01",     false,             0,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with zero value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              0,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with zero value abridged"),
        ("2016-03-01",     "2017-03-01",     false,             1,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with valid value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              1,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with valid value abridged"),
        ("2016-03-01",     "2017-03-01",     false,             6517808,              "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with max value on threshold NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              6517808,              "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with max value on threshold abridged"),
        ("2016-03-01",     "2017-03-01",     false,             -6517808,             "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with min value on threshold NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              -6517808,             "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with min value on threshold abridged"),
        ("2016-03-01",     "2017-03-01",     false,             6517809,              "error.AC12.hmrc.turnover.above.max",      "1 year and 1 day PoA starting 1 March in leap year post FRS with above max value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              6517809,              "error.AC12.hmrc.turnover.above.max",      "1 year and 1 day PoA starting 1 March in leap year post FRS with above max value abridged"),
        ("2016-03-01",     "2017-03-01",     false,             -6517809,             "error.AC12.hmrc.turnover.below.min",      "1 year and 1 day PoA starting 1 March in leap year post FRS with below min value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              -6517809,             "error.AC12.hmrc.turnover.below.min",      "1 year and 1 day PoA starting 1 March in leap year post FRS with below min value abridged"),

        ("2019-02-28",     "2020-02-28",     false,             0,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with zero value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              0,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with zero value abridged"),
        ("2019-02-28",     "2020-02-28",     false,             1,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with valid value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              1,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with valid value abridged"),
        ("2019-02-28",     "2020-02-28",     false,             6517808,              "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              6517808,              "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with max value on threshold abridged"),
        ("2019-02-28",     "2020-02-28",     false,             -6517808,             "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              -6517808,             "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with min value on threshold abridged"),
        ("2019-02-28",     "2020-02-28",     false,             6517809,              "error.AC12.hmrc.turnover.above.max",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with above max value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              6517809,              "error.AC12.hmrc.turnover.above.max",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with above max value abridged"),
        ("2019-02-28",     "2020-02-28",     false,             -6517809,             "error.AC12.hmrc.turnover.below.min",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with below min value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              -6517809,             "error.AC12.hmrc.turnover.below.min",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with below min value abridged"),

        ("2016-02-29",     "2016-12-31",     false,             0,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with zero value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              0,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with zero value abridged"),
        ("2016-02-29",     "2016-12-31",     false,             1,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with valid value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              1,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with valid value abridged"),
        ("2016-02-29",     "2016-12-31",     false,             5452185,              "",                                        "short PoA starting 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              5452185,              "",                                        "short PoA starting 29 Feb in leap year post FRS with max value on threshold abridged"),
        ("2016-02-29",     "2016-12-31",     false,             -5452185,             "",                                        "short PoA starting 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              -5452185,             "",                                        "short PoA starting 29 Feb in leap year post FRS with min value on threshold abridged"),
        ("2016-02-29",     "2016-12-31",     false,             5452186,              "error.AC12.hmrc.turnover.above.max",      "short PoA starting 29 Feb in leap year post FRS with above max value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              5452186,              "error.AC12.hmrc.turnover.above.max",      "short PoA starting 29 Feb in leap year post FRS with above max value abridged"),
        ("2016-02-29",     "2016-12-31",     false,             -5452186,             "error.AC12.hmrc.turnover.below.min",      "short PoA starting 29 Feb in leap year post FRS with below min value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              -5452186,             "error.AC12.hmrc.turnover.below.min",      "short PoA starting 29 Feb in leap year post FRS with below min value abridged"),

        ("2019-02-01",     "2020-02-29",     false,             0,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with zero value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              0,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with zero value abridged"),
        ("2019-02-01",     "2020-02-29",     false,             1,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with valid value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              1,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with valid value abridged"),
        ("2019-02-01",     "2020-02-29",     false,             6997267,              "",                                        "long PoA ending 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              6997267,              "",                                        "long PoA ending 29 Feb in leap year post FRS with max value on threshold abridged"),
        ("2019-02-01",     "2020-02-29",     false,             -6997267,             "",                                        "long PoA ending 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              -6997267,             "",                                        "long PoA ending 29 Feb in leap year post FRS with min value on threshold abridged"),
        ("2019-02-01",     "2020-02-29",     false,             6997268,              "error.AC12.hmrc.turnover.above.max",      "long PoA ending 29 Feb in leap year post FRS with above max value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              6997268,              "error.AC12.hmrc.turnover.above.max",      "long PoA ending 29 Feb in leap year post FRS with above max value abridged"),
        ("2019-02-01",     "2020-02-29",     false,             -6997268,             "error.AC12.hmrc.turnover.below.min",      "long PoA ending 29 Feb in leap year post FRS with below min value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              -6997268,             "error.AC12.hmrc.turnover.below.min",      "long PoA ending 29 Feb in leap year post FRS with below min value abridged"),

        ("2019-06-01",     "2020-05-31",     false,             0,                   "",                                        "1 year PoA ending in leap year post FRS with zero value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              0,                   "",                                        "1 year PoA ending in leap year post FRS with zero value abridged"),
        ("2019-06-01",     "2020-05-31",     false,             1,                   "",                                        "1 year PoA ending in leap year post FRS with valid value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              1,                   "",                                        "1 year PoA ending in leap year post FRS with valid value abridged"),
        ("2019-06-01",     "2020-05-31",     false,             6500000,              "",                                        "1 year PoA ending in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              6500000,              "",                                        "1 year PoA ending in leap year post FRS with max value on threshold abridged"),
        ("2019-06-01",     "2020-05-31",     false,             -6500000,             "",                                        "1 year PoA ending in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              -6500000,             "",                                        "1 year PoA ending in leap year post FRS with min value on threshold abridged"),
        ("2019-06-01",     "2020-05-31",     false,             6500001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA ending in leap year post FRS with above max value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              6500001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA ending in leap year post FRS with above max value abridged"),
        ("2019-06-01",     "2020-05-31",     false,             -6500001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA ending in leap year post FRS with below min value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              -6500001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA ending in leap year post FRS with below min value abridged"),

        ("2019-03-01",     "2020-02-29",     false,             0,                   "",                                        "1 year PoA ending 29 Feb in leap year post FRS with zero value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              0,                   "",                                        "1 year PoA ending 29 Feb in leap year post FRS with zero value abridged"),
        ("2019-03-01",     "2020-02-29",     false,             1,                   "",                                        "1 year PoA ending 29 Feb in leap year post FRS with valid value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              1,                   "",                                        "1 year PoA ending 29 Feb in leap year post FRS with valid value abridged"),
        ("2019-03-01",     "2020-02-29",     false,             6500000,              "",                                        "1 year PoA ending 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              6500000,              "",                                        "1 year PoA ending 29 Feb in leap year post FRS with max value on threshold abridged"),
        ("2019-03-01",     "2020-02-29",     false,             -6500000,             "",                                        "1 year PoA ending 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              -6500000,             "",                                        "1 year PoA ending 29 Feb in leap year post FRS with min value on threshold abridged"),
        ("2019-03-01",     "2020-02-29",     false,             6500001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA ending 29 Feb in leap year post FRS with above max value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              6500001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA ending 29 Feb in leap year post FRS with above max value abridged"),
        ("2019-03-01",     "2020-02-29",     false,             -6500001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA ending 29 Feb in leap year post FRS with below min value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              -6500001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA ending 29 Feb in leap year post FRS with below min value abridged")
      )

      executeTests(isHmrcFiling = isHmrcFiling, isCoHoFiling = isCoHoFiling)(CompanyTypes.AllCharityTypes)(testTable)
    }

    "Joint filing" when {
      assertHmrcInvolvedCompanyValidation(isHmrcFiling = true, isCoHoFiling = true)
    }

    "CoHo only filing" when {
      val isHmrcFiling = false
      val isCoHoFiling = true

      val testTable = Table(
        ("poaStartDate",   "poaEndDate",     "abridgedFiling",  "ac12Value",         "errorKey",                                "message"),
        ("2015-01-01",     "2015-12-31",     false,             0,                   "",                                        "1 year PoA pre FRS with zero value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              0,                   "",                                        "1 year PoA pre FRS with zero value abridged"),
        ("2015-01-01",     "2015-12-31",     false,             1,                   "",                                        "1 year PoA pre FRS with valid value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              1,                   "",                                        "1 year PoA pre FRS with valid value abridged"),
        ("2015-01-01",     "2015-12-31",     false,             6500000,              "",                                        "1 year PoA pre FRS with max value on threshold NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              6500000,              "",                                        "1 year PoA pre FRS with max value on threshold abridged"),
        ("2015-01-01",     "2015-12-31",     false,             -6500000,             "",                                        "1 year PoA pre FRS with min value on threshold NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              -6500000,             "",                                        "1 year PoA pre FRS with min value on threshold abridged"),
        ("2015-01-01",     "2015-12-31",     false,             6500001,              "error.AC12.coho.turnover.above.max",      "1 year PoA pre FRS with above max value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              6500001,              "error.AC12.coho.turnover.above.max",      "1 year PoA pre FRS with above max value abridged"),
        ("2015-01-01",     "2015-12-31",     false,             -6500001,             "error.AC12.coho.turnover.below.min",      "1 year PoA pre FRS with below min value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              -6500001,             "error.AC12.coho.turnover.below.min",      "1 year PoA pre FRS with below min value abridged"),

        ("2017-01-01",     "2017-12-31",     false,             0,                   "",                                        "1 year PoA post FRS with zero value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              0,                   "",                                        "1 year PoA post FRS with zero value abridged"),
        ("2017-01-01",     "2017-12-31",     false,             1,                   "",                                        "1 year PoA post FRS with valid value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              1,                   "",                                        "1 year PoA post FRS with valid value abridged"),
        ("2017-01-01",     "2017-12-31",     false,             10200000,              "",                                        "1 year PoA post FRS with max value on threshold NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              10200000,              "",                                        "1 year PoA post FRS with max value on threshold abridged"),
        ("2017-01-01",     "2017-12-31",     false,             -10200000,             "",                                        "1 year PoA post FRS with min value on threshold NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              -10200000,             "",                                        "1 year PoA post FRS with min value on threshold abridged"),
        ("2017-01-01",     "2017-12-31",     false,             10200001,              "error.AC12.coho.turnover.above.max",      "1 year PoA post FRS with above max value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              10200001,              "error.AC12.coho.turnover.above.max",      "1 year PoA post FRS with above max value abridged"),
        ("2017-01-01",     "2017-12-31",     false,             -10200001,             "error.AC12.coho.turnover.below.min",      "1 year PoA post FRS with below min value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              -10200001,             "error.AC12.coho.turnover.below.min",      "1 year PoA post FRS with below min value abridged"),

        ("2016-01-01",     "2016-12-31",     false,             0,                   "",                                        "1 year PoA FRS with zero value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              0,                   "",                                        "1 year PoA FRS with zero value abridged"),
        ("2016-01-01",     "2016-12-31",     false,             1,                   "",                                        "1 year PoA FRS with valid value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              1,                   "",                                        "1 year PoA FRS with valid value abridged"),
        ("2016-01-01",     "2016-12-31",     false,             10200000,              "",                                        "1 year PoA FRS with max value on threshold NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              10200000,              "",                                        "1 year PoA FRS with max value on threshold abridged"),
        ("2016-01-01",     "2016-12-31",     false,             -10200000,             "",                                        "1 year PoA FRS with min value on threshold NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              -10200000,             "",                                        "1 year PoA FRS with min value on threshold abridged"),
        ("2016-01-01",     "2016-12-31",     false,             10200001,              "error.AC12.coho.turnover.above.max",      "1 year PoA FRS with above max value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              10200001,              "error.AC12.coho.turnover.above.max",      "1 year PoA FRS with above max value abridged"),
        ("2016-01-01",     "2016-12-31",     false,             -10200001,             "error.AC12.coho.turnover.below.min",      "1 year PoA FRS with below min value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              -10200001,             "error.AC12.coho.turnover.below.min",      "1 year PoA FRS with below min value abridged"),


        ("2015-07-01",     "2016-12-31",     false,             0,                   "",                                        "18 month with leap year pre PoA FRS with zero value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              0,                   "",                                        "18 month with leap year pre PoA FRS with zero value abridged"),
        ("2015-07-01",     "2016-12-31",     false,             1,                   "",                                        "18 month with leap year pre PoA FRS with valid value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              1,                   "",                                        "18 month with leap year pre PoA FRS with valid value abridged"),
        ("2015-07-01",     "2016-12-31",     false,             9767759,              "",                                        "18 month with leap year pre PoA FRS with max value on threshold NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              9767759,              "",                                        "18 month with leap year pre PoA FRS with max value on threshold abridged"),
        ("2015-07-01",     "2016-12-31",     false,             -9767759,             "",                                        "18 month with leap year pre PoA FRS with min value on threshold NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              -9767759,             "",                                        "18 month with leap year pre PoA FRS with min value on threshold abridged"),
        ("2015-07-01",     "2016-12-31",     false,             9767760,              "error.AC12.coho.turnover.above.max",      "18 month with leap year pre PoA FRS with above max value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              9767760,              "error.AC12.coho.turnover.above.max",      "18 month with leap year pre PoA FRS with above max value abridged"),
        ("2015-07-01",     "2016-12-31",     false,             -9767760,             "error.AC12.coho.turnover.below.min",      "18 month with leap year pre PoA FRS with below min value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              -9767760,             "error.AC12.coho.turnover.below.min",      "18 month with leap year pre PoA FRS with below min value abridged"),

        ("2019-07-01",     "2020-12-31",     false,             0,                   "",                                        "18 month with leap year PoA post FRS with zero value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              0,                   "",                                        "18 month with leap year PoA post FRS with zero value abridged"),
        ("2019-07-01",     "2020-12-31",     false,             1,                   "",                                        "18 month with leap year PoA post FRS with valid value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              1,                   "",                                        "18 month with leap year PoA post FRS with valid value abridged"),
        ("2019-07-01",     "2020-12-31",     false,             15327868,              "",                                        "18 month with leap year PoA post FRS with max value on threshold NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              15327868,              "",                                        "18 month with leap year PoA post FRS with max value on threshold abridged"),
        ("2019-07-01",     "2020-12-31",     false,             -15327868,             "",                                        "18 month with leap year PoA post FRS with min value on threshold NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              -15327868,             "",                                        "18 month with leap year PoA post FRS with min value on threshold abridged"),
        ("2019-07-01",     "2020-12-31",     false,             15327869,              "error.AC12.coho.turnover.above.max",      "18 month with leap year PoA post FRS with above max value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              15327869,              "error.AC12.coho.turnover.above.max",      "18 month with leap year PoA post FRS with above max value abridged"),
        ("2019-07-01",     "2020-12-31",     false,             -15327869,             "error.AC12.coho.turnover.below.min",      "18 month with leap year PoA post FRS with below min value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              -15327869,             "error.AC12.coho.turnover.below.min",      "18 month with leap year PoA post FRS with below min value abridged"),

        ("2014-07-01",     "2015-12-31",     false,             0,                   "",                                        "18 month with non leap year pre PoA FRS with zero value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              0,                   "",                                        "18 month with non leap year pre PoA FRS with zero value abridged"),
        ("2014-07-01",     "2015-12-31",     false,             1,                   "",                                        "18 month with non leap year pre PoA FRS with valid value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              1,                   "",                                        "18 month with non leap year pre PoA FRS with valid value abridged"),
        ("2014-07-01",     "2015-12-31",     false,             9776712,              "",                                        "18 month with non leap year pre PoA FRS with max value on threshold NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              9776712,              "",                                        "18 month with non leap year pre PoA FRS with max value on threshold abridged"),
        ("2014-07-01",     "2015-12-31",     false,             -9776712,             "",                                        "18 month with non leap year pre PoA FRS with min value on threshold NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              -9776712,             "",                                        "18 month with non leap year pre PoA FRS with min value on threshold abridged"),
        ("2014-07-01",     "2015-12-31",     false,             9776713,              "error.AC12.coho.turnover.above.max",      "18 month with non leap year pre PoA FRS with above max value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              9776713,              "error.AC12.coho.turnover.above.max",      "18 month with non leap year pre PoA FRS with above max value abridged"),
        ("2014-07-01",     "2015-12-31",     false,             -9776713,             "error.AC12.coho.turnover.below.min",      "18 month with non leap year pre PoA FRS with below min value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              -9776713,             "error.AC12.coho.turnover.below.min",      "18 month with non leap year pre PoA FRS with below min value abridged"),

        ("2014-01-01",     "2015-06-30",     false,             0,                   "",                                        "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with zero value NOT abridged"),
        ("2014-01-01",     "2015-06-30",     true,              0,                   "",                                        "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with zero value abridged"),
        ("2014-01-01",     "2015-06-30",     false,             1,                   "",                                        "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with valid value NOT abridged"),
        ("2014-01-01",     "2015-06-30",     true,              1,                   "",                                        "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with valid value abridged"),
        ("2014-01-01",     "2015-06-30",     false,             9723287,             "",                                        "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with max value on threshold NOT abridged"),
        ("2014-01-01",     "2015-06-30",     true,              9723287,             "",                                        "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with max value on threshold abridged"),
        ("2014-01-01",     "2015-06-30",     false,             -9723287,            "",                                        "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with min value on threshold NOT abridged"),
        ("2014-01-01",     "2015-06-30",     true,              -9723287,            "",                                        "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with min value on threshold abridged"),
        ("2014-01-01",     "2015-06-30",     false,             9723288,             "error.AC12.coho.turnover.above.max",      "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with above max value NOT abridged"),
        ("2014-01-01",     "2015-06-30",     true,              9723288,             "error.AC12.coho.turnover.above.max",      "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with above max value abridged"),
        ("2014-01-01",     "2015-06-30",     false,             -9723288,            "error.AC12.coho.turnover.below.min",      "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with below min value NOT abridged"),
        ("2014-01-01",     "2015-06-30",     true,              -9723288,            "error.AC12.coho.turnover.below.min",      "18 month (starting Jan 1 2014) with non leap year pre PoA FRS with below min value abridged"),

        ("2017-07-01",     "2018-12-31",     false,             0,                   "",                                        "18 month with non leap year PoA post FRS with zero value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              0,                   "",                                        "18 month with non leap year PoA post FRS with zero value abridged"),
        ("2017-07-01",     "2018-12-31",     false,             1,                   "",                                        "18 month with non leap year PoA post FRS with valid value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              1,                   "",                                        "18 month with non leap year PoA post FRS with valid value abridged"),
        ("2017-07-01",     "2018-12-31",     false,             15341917,              "",                                        "18 month with non leap year PoA post FRS with max value on threshold NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              15341917,              "",                                        "18 month with non leap year PoA post FRS with max value on threshold abridged"),
        ("2017-07-01",     "2018-12-31",     false,             -15341917,             "",                                        "18 month with non leap year PoA post FRS with min value on threshold NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              -15341917,             "",                                        "18 month with non leap year PoA post FRS with min value on threshold abridged"),
        ("2017-07-01",     "2018-12-31",     false,             15341918,              "error.AC12.coho.turnover.above.max",      "18 month with non leap year PoA post FRS with above max value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              15341918,              "error.AC12.coho.turnover.above.max",      "18 month with non leap year PoA post FRS with above max value abridged"),
        ("2017-07-01",     "2018-12-31",     false,             -15341918,             "error.AC12.coho.turnover.below.min",      "18 month with non leap year PoA post FRS with below min value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              -15341918,             "error.AC12.coho.turnover.below.min",      "18 month with non leap year PoA post FRS with below min value abridged"),


        ("2015-12-01",     "2016-05-31",     false,             0,                   "",                                        "6 month with leap year pre PoA FRS with zero value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              0,                   "",                                        "6 month with leap year pre PoA FRS with zero value abridged"),
        ("2015-12-01",     "2016-05-31",     false,             1,                   "",                                        "6 month with leap year pre PoA FRS with valid value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              1,                   "",                                        "6 month with leap year pre PoA FRS with valid value abridged"),
        ("2015-12-01",     "2016-05-31",     false,             3250000,              "",                                        "6 month with leap year pre PoA FRS with max value on threshold NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              3250000,              "",                                        "6 month with leap year pre PoA FRS with max value on threshold abridged"),
        ("2015-12-01",     "2016-05-31",     false,             -3250000,             "",                                        "6 month with leap year pre PoA FRS with min value on threshold NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              -3250000,             "",                                        "6 month with leap year pre PoA FRS with min value on threshold abridged"),
        ("2015-12-01",     "2016-05-31",     false,             3250001,              "error.AC12.coho.turnover.above.max",      "6 month with leap year pre PoA FRS with above max value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              3250001,              "error.AC12.coho.turnover.above.max",      "6 month with leap year pre PoA FRS with above max value abridged"),
        ("2015-12-01",     "2016-05-31",     false,             -3250001,             "error.AC12.coho.turnover.below.min",      "6 month with leap year pre PoA FRS with below min value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              -3250001,             "error.AC12.coho.turnover.below.min",      "6 month with leap year pre PoA FRS with below min value abridged"),

        ("2020-01-01",     "2020-06-30",     false,             0,                   "",                                        "6 month with leap year PoA post FRS with zero value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              0,                   "",                                        "6 month with leap year PoA post FRS with zero value abridged"),
        ("2020-01-01",     "2020-06-30",     false,             1,                   "",                                        "6 month with leap year PoA post FRS with valid value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              1,                   "",                                        "6 month with leap year PoA post FRS with valid value abridged"),
        ("2020-01-01",     "2020-06-30",     false,             5072131,              "",                                        "6 month with leap year PoA post FRS with max value on threshold NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              5072131,              "",                                        "6 month with leap year PoA post FRS with max value on threshold abridged"),
        ("2020-01-01",     "2020-06-30",     false,             -5072131,             "",                                        "6 month with leap year PoA post FRS with min value on threshold NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              -5072131,             "",                                        "6 month with leap year PoA post FRS with min value on threshold abridged"),
        ("2020-01-01",     "2020-06-30",     false,             5072132,              "error.AC12.coho.turnover.above.max",      "6 month with leap year PoA post FRS with above max value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              5072132,              "error.AC12.coho.turnover.above.max",      "6 month with leap year PoA post FRS with above max value abridged"),
        ("2020-01-01",     "2020-06-30",     false,             -5072132,             "error.AC12.coho.turnover.below.min",      "6 month with leap year PoA post FRS with below min value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              -5072132,             "error.AC12.coho.turnover.below.min",      "6 month with leap year PoA post FRS with below min value abridged"),

        ("2015-07-01",     "2015-12-31",     false,             0,                   "",                                        "6 month with non leap year pre PoA FRS with zero value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              0,                   "",                                        "6 month with non leap year pre PoA FRS with zero value abridged"),
        ("2015-07-01",     "2015-12-31",     false,             1,                   "",                                        "6 month with non leap year pre PoA FRS with valid value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              1,                   "",                                        "6 month with non leap year pre PoA FRS with valid value abridged"),
        ("2015-07-01",     "2015-12-31",     false,             3276712,              "",                                        "6 month with non leap year pre PoA FRS with max value on threshold NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              3276712,              "",                                        "6 month with non leap year pre PoA FRS with max value on threshold abridged"),
        ("2015-07-01",     "2015-12-31",     false,             -3276712,             "",                                        "6 month with non leap year pre PoA FRS with min value on threshold NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              -3276712,             "",                                        "6 month with non leap year pre PoA FRS with min value on threshold abridged"),
        ("2015-07-01",     "2015-12-31",     false,             3276713,              "error.AC12.coho.turnover.above.max",      "6 month with non leap year pre PoA FRS with above max value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              3276713,              "error.AC12.coho.turnover.above.max",      "6 month with non leap year pre PoA FRS with above max value abridged"),
        ("2015-07-01",     "2015-12-31",     false,             -3276713,             "error.AC12.coho.turnover.below.min",      "6 month with non leap year pre PoA FRS with below min value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              -3276713,             "error.AC12.coho.turnover.below.min",      "6 month with non leap year pre PoA FRS with below min value abridged"),

        ("2017-07-01",     "2017-12-31",     false,             0,                   "",                                        "6 month with non leap year PoA post FRS with zero value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              0,                   "",                                        "6 month with non leap year PoA post FRS with zero value abridged"),
        ("2017-07-01",     "2017-12-31",     false,             1,                   "",                                        "6 month with non leap year PoA post FRS with valid value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              1,                   "",                                        "6 month with non leap year PoA post FRS with valid value abridged"),
        ("2017-07-01",     "2017-12-31",     false,             5141917,              "",                                        "6 month with non leap year PoA post FRS with max value on threshold NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              5141917,              "",                                        "6 month with non leap year PoA post FRS with max value on threshold abridged"),
        ("2017-07-01",     "2017-12-31",     false,             -5141917,             "",                                        "6 month with non leap year PoA post FRS with min value on threshold NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              -5141917,             "",                                        "6 month with non leap year PoA post FRS with min value on threshold abridged"),
        ("2017-07-01",     "2017-12-31",     false,             5141918,              "error.AC12.coho.turnover.above.max",      "6 month with non leap year PoA post FRS with above max value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              5141918,              "error.AC12.coho.turnover.above.max",      "6 month with non leap year PoA post FRS with above max value abridged"),
        ("2017-07-01",     "2017-12-31",     false,             -5141918,             "error.AC12.coho.turnover.below.min",      "6 month with non leap year PoA post FRS with below min value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              -5141918,             "error.AC12.coho.turnover.below.min",      "6 month with non leap year PoA post FRS with below min value abridged"),

        ("2016-03-01",     "2017-03-01",     false,             0,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with zero value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              0,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with zero value abridged"),
        ("2016-03-01",     "2017-03-01",     false,             1,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with valid value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              1,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with valid value abridged"),
        ("2016-03-01",     "2017-03-01",     false,             10227945,              "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with max value on threshold NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              10227945,              "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with max value on threshold abridged"),
        ("2016-03-01",     "2017-03-01",     false,             -10227945,             "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with min value on threshold NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              -10227945,             "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with min value on threshold abridged"),
        ("2016-03-01",     "2017-03-01",     false,             10227946,              "error.AC12.coho.turnover.above.max",      "1 year and 1 day PoA starting 1 March in leap year post FRS with above max value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              10227946,              "error.AC12.coho.turnover.above.max",      "1 year and 1 day PoA starting 1 March in leap year post FRS with above max value abridged"),
        ("2016-03-01",     "2017-03-01",     false,             -10227946,             "error.AC12.coho.turnover.below.min",      "1 year and 1 day PoA starting 1 March in leap year post FRS with below min value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              -10227946,             "error.AC12.coho.turnover.below.min",      "1 year and 1 day PoA starting 1 March in leap year post FRS with below min value abridged"),

        ("2019-02-28",     "2020-02-28",     false,             0,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with zero value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              0,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with zero value abridged"),
        ("2019-02-28",     "2020-02-28",     false,             1,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with valid value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              1,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with valid value abridged"),
        ("2019-02-28",     "2020-02-28",     false,             10227945,              "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              10227945,              "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with max value on threshold abridged"),
        ("2019-02-28",     "2020-02-28",     false,             -10227945,             "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              -10227945,             "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with min value on threshold abridged"),
        ("2019-02-28",     "2020-02-28",     false,             10227946,              "error.AC12.coho.turnover.above.max",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with above max value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              10227946,              "error.AC12.coho.turnover.above.max",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with above max value abridged"),
        ("2019-02-28",     "2020-02-28",     false,             -10227946,             "error.AC12.coho.turnover.below.min",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with below min value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              -10227946,             "error.AC12.coho.turnover.below.min",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with below min value abridged"),

        ("2016-02-29",     "2016-12-31",     false,             0,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with zero value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              0,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with zero value abridged"),
        ("2016-02-29",     "2016-12-31",     false,             1,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with valid value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              1,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with valid value abridged"),
        ("2016-02-29",     "2016-12-31",     false,             8555737,              "",                                        "short PoA starting 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              8555737,              "",                                        "short PoA starting 29 Feb in leap year post FRS with max value on threshold abridged"),
        ("2016-02-29",     "2016-12-31",     false,             -8555737,             "",                                        "short PoA starting 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              -8555737,             "",                                        "short PoA starting 29 Feb in leap year post FRS with min value on threshold abridged"),
        ("2016-02-29",     "2016-12-31",     false,             8555738,              "error.AC12.coho.turnover.above.max",      "short PoA starting 29 Feb in leap year post FRS with above max value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              8555738,              "error.AC12.coho.turnover.above.max",      "short PoA starting 29 Feb in leap year post FRS with above max value abridged"),
        ("2016-02-29",     "2016-12-31",     false,             -8555738,             "error.AC12.coho.turnover.below.min",      "short PoA starting 29 Feb in leap year post FRS with below min value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              -8555738,             "error.AC12.coho.turnover.below.min",      "short PoA starting 29 Feb in leap year post FRS with below min value abridged"),

        ("2019-02-01",     "2020-02-29",     false,             0,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with zero value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              0,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with zero value abridged"),
        ("2019-02-01",     "2020-02-29",     false,             1,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with valid value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              1,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with valid value abridged"),
        ("2019-02-01",     "2020-02-29",     false,             10980327,              "",                                        "long PoA ending 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              10980327,              "",                                        "long PoA ending 29 Feb in leap year post FRS with max value on threshold abridged"),
        ("2019-02-01",     "2020-02-29",     false,             -10980327,             "",                                        "long PoA ending 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              -10980327,             "",                                        "long PoA ending 29 Feb in leap year post FRS with min value on threshold abridged"),
        ("2019-02-01",     "2020-02-29",     false,             10980328,              "error.AC12.coho.turnover.above.max",      "long PoA ending 29 Feb in leap year post FRS with above max value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              10980328,              "error.AC12.coho.turnover.above.max",      "long PoA ending 29 Feb in leap year post FRS with above max value abridged"),
        ("2019-02-01",     "2020-02-29",     false,             -10980328,             "error.AC12.coho.turnover.below.min",      "long PoA ending 29 Feb in leap year post FRS with below min value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              -10980328,             "error.AC12.coho.turnover.below.min",      "long PoA ending 29 Feb in leap year post FRS with below min value abridged"),

        ("2019-06-01",     "2020-05-31",     false,             0,                   "",                                        "1 year PoA ending in leap year post FRS with zero value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              0,                   "",                                        "1 year PoA ending in leap year post FRS with zero value abridged"),
        ("2019-06-01",     "2020-05-31",     false,             1,                   "",                                        "1 year PoA ending in leap year post FRS with valid value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              1,                   "",                                        "1 year PoA ending in leap year post FRS with valid value abridged"),
        ("2019-06-01",     "2020-05-31",     false,             10200000,              "",                                        "1 year PoA ending in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              10200000,              "",                                        "1 year PoA ending in leap year post FRS with max value on threshold abridged"),
        ("2019-06-01",     "2020-05-31",     false,             -10200000,             "",                                        "1 year PoA ending in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              -10200000,             "",                                        "1 year PoA ending in leap year post FRS with min value on threshold abridged"),
        ("2019-06-01",     "2020-05-31",     false,             10200001,              "error.AC12.coho.turnover.above.max",      "1 year PoA ending in leap year post FRS with above max value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              10200001,              "error.AC12.coho.turnover.above.max",      "1 year PoA ending in leap year post FRS with above max value abridged"),
        ("2019-06-01",     "2020-05-31",     false,             -10200001,             "error.AC12.coho.turnover.below.min",      "1 year PoA ending in leap year post FRS with below min value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              -10200001,             "error.AC12.coho.turnover.below.min",      "1 year PoA ending in leap year post FRS with below min value abridged"),

        ("2019-03-01",     "2020-02-29",     false,             0,                   "",                                        "1 year PoA ending 29 Feb in leap year post FRS with zero value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              0,                   "",                                        "1 year PoA ending 29 Feb in leap year post FRS with zero value abridged"),
        ("2019-03-01",     "2020-02-29",     false,             1,                   "",                                        "1 year PoA ending 29 Feb in leap year post FRS with valid value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              1,                   "",                                        "1 year PoA ending 29 Feb in leap year post FRS with valid value abridged"),
        ("2019-03-01",     "2020-02-29",     false,             10200000,              "",                                        "1 year PoA ending 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              10200000,              "",                                        "1 year PoA ending 29 Feb in leap year post FRS with max value on threshold abridged"),
        ("2019-03-01",     "2020-02-29",     false,             -10200000,             "",                                        "1 year PoA ending 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              -10200000,             "",                                        "1 year PoA ending 29 Feb in leap year post FRS with min value on threshold abridged"),
        ("2019-03-01",     "2020-02-29",     false,             10200001,              "error.AC12.coho.turnover.above.max",      "1 year PoA ending 29 Feb in leap year post FRS with above max value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              10200001,              "error.AC12.coho.turnover.above.max",      "1 year PoA ending 29 Feb in leap year post FRS with above max value abridged"),
        ("2019-03-01",     "2020-02-29",     false,             -10200001,             "error.AC12.coho.turnover.below.min",      "1 year PoA ending 29 Feb in leap year post FRS with below min value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              -10200001,             "error.AC12.coho.turnover.below.min",      "1 year PoA ending 29 Feb in leap year post FRS with below min value abridged")
      )

      executeTests(isHmrcFiling = isHmrcFiling, isCoHoFiling = isCoHoFiling)(CompanyTypes.AllCompanyTypes -- CompanyTypes.AllCharityTypes)(testTable)
    }
  }


  private def assertHmrcInvolvedCompanyValidation(isHmrcFiling: Boolean , isCoHoFiling: Boolean): Unit = {

    val testTable = Table(
      ("poaStartDate",   "poaEndDate",     "abridgedFiling",  "ac12Value",         "errorKey",                                "message"),
      ("2015-01-01",     "2015-12-31",     false,             0,                   "",                                        "1 year PoA pre FRS with zero value NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              0,                   "",                                        "1 year PoA pre FRS with zero value abridged"),
      ("2015-01-01",     "2015-12-31",     false,             1,                   "",                                        "1 year PoA pre FRS with valid value NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              1,                   "",                                        "1 year PoA pre FRS with valid value abridged"),
      ("2015-01-01",     "2015-12-31",     false,             632000,              "",                                        "1 year PoA pre FRS with max value on threshold NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              632000,              "",                                        "1 year PoA pre FRS with max value on threshold abridged"),
      ("2015-01-01",     "2015-12-31",     false,             -632000,             "",                                        "1 year PoA pre FRS with min value on threshold NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              -632000,             "",                                        "1 year PoA pre FRS with min value on threshold abridged"),
      ("2015-01-01",     "2015-12-31",     false,             632001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA pre FRS with above max value NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              632001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA pre FRS with above max value abridged"),
      ("2015-01-01",     "2015-12-31",     false,             -632001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA pre FRS with below min value NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              -632001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA pre FRS with below min value abridged"),

      ("2016-01-01",     "2016-12-31",     false,             0,                   "",                                        "1 year PoA FRS with zero value NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              0,                   "",                                        "1 year PoA FRS with zero value abridged"),
      ("2016-01-01",     "2016-12-31",     false,             1,                   "",                                        "1 year PoA FRS with valid value NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              1,                   "",                                        "1 year PoA FRS with valid value abridged"),
      ("2016-01-01",     "2016-12-31",     false,             632000,              "",                                        "1 year PoA FRS with max value on threshold NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              632000,              "",                                        "1 year PoA FRS with max value on threshold abridged"),
      ("2016-01-01",     "2016-12-31",     false,             -632000,             "",                                        "1 year PoA FRS with min value on threshold NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              -632000,             "",                                        "1 year PoA FRS with min value on threshold abridged"),
      ("2016-01-01",     "2016-12-31",     false,             632001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA FRS with above max value NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              632001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA FRS with above max value abridged"),
      ("2016-01-01",     "2016-12-31",     false,             -632001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA FRS with below min value NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              -632001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA FRS with below min value abridged"),


      ("2015-07-01",     "2016-12-31",     false,             0,                   "",                                        "18 month with leap year pre PoA FRS with zero value NOT abridged"),
      ("2015-07-01",     "2016-12-31",     true,              0,                   "",                                        "18 month with leap year pre PoA FRS with zero value abridged"),
      ("2015-07-01",     "2016-12-31",     false,             1,                   "",                                        "18 month with leap year pre PoA FRS with valid value NOT abridged"),
      ("2015-07-01",     "2016-12-31",     true,              1,                   "",                                        "18 month with leap year pre PoA FRS with valid value abridged"),
      ("2015-07-01",     "2016-12-31",     false,             949726,              "",                                        "18 month with leap year pre PoA FRS with max value on threshold NOT abridged"),
      ("2015-07-01",     "2016-12-31",     true,              949726,              "",                                        "18 month with leap year pre PoA FRS with max value on threshold abridged"),
      ("2015-07-01",     "2016-12-31",     false,             -949726,             "",                                        "18 month with leap year pre PoA FRS with min value on threshold NOT abridged"),
      ("2015-07-01",     "2016-12-31",     true,              -949726,             "",                                        "18 month with leap year pre PoA FRS with min value on threshold abridged"),
      ("2015-07-01",     "2016-12-31",     false,             949727,              "error.AC12.hmrc.turnover.above.max",      "18 month with leap year pre PoA FRS with above max value NOT abridged"),
      ("2015-07-01",     "2016-12-31",     true,              949727,              "error.AC12.hmrc.turnover.above.max",      "18 month with leap year pre PoA FRS with above max value abridged"),
      ("2015-07-01",     "2016-12-31",     false,             -949727,             "error.AC12.hmrc.turnover.below.min",      "18 month with leap year pre PoA FRS with below min value NOT abridged"),
      ("2015-07-01",     "2016-12-31",     true,              -949727,             "error.AC12.hmrc.turnover.below.min",      "18 month with leap year pre PoA FRS with below min value abridged"),

      ("2019-07-01",     "2020-12-31",     false,             0,                   "",                                        "18 month with leap year PoA post FRS with zero value NOT abridged"),
      ("2019-07-01",     "2020-12-31",     true,              0,                   "",                                        "18 month with leap year PoA post FRS with zero value abridged"),
      ("2019-07-01",     "2020-12-31",     false,             1,                   "",                                        "18 month with leap year PoA post FRS with valid value NOT abridged"),
      ("2019-07-01",     "2020-12-31",     true,              1,                   "",                                        "18 month with leap year PoA post FRS with valid value abridged"),
      ("2019-07-01",     "2020-12-31",     false,             949726,              "",                                        "18 month with leap year PoA post FRS with max value on threshold NOT abridged"),
      ("2019-07-01",     "2020-12-31",     true,              949726,              "",                                        "18 month with leap year PoA post FRS with max value on threshold abridged"),
      ("2019-07-01",     "2020-12-31",     false,             -949726,             "",                                        "18 month with leap year PoA post FRS with min value on threshold NOT abridged"),
      ("2019-07-01",     "2020-12-31",     true,              -949726,             "",                                        "18 month with leap year PoA post FRS with min value on threshold abridged"),
      ("2019-07-01",     "2020-12-31",     false,             949727,              "error.AC12.hmrc.turnover.above.max",      "18 month with leap year PoA post FRS with above max value NOT abridged"),
      ("2019-07-01",     "2020-12-31",     true,              949727,              "error.AC12.hmrc.turnover.above.max",      "18 month with leap year PoA post FRS with above max value abridged"),
      ("2019-07-01",     "2020-12-31",     false,             -949727,             "error.AC12.hmrc.turnover.below.min",      "18 month with leap year PoA post FRS with below min value NOT abridged"),
      ("2019-07-01",     "2020-12-31",     true,              -949727,             "error.AC12.hmrc.turnover.below.min",      "18 month with leap year PoA post FRS with below min value abridged"),

      ("2014-07-01",     "2015-12-31",     false,             0,                   "",                                        "18 month with non leap year pre PoA FRS with zero value NOT abridged"),
      ("2014-07-01",     "2015-12-31",     true,              0,                   "",                                        "18 month with non leap year pre PoA FRS with zero value abridged"),
      ("2014-07-01",     "2015-12-31",     false,             1,                   "",                                        "18 month with non leap year pre PoA FRS with valid value NOT abridged"),
      ("2014-07-01",     "2015-12-31",     true,              1,                   "",                                        "18 month with non leap year pre PoA FRS with valid value abridged"),
      ("2014-07-01",     "2015-12-31",     false,             950597,              "",                                        "18 month with non leap year pre PoA FRS with max value on threshold NOT abridged"),
      ("2014-07-01",     "2015-12-31",     true,              950597,              "",                                        "18 month with non leap year pre PoA FRS with max value on threshold abridged"),
      ("2014-07-01",     "2015-12-31",     false,             -950597,             "",                                        "18 month with non leap year pre PoA FRS with min value on threshold NOT abridged"),
      ("2014-07-01",     "2015-12-31",     true,              -950597,             "",                                        "18 month with non leap year pre PoA FRS with min value on threshold abridged"),
      ("2014-07-01",     "2015-12-31",     false,             950598,              "error.AC12.hmrc.turnover.above.max",      "18 month with non leap year pre PoA FRS with above max value NOT abridged"),
      ("2014-07-01",     "2015-12-31",     true,              950598,              "error.AC12.hmrc.turnover.above.max",      "18 month with non leap year pre PoA FRS with above max value abridged"),
      ("2014-07-01",     "2015-12-31",     false,             -950598,             "error.AC12.hmrc.turnover.below.min",      "18 month with non leap year pre PoA FRS with below min value NOT abridged"),
      ("2014-07-01",     "2015-12-31",     true,              -950598,             "error.AC12.hmrc.turnover.below.min",      "18 month with non leap year pre PoA FRS with below min value abridged"),

      ("2017-07-01",     "2018-12-31",     false,             0,                   "",                                        "18 month with non leap year PoA post FRS with zero value NOT abridged"),
      ("2017-07-01",     "2018-12-31",     true,              0,                   "",                                        "18 month with non leap year PoA post FRS with zero value abridged"),
      ("2017-07-01",     "2018-12-31",     false,             1,                   "",                                        "18 month with non leap year PoA post FRS with valid value NOT abridged"),
      ("2017-07-01",     "2018-12-31",     true,              1,                   "",                                        "18 month with non leap year PoA post FRS with valid value abridged"),
      ("2017-07-01",     "2018-12-31",     false,             950597,              "",                                        "18 month with non leap year PoA post FRS with max value on threshold NOT abridged"),
      ("2017-07-01",     "2018-12-31",     true,              950597,              "",                                        "18 month with non leap year PoA post FRS with max value on threshold abridged"),
      ("2017-07-01",     "2018-12-31",     false,             -950597,             "",                                        "18 month with non leap year PoA post FRS with min value on threshold NOT abridged"),
      ("2017-07-01",     "2018-12-31",     true,              -950597,             "",                                        "18 month with non leap year PoA post FRS with min value on threshold abridged"),
      ("2017-07-01",     "2018-12-31",     false,             950598,              "error.AC12.hmrc.turnover.above.max",      "18 month with non leap year PoA post FRS with above max value NOT abridged"),
      ("2017-07-01",     "2018-12-31",     true,              950598,              "error.AC12.hmrc.turnover.above.max",      "18 month with non leap year PoA post FRS with above max value abridged"),
      ("2017-07-01",     "2018-12-31",     false,             -950598,             "error.AC12.hmrc.turnover.below.min",      "18 month with non leap year PoA post FRS with below min value NOT abridged"),
      ("2017-07-01",     "2018-12-31",     true,              -950598,             "error.AC12.hmrc.turnover.below.min",      "18 month with non leap year PoA post FRS with below min value abridged"),


      ("2015-12-01",     "2016-05-31",     false,             0,                   "",                                        "6 month with leap year pre PoA FRS with zero value NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              0,                   "",                                        "6 month with leap year pre PoA FRS with zero value abridged"),
      ("2015-12-01",     "2016-05-31",     false,             1,                   "",                                        "6 month with leap year pre PoA FRS with valid value NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              1,                   "",                                        "6 month with leap year pre PoA FRS with valid value abridged"),
      ("2015-12-01",     "2016-05-31",     false,             316000,              "",                                        "6 month with leap year pre PoA FRS with max value on threshold NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              316000,              "",                                        "6 month with leap year pre PoA FRS with max value on threshold abridged"),
      ("2015-12-01",     "2016-05-31",     false,             -316000,             "",                                        "6 month with leap year pre PoA FRS with min value on threshold NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              -316000,             "",                                        "6 month with leap year pre PoA FRS with min value on threshold abridged"),
      ("2015-12-01",     "2016-05-31",     false,             316001,              "error.AC12.hmrc.turnover.above.max",      "6 month with leap year pre PoA FRS with above max value NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              316001,              "error.AC12.hmrc.turnover.above.max",      "6 month with leap year pre PoA FRS with above max value abridged"),
      ("2015-12-01",     "2016-05-31",     false,             -316001,             "error.AC12.hmrc.turnover.below.min",      "6 month with leap year pre PoA FRS with below min value NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              -316001,             "error.AC12.hmrc.turnover.below.min",      "6 month with leap year pre PoA FRS with below min value abridged"),

      ("2020-01-01",     "2020-06-30",     false,             0,                   "",                                        "6 month with leap year PoA post FRS with zero value NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              0,                   "",                                        "6 month with leap year PoA post FRS with zero value abridged"),
      ("2020-01-01",     "2020-06-30",     false,             1,                   "",                                        "6 month with leap year PoA post FRS with valid value NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              1,                   "",                                        "6 month with leap year PoA post FRS with valid value abridged"),
      ("2020-01-01",     "2020-06-30",     false,             314273,              "",                                        "6 month with leap year PoA post FRS with max value on threshold NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              314273,              "",                                        "6 month with leap year PoA post FRS with max value on threshold abridged"),
      ("2020-01-01",     "2020-06-30",     false,             -314273,             "",                                        "6 month with leap year PoA post FRS with min value on threshold NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              -314273,             "",                                        "6 month with leap year PoA post FRS with min value on threshold abridged"),
      ("2020-01-01",     "2020-06-30",     false,             314274,              "error.AC12.hmrc.turnover.above.max",      "6 month with leap year PoA post FRS with above max value NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              314274,              "error.AC12.hmrc.turnover.above.max",      "6 month with leap year PoA post FRS with above max value abridged"),
      ("2020-01-01",     "2020-06-30",     false,             -314274,             "error.AC12.hmrc.turnover.below.min",      "6 month with leap year PoA post FRS with below min value NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              -314274,             "error.AC12.hmrc.turnover.below.min",      "6 month with leap year PoA post FRS with below min value abridged"),

      ("2015-07-01",     "2015-12-31",     false,             0,                   "",                                        "6 month with non leap year pre PoA FRS with zero value NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              0,                   "",                                        "6 month with non leap year pre PoA FRS with zero value abridged"),
      ("2015-07-01",     "2015-12-31",     false,             1,                   "",                                        "6 month with non leap year pre PoA FRS with valid value NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              1,                   "",                                        "6 month with non leap year pre PoA FRS with valid value abridged"),
      ("2015-07-01",     "2015-12-31",     false,             318597,              "",                                        "6 month with non leap year pre PoA FRS with max value on threshold NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              318597,              "",                                        "6 month with non leap year pre PoA FRS with max value on threshold abridged"),
      ("2015-07-01",     "2015-12-31",     false,             -318597,             "",                                        "6 month with non leap year pre PoA FRS with min value on threshold NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              -318597,             "",                                        "6 month with non leap year pre PoA FRS with min value on threshold abridged"),
      ("2015-07-01",     "2015-12-31",     false,             318598,              "error.AC12.hmrc.turnover.above.max",      "6 month with non leap year pre PoA FRS with above max value NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              318598,              "error.AC12.hmrc.turnover.above.max",      "6 month with non leap year pre PoA FRS with above max value abridged"),
      ("2015-07-01",     "2015-12-31",     false,             -318598,             "error.AC12.hmrc.turnover.below.min",      "6 month with non leap year pre PoA FRS with below min value NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              -318598,             "error.AC12.hmrc.turnover.below.min",      "6 month with non leap year pre PoA FRS with below min value abridged"),

      ("2017-07-01",     "2017-12-31",     false,             0,                   "",                                        "6 month with non leap year PoA post FRS with zero value NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              0,                   "",                                        "6 month with non leap year PoA post FRS with zero value abridged"),
      ("2017-07-01",     "2017-12-31",     false,             1,                   "",                                        "6 month with non leap year PoA post FRS with valid value NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              1,                   "",                                        "6 month with non leap year PoA post FRS with valid value abridged"),
      ("2017-07-01",     "2017-12-31",     false,             318597,              "",                                        "6 month with non leap year PoA post FRS with max value on threshold NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              318597,              "",                                        "6 month with non leap year PoA post FRS with max value on threshold abridged"),
      ("2017-07-01",     "2017-12-31",     false,             -318597,             "",                                        "6 month with non leap year PoA post FRS with min value on threshold NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              -318597,             "",                                        "6 month with non leap year PoA post FRS with min value on threshold abridged"),
      ("2017-07-01",     "2017-12-31",     false,             318598,              "error.AC12.hmrc.turnover.above.max",      "6 month with non leap year PoA post FRS with above max value NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              318598,              "error.AC12.hmrc.turnover.above.max",      "6 month with non leap year PoA post FRS with above max value abridged"),
      ("2017-07-01",     "2017-12-31",     false,             -318598,             "error.AC12.hmrc.turnover.below.min",      "6 month with non leap year PoA post FRS with below min value NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              -318598,             "error.AC12.hmrc.turnover.below.min",      "6 month with non leap year PoA post FRS with below min value abridged"),

      ("2016-03-01",     "2017-03-01",     false,             0,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with zero value NOT abridged"),
      ("2016-03-01",     "2017-03-01",     true,              0,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with zero value abridged"),
      ("2016-03-01",     "2017-03-01",     false,             1,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with valid value NOT abridged"),
      ("2016-03-01",     "2017-03-01",     true,              1,                   "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with valid value abridged"),
      ("2016-03-01",     "2017-03-01",     false,             633731,              "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with max value on threshold NOT abridged"),
      ("2016-03-01",     "2017-03-01",     true,              633731,              "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with max value on threshold abridged"),
      ("2016-03-01",     "2017-03-01",     false,             -633731,             "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with min value on threshold NOT abridged"),
      ("2016-03-01",     "2017-03-01",     true,              -633731,             "",                                        "1 year and 1 day PoA starting 1 March in leap year post FRS with min value on threshold abridged"),
      ("2016-03-01",     "2017-03-01",     false,             633732,              "error.AC12.hmrc.turnover.above.max",      "1 year and 1 day PoA starting 1 March in leap year post FRS with above max value NOT abridged"),
      ("2016-03-01",     "2017-03-01",     true,              633732,              "error.AC12.hmrc.turnover.above.max",      "1 year and 1 day PoA starting 1 March in leap year post FRS with above max value abridged"),
      ("2016-03-01",     "2017-03-01",     false,             -633732,             "error.AC12.hmrc.turnover.below.min",      "1 year and 1 day PoA starting 1 March in leap year post FRS with below min value NOT abridged"),
      ("2016-03-01",     "2017-03-01",     true,              -633732,             "error.AC12.hmrc.turnover.below.min",      "1 year and 1 day PoA starting 1 March in leap year post FRS with below min value abridged"),

      ("2019-02-28",     "2020-02-28",     false,             0,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with zero value NOT abridged"),
      ("2019-02-28",     "2020-02-28",     true,              0,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with zero value abridged"),
      ("2019-02-28",     "2020-02-28",     false,             1,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with valid value NOT abridged"),
      ("2019-02-28",     "2020-02-28",     true,              1,                   "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with valid value abridged"),
      ("2019-02-28",     "2020-02-28",     false,             633731,              "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with max value on threshold NOT abridged"),
      ("2019-02-28",     "2020-02-28",     true,              633731,              "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with max value on threshold abridged"),
      ("2019-02-28",     "2020-02-28",     false,             -633731,             "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with min value on threshold NOT abridged"),
      ("2019-02-28",     "2020-02-28",     true,              -633731,             "",                                        "1 year and 1 day PoA ending 28 Feb in leap year post FRS with min value on threshold abridged"),
      ("2019-02-28",     "2020-02-28",     false,             633732,              "error.AC12.hmrc.turnover.above.max",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with above max value NOT abridged"),
      ("2019-02-28",     "2020-02-28",     true,              633732,              "error.AC12.hmrc.turnover.above.max",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with above max value abridged"),
      ("2019-02-28",     "2020-02-28",     false,             -633732,             "error.AC12.hmrc.turnover.below.min",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with below min value NOT abridged"),
      ("2019-02-28",     "2020-02-28",     true,              -633732,             "error.AC12.hmrc.turnover.below.min",      "1 year and 1 day PoA ending 28 Feb in leap year post FRS with below min value abridged"),

      ("2016-02-29",     "2016-12-31",     false,             0,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with zero value NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              0,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with zero value abridged"),
      ("2016-02-29",     "2016-12-31",     false,             1,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with valid value NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              1,                   "",                                        "short PoA starting 29 Feb in leap year post FRS with valid value abridged"),
      ("2016-02-29",     "2016-12-31",     false,             530120,              "",                                        "short PoA starting 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              530120,              "",                                        "short PoA starting 29 Feb in leap year post FRS with max value on threshold abridged"),
      ("2016-02-29",     "2016-12-31",     false,             -530120,             "",                                        "short PoA starting 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              -530120,             "",                                        "short PoA starting 29 Feb in leap year post FRS with min value on threshold abridged"),
      ("2016-02-29",     "2016-12-31",     false,             530121,              "error.AC12.hmrc.turnover.above.max",      "short PoA starting 29 Feb in leap year post FRS with above max value NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              530121,              "error.AC12.hmrc.turnover.above.max",      "short PoA starting 29 Feb in leap year post FRS with above max value abridged"),
      ("2016-02-29",     "2016-12-31",     false,             -530121,             "error.AC12.hmrc.turnover.below.min",      "short PoA starting 29 Feb in leap year post FRS with below min value NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              -530121,             "error.AC12.hmrc.turnover.below.min",      "short PoA starting 29 Feb in leap year post FRS with below min value abridged"),

      ("2019-02-01",     "2020-02-29",     false,             0,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with zero value NOT abridged"),
      ("2019-02-01",     "2020-02-29",     true,              0,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with zero value abridged"),
      ("2019-02-01",     "2020-02-29",     false,             1,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with valid value NOT abridged"),
      ("2019-02-01",     "2020-02-29",     true,              1,                   "",                                        "long PoA ending 29 Feb in leap year post FRS with valid value abridged"),
      ("2019-02-01",     "2020-02-29",     false,             680349,              "",                                        "long PoA ending 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
      ("2019-02-01",     "2020-02-29",     true,              680349,              "",                                        "long PoA ending 29 Feb in leap year post FRS with max value on threshold abridged"),
      ("2019-02-01",     "2020-02-29",     false,             -680349,             "",                                        "long PoA ending 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
      ("2019-02-01",     "2020-02-29",     true,              -680349,             "",                                        "long PoA ending 29 Feb in leap year post FRS with min value on threshold abridged"),
      ("2019-02-01",     "2020-02-29",     false,             680350,              "error.AC12.hmrc.turnover.above.max",      "long PoA ending 29 Feb in leap year post FRS with above max value NOT abridged"),
      ("2019-02-01",     "2020-02-29",     true,              680350,              "error.AC12.hmrc.turnover.above.max",      "long PoA ending 29 Feb in leap year post FRS with above max value abridged"),
      ("2019-02-01",     "2020-02-29",     false,             -680350,             "error.AC12.hmrc.turnover.below.min",      "long PoA ending 29 Feb in leap year post FRS with below min value NOT abridged"),
      ("2019-02-01",     "2020-02-29",     true,              -680350,             "error.AC12.hmrc.turnover.below.min",      "long PoA ending 29 Feb in leap year post FRS with below min value abridged"),

      ("2019-06-01",     "2020-05-31",     false,             0,                   "",                                        "1 year PoA ending in leap year post FRS with zero value NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              0,                   "",                                        "1 year PoA ending in leap year post FRS with zero value abridged"),
      ("2019-06-01",     "2020-05-31",     false,             1,                   "",                                        "1 year PoA ending in leap year post FRS with valid value NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              1,                   "",                                        "1 year PoA ending in leap year post FRS with valid value abridged"),
      ("2019-06-01",     "2020-05-31",     false,             632000,              "",                                        "1 year PoA ending in leap year post FRS with max value on threshold NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              632000,              "",                                        "1 year PoA ending in leap year post FRS with max value on threshold abridged"),
      ("2019-06-01",     "2020-05-31",     false,             -632000,             "",                                        "1 year PoA ending in leap year post FRS with min value on threshold NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              -632000,             "",                                        "1 year PoA ending in leap year post FRS with min value on threshold abridged"),
      ("2019-06-01",     "2020-05-31",     false,             632001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA ending in leap year post FRS with above max value NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              632001,              "error.AC12.hmrc.turnover.above.max",      "1 year PoA ending in leap year post FRS with above max value abridged"),
      ("2019-06-01",     "2020-05-31",     false,             -632001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA ending in leap year post FRS with below min value NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              -632001,             "error.AC12.hmrc.turnover.below.min",      "1 year PoA ending in leap year post FRS with below min value abridged")
    )

    executeTests(isHmrcFiling = isHmrcFiling, isCoHoFiling = isCoHoFiling)(CompanyTypes.AllCompanyTypes -- CompanyTypes.AllCharityTypes)(testTable)
  }

  def executeTests(isHmrcFiling: Boolean, isCoHoFiling: Boolean)(companyTypes: Set[CompanyType])(table: TableFor6[String, String, Boolean, Int, String, String]): Unit = {
    companyTypes.foreach { companyType =>

      s"testing validation for $companyType" when {
        forAll(table) { (startDateString: String, endDateString: String, abridgedFiling: Boolean, ac12Value: Int, expectedErrorKey: String, message: String) =>
          val boxRetriever = mock[TestBoxRetriever]

          when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(startDateString)))
          when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(endDateString)))
          when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(isHmrcFiling))
          when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(isCoHoFiling))
          when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(abridgedFiling))
          when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))

          s"$message : $ac12Value" in {
            val validationResult = AC12(Some(ac12Value)).validate(boxRetriever)
            if (expectedErrorKey.isEmpty) {
              withClue(s"HMRC: $isHmrcFiling, CoHo: $isCoHoFiling ::: $message")(validationResult shouldBe empty)
            }
            else {
              val error = validationResult.find { error =>
                error.boxId.contains("AC12") && error.errorMessageKey == expectedErrorKey && error.args.map { args => args.size == 2}.getOrElse(false)
              }
              withClue(s"HMRC: $isHmrcFiling, CoHo: $isCoHoFiling ::: $message : $validationResult"){error should not be empty}
            }
          }

        }
      }
    }
  }
}

trait TestBoxRetriever extends AccountsBoxRetriever with FilingAttributesBoxValueRetriever
