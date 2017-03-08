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

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.TableFor6
import org.scalatest.prop.Tables.Table
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.{AbridgedFiling, CompaniesHouseFiling, FilingCompanyType, HMRCFiling}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.domain.CompanyTypes
import uk.gov.hmrc.ct.domain.CompanyTypes.CompanyType

class CP7Spec extends WordSpec with Matchers with MockitoSugar {

  implicit val format = {
    import uk.gov.hmrc.ct.computations.formats._
    Json.format[CP7Holder]
  }

  "CP7 to json" should {
    "create valid json for int value" in {
      val json = Json.toJson(CP7Holder(CP7(Some(1234))))
      json.toString shouldBe """{"cp7":1234}"""
    }
    "create valid json for None" in {
      val json = Json.toJson(CP7Holder(CP7(None)))
      json.toString shouldBe """{"cp7":null}"""
    }
    "create valid json for default" in {
      val json = Json.toJson(CP7Holder(CP7(None, Some(54321))))
      json.toString shouldBe """{"cp7":54321}"""
    }
  }

  "CP7 from json" should {
    "create +ve int from valid json" in {
      val json = Json.parse("""{"cp7":1234}""")
      Json.fromJson[CP7Holder](json).get shouldBe CP7Holder(cp7 = new CP7(Some(1234), None))
    }
    "create None from valid json" in {
      val json = Json.parse("""{"cp7":null}""")
      Json.fromJson[CP7Holder](json).get shouldBe CP7Holder(cp7 = new CP7(None, None))
    }
  }

  "CP7 validation" should {

    "validate mandatory check" when {

      val testTable = Table(
        ("poaStartDate",   "poaEndDate",  "abridgedFiling",  "ac12Value",  "required", "message"),
        ("2015-01-01",     "2015-12-31",     false,             None,      true,      "1 year AP pre FRS empty NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              None,      true,      "1 year AP pre FRS empty abridged"),
        ("2015-01-01",     "2015-12-01",     false,             None,      true,      "short AP pre FRS empty NOT abridged"),
        ("2015-01-01",     "2015-12-01",     true,              None,      true,      "short AP pre FRS empty abridged"),

        ("2016-01-01",     "2016-12-31",     false,             None,      true,      "1 year AP post FRS empty NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              None,      true,      "1 year AP post FRS empty abridged"),
        ("2016-01-01",     "2016-12-01",     false,             None,      true,      "short AP post FRS empty NOT abridged"),
        ("2016-01-01",     "2016-12-01",     true,              None,      true,      "short AP post FRS empty abridged")
      )

      (CompanyTypes.AllCompanyTypes -- CompanyTypes.AllCharityTypes).foreach { companyType =>

        s"check validation when empty for $companyType" in {

          forAll(testTable) { (startDateString: String, endDateString: String, abridgedFiling: Boolean, cp7Value: Option[Int], required: Boolean, message: String) =>
            val boxRetriever = mock[TestComputationsBoxRetriever]

            when(boxRetriever.cp1()).thenReturn(CP1(new LocalDate(startDateString)))
            when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate(endDateString)))
            when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
            when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
            when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(abridgedFiling))
            when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))

            val validationResult = CP7(cp7Value).validate(boxRetriever)
            if (required)
              validationResult shouldBe Set(CtValidation(Some("CP7"), "error.CP7.required"))
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
        ("2015-01-01",     "2015-12-31",     false,             0,                   "",                                        "1 year AP pre FRS with zero value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              0,                   "",                                        "1 year AP pre FRS with zero value abridged"),
        ("2015-01-01",     "2015-12-31",     false,             1,                   "",                                        "1 year AP pre FRS with valid value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              1,                   "",                                        "1 year AP pre FRS with valid value abridged"),
        ("2015-01-01",     "2015-12-31",     false,             6500000,              "",                                        "1 year AP pre FRS with max value on threshold NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              6500000,              "",                                        "1 year AP pre FRS with max value on threshold abridged"),
        ("2015-01-01",     "2015-12-31",     false,             -6500000,             "",                                        "1 year AP pre FRS with min value on threshold NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              -6500000,             "",                                        "1 year AP pre FRS with min value on threshold abridged"),
        ("2015-01-01",     "2015-12-31",     false,             6500001,              "error.CP7.above.max",      "1 year AP pre FRS with above max value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              6500001,              "error.CP7.above.max",      "1 year AP pre FRS with above max value abridged"),
        ("2015-01-01",     "2015-12-31",     false,             -6500001,             "error.CP7.below.min",      "1 year AP pre FRS with below min value NOT abridged"),
        ("2015-01-01",     "2015-12-31",     true,              -6500001,             "error.CP7.below.min",      "1 year AP pre FRS with below min value abridged"),

        ("2017-01-01",     "2017-12-31",     false,             0,                   "",                                        "1 year AP post FRS with zero value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              0,                   "",                                        "1 year AP post FRS with zero value abridged"),
        ("2017-01-01",     "2017-12-31",     false,             1,                   "",                                        "1 year AP post FRS with valid value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              1,                   "",                                        "1 year AP post FRS with valid value abridged"),
        ("2017-01-01",     "2017-12-31",     false,             6500000,              "",                                        "1 year AP post FRS with max value on threshold NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              6500000,              "",                                        "1 year AP post FRS with max value on threshold abridged"),
        ("2017-01-01",     "2017-12-31",     false,             -6500000,             "",                                        "1 year AP post FRS with min value on threshold NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              -6500000,             "",                                        "1 year AP post FRS with min value on threshold abridged"),
        ("2017-01-01",     "2017-12-31",     false,             6500001,              "error.CP7.above.max",      "1 year AP post FRS with above max value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              6500001,              "error.CP7.above.max",      "1 year AP post FRS with above max value abridged"),
        ("2017-01-01",     "2017-12-31",     false,             -6500001,             "error.CP7.below.min",      "1 year AP post FRS with below min value NOT abridged"),
        ("2017-01-01",     "2017-12-31",     true,              -6500001,             "error.CP7.below.min",      "1 year AP post FRS with below min value abridged"),

        ("2016-01-01",     "2016-12-31",     false,             0,                   "",                                        "1 year AP FRS with zero value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              0,                   "",                                        "1 year AP FRS with zero value abridged"),
        ("2016-01-01",     "2016-12-31",     false,             1,                   "",                                        "1 year AP FRS with valid value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              1,                   "",                                        "1 year AP FRS with valid value abridged"),
        ("2016-01-01",     "2016-12-31",     false,             6500000,              "",                                        "1 year AP FRS with max value on threshold NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              6500000,              "",                                        "1 year AP FRS with max value on threshold abridged"),
        ("2016-01-01",     "2016-12-31",     false,             -6500000,             "",                                        "1 year AP FRS with min value on threshold NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              -6500000,             "",                                        "1 year AP FRS with min value on threshold abridged"),
        ("2016-01-01",     "2016-12-31",     false,             6500001,              "error.CP7.above.max",      "1 year AP FRS with above max value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              6500001,              "error.CP7.above.max",      "1 year AP FRS with above max value abridged"),
        ("2016-01-01",     "2016-12-31",     false,             -6500001,             "error.CP7.below.min",      "1 year AP FRS with below min value NOT abridged"),
        ("2016-01-01",     "2016-12-31",     true,              -6500001,             "error.CP7.below.min",      "1 year AP FRS with below min value abridged"),


        ("2015-07-01",     "2016-12-31",     false,             0,                   "",                                        "18 month with leap year pre AP FRS with zero value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              0,                   "",                                        "18 month with leap year pre AP FRS with zero value abridged"),
        ("2015-07-01",     "2016-12-31",     false,             1,                   "",                                        "18 month with leap year pre AP FRS with valid value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              1,                   "",                                        "18 month with leap year pre AP FRS with valid value abridged"),
        ("2015-07-01",     "2016-12-31",     false,             9767759,              "",                                        "18 month with leap year pre AP FRS with max value on threshold NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              9767759,              "",                                        "18 month with leap year pre AP FRS with max value on threshold abridged"),
        ("2015-07-01",     "2016-12-31",     false,             -9767759,             "",                                        "18 month with leap year pre AP FRS with min value on threshold NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              -9767759,             "",                                        "18 month with leap year pre AP FRS with min value on threshold abridged"),
        ("2015-07-01",     "2016-12-31",     false,             9767760,              "error.CP7.above.max",      "18 month with leap year pre AP FRS with above max value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              9767760,              "error.CP7.above.max",      "18 month with leap year pre AP FRS with above max value abridged"),
        ("2015-07-01",     "2016-12-31",     false,             -9767760,             "error.CP7.below.min",      "18 month with leap year pre AP FRS with below min value NOT abridged"),
        ("2015-07-01",     "2016-12-31",     true,              -9767760,             "error.CP7.below.min",      "18 month with leap year pre AP FRS with below min value abridged"),

        ("2019-07-01",     "2020-12-31",     false,             0,                   "",                                        "18 month with leap year AP post FRS with zero value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              0,                   "",                                        "18 month with leap year AP post FRS with zero value abridged"),
        ("2019-07-01",     "2020-12-31",     false,             1,                   "",                                        "18 month with leap year AP post FRS with valid value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              1,                   "",                                        "18 month with leap year AP post FRS with valid value abridged"),
        ("2019-07-01",     "2020-12-31",     false,             9767759,              "",                                        "18 month with leap year AP post FRS with max value on threshold NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              9767759,              "",                                        "18 month with leap year AP post FRS with max value on threshold abridged"),
        ("2019-07-01",     "2020-12-31",     false,             -9767759,             "",                                        "18 month with leap year AP post FRS with min value on threshold NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              -9767759,             "",                                        "18 month with leap year AP post FRS with min value on threshold abridged"),
        ("2019-07-01",     "2020-12-31",     false,             9767760,              "error.CP7.above.max",      "18 month with leap year AP post FRS with above max value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              9767760,              "error.CP7.above.max",      "18 month with leap year AP post FRS with above max value abridged"),
        ("2019-07-01",     "2020-12-31",     false,             -9767760,             "error.CP7.below.min",      "18 month with leap year AP post FRS with below min value NOT abridged"),
        ("2019-07-01",     "2020-12-31",     true,              -9767760,             "error.CP7.below.min",      "18 month with leap year AP post FRS with below min value abridged"),

        ("2014-07-01",     "2015-12-31",     false,             0,                   "",                                        "18 month with non leap year pre AP FRS with zero value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              0,                   "",                                        "18 month with non leap year pre AP FRS with zero value abridged"),
        ("2014-07-01",     "2015-12-31",     false,             1,                   "",                                        "18 month with non leap year pre AP FRS with valid value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              1,                   "",                                        "18 month with non leap year pre AP FRS with valid value abridged"),
        ("2014-07-01",     "2015-12-31",     false,             9776712,              "",                                        "18 month with non leap year pre AP FRS with max value on threshold NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              9776712,              "",                                        "18 month with non leap year pre AP FRS with max value on threshold abridged"),
        ("2014-07-01",     "2015-12-31",     false,             -9776712,             "",                                        "18 month with non leap year pre AP FRS with min value on threshold NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              -9776712,             "",                                        "18 month with non leap year pre AP FRS with min value on threshold abridged"),
        ("2014-07-01",     "2015-12-31",     false,             9776713,              "error.CP7.above.max",      "18 month with non leap year pre AP FRS with above max value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              9776713,              "error.CP7.above.max",      "18 month with non leap year pre AP FRS with above max value abridged"),
        ("2014-07-01",     "2015-12-31",     false,             -9776713,             "error.CP7.below.min",      "18 month with non leap year pre AP FRS with below min value NOT abridged"),
        ("2014-07-01",     "2015-12-31",     true,              -9776713,             "error.CP7.below.min",      "18 month with non leap year pre AP FRS with below min value abridged"),

        ("2017-07-01",     "2018-12-31",     false,             0,                   "",                                        "18 month with non leap year AP post FRS with zero value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              0,                   "",                                        "18 month with non leap year AP post FRS with zero value abridged"),
        ("2017-07-01",     "2018-12-31",     false,             1,                   "",                                        "18 month with non leap year AP post FRS with valid value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              1,                   "",                                        "18 month with non leap year AP post FRS with valid value abridged"),
        ("2017-07-01",     "2018-12-31",     false,             9776712,              "",                                        "18 month with non leap year AP post FRS with max value on threshold NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              9776712,              "",                                        "18 month with non leap year AP post FRS with max value on threshold abridged"),
        ("2017-07-01",     "2018-12-31",     false,             -9776712,             "",                                        "18 month with non leap year AP post FRS with min value on threshold NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              -9776712,             "",                                        "18 month with non leap year AP post FRS with min value on threshold abridged"),
        ("2017-07-01",     "2018-12-31",     false,             9776713,              "error.CP7.above.max",      "18 month with non leap year AP post FRS with above max value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              9776713,              "error.CP7.above.max",      "18 month with non leap year AP post FRS with above max value abridged"),
        ("2017-07-01",     "2018-12-31",     false,             -9776713,             "error.CP7.below.min",      "18 month with non leap year AP post FRS with below min value NOT abridged"),
        ("2017-07-01",     "2018-12-31",     true,              -9776713,             "error.CP7.below.min",      "18 month with non leap year AP post FRS with below min value abridged"),


        ("2015-12-01",     "2016-05-31",     false,             0,                   "",                                        "6 month with leap year pre AP FRS with zero value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              0,                   "",                                        "6 month with leap year pre AP FRS with zero value abridged"),
        ("2015-12-01",     "2016-05-31",     false,             1,                   "",                                        "6 month with leap year pre AP FRS with valid value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              1,                   "",                                        "6 month with leap year pre AP FRS with valid value abridged"),
        ("2015-12-01",     "2016-05-31",     false,             3250000,              "",                                        "6 month with leap year pre AP FRS with max value on threshold NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              3250000,              "",                                        "6 month with leap year pre AP FRS with max value on threshold abridged"),
        ("2015-12-01",     "2016-05-31",     false,             -3250000,             "",                                        "6 month with leap year pre AP FRS with min value on threshold NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              -3250000,             "",                                        "6 month with leap year pre AP FRS with min value on threshold abridged"),
        ("2015-12-01",     "2016-05-31",     false,             3250001,              "error.CP7.above.max",      "6 month with leap year pre AP FRS with above max value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              3250001,              "error.CP7.above.max",      "6 month with leap year pre AP FRS with above max value abridged"),
        ("2015-12-01",     "2016-05-31",     false,             -3250001,             "error.CP7.below.min",      "6 month with leap year pre AP FRS with below min value NOT abridged"),
        ("2015-12-01",     "2016-05-31",     true,              -3250001,             "error.CP7.below.min",      "6 month with leap year pre AP FRS with below min value abridged"),

        ("2020-01-01",     "2020-06-30",     false,             0,                   "",                                        "6 month with leap year AP post FRS with zero value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              0,                   "",                                        "6 month with leap year AP post FRS with zero value abridged"),
        ("2020-01-01",     "2020-06-30",     false,             1,                   "",                                        "6 month with leap year AP post FRS with valid value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              1,                   "",                                        "6 month with leap year AP post FRS with valid value abridged"),
        ("2020-01-01",     "2020-06-30",     false,             3232240,              "",                                        "6 month with leap year AP post FRS with max value on threshold NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              3232240,              "",                                        "6 month with leap year AP post FRS with max value on threshold abridged"),
        ("2020-01-01",     "2020-06-30",     false,             -3232240,             "",                                        "6 month with leap year AP post FRS with min value on threshold NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              -3232240,             "",                                        "6 month with leap year AP post FRS with min value on threshold abridged"),
        ("2020-01-01",     "2020-06-30",     false,             3232241,              "error.CP7.above.max",      "6 month with leap year AP post FRS with above max value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              3232241,              "error.CP7.above.max",      "6 month with leap year AP post FRS with above max value abridged"),
        ("2020-01-01",     "2020-06-30",     false,             -3232241,             "error.CP7.below.min",      "6 month with leap year AP post FRS with below min value NOT abridged"),
        ("2020-01-01",     "2020-06-30",     true,              -3232241,             "error.CP7.below.min",      "6 month with leap year AP post FRS with below min value abridged"),

        ("2015-07-01",     "2015-12-31",     false,             0,                   "",                                        "6 month with non leap year pre AP FRS with zero value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              0,                   "",                                        "6 month with non leap year pre AP FRS with zero value abridged"),
        ("2015-07-01",     "2015-12-31",     false,             1,                   "",                                        "6 month with non leap year pre AP FRS with valid value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              1,                   "",                                        "6 month with non leap year pre AP FRS with valid value abridged"),
        ("2015-07-01",     "2015-12-31",     false,             3276712,              "",                                        "6 month with non leap year pre AP FRS with max value on threshold NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              3276712,              "",                                        "6 month with non leap year pre AP FRS with max value on threshold abridged"),
        ("2015-07-01",     "2015-12-31",     false,             -3276712,             "",                                        "6 month with non leap year pre AP FRS with min value on threshold NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              -3276712,             "",                                        "6 month with non leap year pre AP FRS with min value on threshold abridged"),
        ("2015-07-01",     "2015-12-31",     false,             3276713,              "error.CP7.above.max",      "6 month with non leap year pre AP FRS with above max value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              3276713,              "error.CP7.above.max",      "6 month with non leap year pre AP FRS with above max value abridged"),
        ("2015-07-01",     "2015-12-31",     false,             -3276713,             "error.CP7.below.min",      "6 month with non leap year pre AP FRS with below min value NOT abridged"),
        ("2015-07-01",     "2015-12-31",     true,              -3276713,             "error.CP7.below.min",      "6 month with non leap year pre AP FRS with below min value abridged"),

        ("2017-07-01",     "2017-12-31",     false,             0,                   "",                                        "6 month with non leap year AP post FRS with zero value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              0,                   "",                                        "6 month with non leap year AP post FRS with zero value abridged"),
        ("2017-07-01",     "2017-12-31",     false,             1,                   "",                                        "6 month with non leap year AP post FRS with valid value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              1,                   "",                                        "6 month with non leap year AP post FRS with valid value abridged"),
        ("2017-07-01",     "2017-12-31",     false,             3276712,              "",                                        "6 month with non leap year AP post FRS with max value on threshold NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              3276712,              "",                                        "6 month with non leap year AP post FRS with max value on threshold abridged"),
        ("2017-07-01",     "2017-12-31",     false,             -3276712,             "",                                        "6 month with non leap year AP post FRS with min value on threshold NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              -3276712,             "",                                        "6 month with non leap year AP post FRS with min value on threshold abridged"),
        ("2017-07-01",     "2017-12-31",     false,             3276713,              "error.CP7.above.max",      "6 month with non leap year AP post FRS with above max value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              3276713,              "error.CP7.above.max",      "6 month with non leap year AP post FRS with above max value abridged"),
        ("2017-07-01",     "2017-12-31",     false,             -3276713,             "error.CP7.below.min",      "6 month with non leap year AP post FRS with below min value NOT abridged"),
        ("2017-07-01",     "2017-12-31",     true,              -3276713,             "error.CP7.below.min",      "6 month with non leap year AP post FRS with below min value abridged"),

        ("2016-03-01",     "2017-03-01",     false,             0,                   "",                                        "1 year and 1 day AP starting 1 March in leap year post FRS with zero value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              0,                   "",                                        "1 year and 1 day AP starting 1 March in leap year post FRS with zero value abridged"),
        ("2016-03-01",     "2017-03-01",     false,             1,                   "",                                        "1 year and 1 day AP starting 1 March in leap year post FRS with valid value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              1,                   "",                                        "1 year and 1 day AP starting 1 March in leap year post FRS with valid value abridged"),
        ("2016-03-01",     "2017-03-01",     false,             6517808,              "",                                        "1 year and 1 day AP starting 1 March in leap year post FRS with max value on threshold NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              6517808,              "",                                        "1 year and 1 day AP starting 1 March in leap year post FRS with max value on threshold abridged"),
        ("2016-03-01",     "2017-03-01",     false,             -6517808,             "",                                        "1 year and 1 day AP starting 1 March in leap year post FRS with min value on threshold NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              -6517808,             "",                                        "1 year and 1 day AP starting 1 March in leap year post FRS with min value on threshold abridged"),
        ("2016-03-01",     "2017-03-01",     false,             6517809,              "error.CP7.above.max",      "1 year and 1 day AP starting 1 March in leap year post FRS with above max value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              6517809,              "error.CP7.above.max",      "1 year and 1 day AP starting 1 March in leap year post FRS with above max value abridged"),
        ("2016-03-01",     "2017-03-01",     false,             -6517809,             "error.CP7.below.min",      "1 year and 1 day AP starting 1 March in leap year post FRS with below min value NOT abridged"),
        ("2016-03-01",     "2017-03-01",     true,              -6517809,             "error.CP7.below.min",      "1 year and 1 day AP starting 1 March in leap year post FRS with below min value abridged"),

        ("2019-02-28",     "2020-02-28",     false,             0,                   "",                                        "1 year and 1 day AP ending 28 Feb in leap year post FRS with zero value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              0,                   "",                                        "1 year and 1 day AP ending 28 Feb in leap year post FRS with zero value abridged"),
        ("2019-02-28",     "2020-02-28",     false,             1,                   "",                                        "1 year and 1 day AP ending 28 Feb in leap year post FRS with valid value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              1,                   "",                                        "1 year and 1 day AP ending 28 Feb in leap year post FRS with valid value abridged"),
        ("2019-02-28",     "2020-02-28",     false,             6517808,              "",                                        "1 year and 1 day AP ending 28 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              6517808,              "",                                        "1 year and 1 day AP ending 28 Feb in leap year post FRS with max value on threshold abridged"),
        ("2019-02-28",     "2020-02-28",     false,             -6517808,             "",                                        "1 year and 1 day AP ending 28 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              -6517808,             "",                                        "1 year and 1 day AP ending 28 Feb in leap year post FRS with min value on threshold abridged"),
        ("2019-02-28",     "2020-02-28",     false,             6517809,              "error.CP7.above.max",      "1 year and 1 day AP ending 28 Feb in leap year post FRS with above max value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              6517809,              "error.CP7.above.max",      "1 year and 1 day AP ending 28 Feb in leap year post FRS with above max value abridged"),
        ("2019-02-28",     "2020-02-28",     false,             -6517809,             "error.CP7.below.min",      "1 year and 1 day AP ending 28 Feb in leap year post FRS with below min value NOT abridged"),
        ("2019-02-28",     "2020-02-28",     true,              -6517809,             "error.CP7.below.min",      "1 year and 1 day AP ending 28 Feb in leap year post FRS with below min value abridged"),

        ("2016-02-29",     "2016-12-31",     false,             0,                   "",                                        "short AP starting 29 Feb in leap year post FRS with zero value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              0,                   "",                                        "short AP starting 29 Feb in leap year post FRS with zero value abridged"),
        ("2016-02-29",     "2016-12-31",     false,             1,                   "",                                        "short AP starting 29 Feb in leap year post FRS with valid value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              1,                   "",                                        "short AP starting 29 Feb in leap year post FRS with valid value abridged"),
        ("2016-02-29",     "2016-12-31",     false,             5452185,              "",                                        "short AP starting 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              5452185,              "",                                        "short AP starting 29 Feb in leap year post FRS with max value on threshold abridged"),
        ("2016-02-29",     "2016-12-31",     false,             -5452185,             "",                                        "short AP starting 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              -5452185,             "",                                        "short AP starting 29 Feb in leap year post FRS with min value on threshold abridged"),
        ("2016-02-29",     "2016-12-31",     false,             5452186,              "error.CP7.above.max",      "short AP starting 29 Feb in leap year post FRS with above max value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              5452186,              "error.CP7.above.max",      "short AP starting 29 Feb in leap year post FRS with above max value abridged"),
        ("2016-02-29",     "2016-12-31",     false,             -5452186,             "error.CP7.below.min",      "short AP starting 29 Feb in leap year post FRS with below min value NOT abridged"),
        ("2016-02-29",     "2016-12-31",     true,              -5452186,             "error.CP7.below.min",      "short AP starting 29 Feb in leap year post FRS with below min value abridged"),

        ("2019-02-01",     "2020-02-29",     false,             0,                   "",                                        "long AP ending 29 Feb in leap year post FRS with zero value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              0,                   "",                                        "long AP ending 29 Feb in leap year post FRS with zero value abridged"),
        ("2019-02-01",     "2020-02-29",     false,             1,                   "",                                        "long AP ending 29 Feb in leap year post FRS with valid value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              1,                   "",                                        "long AP ending 29 Feb in leap year post FRS with valid value abridged"),
        ("2019-02-01",     "2020-02-29",     false,             6997267,              "",                                        "long AP ending 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              6997267,              "",                                        "long AP ending 29 Feb in leap year post FRS with max value on threshold abridged"),
        ("2019-02-01",     "2020-02-29",     false,             -6997267,             "",                                        "long AP ending 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              -6997267,             "",                                        "long AP ending 29 Feb in leap year post FRS with min value on threshold abridged"),
        ("2019-02-01",     "2020-02-29",     false,             6997268,              "error.CP7.above.max",      "long AP ending 29 Feb in leap year post FRS with above max value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              6997268,              "error.CP7.above.max",      "long AP ending 29 Feb in leap year post FRS with above max value abridged"),
        ("2019-02-01",     "2020-02-29",     false,             -6997268,             "error.CP7.below.min",      "long AP ending 29 Feb in leap year post FRS with below min value NOT abridged"),
        ("2019-02-01",     "2020-02-29",     true,              -6997268,             "error.CP7.below.min",      "long AP ending 29 Feb in leap year post FRS with below min value abridged"),

        ("2019-06-01",     "2020-05-31",     false,             0,                   "",                                        "1 year AP ending in leap year post FRS with zero value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              0,                   "",                                        "1 year AP ending in leap year post FRS with zero value abridged"),
        ("2019-06-01",     "2020-05-31",     false,             1,                   "",                                        "1 year AP ending in leap year post FRS with valid value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              1,                   "",                                        "1 year AP ending in leap year post FRS with valid value abridged"),
        ("2019-06-01",     "2020-05-31",     false,             6500000,              "",                                        "1 year AP ending in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              6500000,              "",                                        "1 year AP ending in leap year post FRS with max value on threshold abridged"),
        ("2019-06-01",     "2020-05-31",     false,             -6500000,             "",                                        "1 year AP ending in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              -6500000,             "",                                        "1 year AP ending in leap year post FRS with min value on threshold abridged"),
        ("2019-06-01",     "2020-05-31",     false,             6500001,              "error.CP7.above.max",      "1 year AP ending in leap year post FRS with above max value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              6500001,              "error.CP7.above.max",      "1 year AP ending in leap year post FRS with above max value abridged"),
        ("2019-06-01",     "2020-05-31",     false,             -6500001,             "error.CP7.below.min",      "1 year AP ending in leap year post FRS with below min value NOT abridged"),
        ("2019-06-01",     "2020-05-31",     true,              -6500001,             "error.CP7.below.min",      "1 year AP ending in leap year post FRS with below min value abridged"),

        ("2019-03-01",     "2020-02-29",     false,             0,                   "",                                        "1 year AP ending 29 Feb in leap year post FRS with zero value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              0,                   "",                                        "1 year AP ending 29 Feb in leap year post FRS with zero value abridged"),
        ("2019-03-01",     "2020-02-29",     false,             1,                   "",                                        "1 year AP ending 29 Feb in leap year post FRS with valid value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              1,                   "",                                        "1 year AP ending 29 Feb in leap year post FRS with valid value abridged"),
        ("2019-03-01",     "2020-02-29",     false,             6500000,              "",                                        "1 year AP ending 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              6500000,              "",                                        "1 year AP ending 29 Feb in leap year post FRS with max value on threshold abridged"),
        ("2019-03-01",     "2020-02-29",     false,             -6500000,             "",                                        "1 year AP ending 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              -6500000,             "",                                        "1 year AP ending 29 Feb in leap year post FRS with min value on threshold abridged"),
        ("2019-03-01",     "2020-02-29",     false,             6500001,              "error.CP7.above.max",      "1 year AP ending 29 Feb in leap year post FRS with above max value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              6500001,              "error.CP7.above.max",      "1 year AP ending 29 Feb in leap year post FRS with above max value abridged"),
        ("2019-03-01",     "2020-02-29",     false,             -6500001,             "error.CP7.below.min",      "1 year AP ending 29 Feb in leap year post FRS with below min value NOT abridged"),
        ("2019-03-01",     "2020-02-29",     true,              -6500001,             "error.CP7.below.min",      "1 year AP ending 29 Feb in leap year post FRS with below min value abridged")
      )

      executeTests(isHmrcFiling = isHmrcFiling, isCoHoFiling = isCoHoFiling)(CompanyTypes.AllCharityTypes)(testTable)
    }

    "Joint filing" when {
      assertHmrcInvolvedCompanyValidation(isHmrcFiling = true, isCoHoFiling = true)
    }

  }


  private def assertHmrcInvolvedCompanyValidation(isHmrcFiling: Boolean, isCoHoFiling: Boolean): Unit = {

    val testTable = Table(
      ("poaStartDate",   "poaEndDate",     "abridgedFiling",  "ac12Value",         "errorKey",                                "message"),
      ("2015-01-01",     "2015-12-31",     false,             0,                   "",                                        "1 year AP pre FRS with zero value NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              0,                   "",                                        "1 year AP pre FRS with zero value abridged"),
      ("2015-01-01",     "2015-12-31",     false,             1,                   "",                                        "1 year AP pre FRS with valid value NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              1,                   "",                                        "1 year AP pre FRS with valid value abridged"),
      ("2015-01-01",     "2015-12-31",     false,             632000,              "",                                        "1 year AP pre FRS with max value on threshold NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              632000,              "",                                        "1 year AP pre FRS with max value on threshold abridged"),
      ("2015-01-01",     "2015-12-31",     false,             -632000,             "",                                        "1 year AP pre FRS with min value on threshold NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              -632000,             "",                                        "1 year AP pre FRS with min value on threshold abridged"),
      ("2015-01-01",     "2015-12-31",     false,             632001,              "error.CP7.above.max",      "1 year AP pre FRS with above max value NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              632001,              "error.CP7.above.max",      "1 year AP pre FRS with above max value abridged"),
      ("2015-01-01",     "2015-12-31",     false,             -632001,             "error.CP7.below.min",      "1 year AP pre FRS with below min value NOT abridged"),
      ("2015-01-01",     "2015-12-31",     true,              -632001,             "error.CP7.below.min",      "1 year AP pre FRS with below min value abridged"),

      ("2016-01-01",     "2016-12-31",     false,             0,                   "",                                        "1 year AP FRS with zero value NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              0,                   "",                                        "1 year AP FRS with zero value abridged"),
      ("2016-01-01",     "2016-12-31",     false,             1,                   "",                                        "1 year AP FRS with valid value NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              1,                   "",                                        "1 year AP FRS with valid value abridged"),
      ("2016-01-01",     "2016-12-31",     false,             632000,              "",                                        "1 year AP FRS with max value on threshold NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              632000,              "",                                        "1 year AP FRS with max value on threshold abridged"),
      ("2016-01-01",     "2016-12-31",     false,             -632000,             "",                                        "1 year AP FRS with min value on threshold NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              -632000,             "",                                        "1 year AP FRS with min value on threshold abridged"),
      ("2016-01-01",     "2016-12-31",     false,             632001,              "error.CP7.above.max",      "1 year AP FRS with above max value NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              632001,              "error.CP7.above.max",      "1 year AP FRS with above max value abridged"),
      ("2016-01-01",     "2016-12-31",     false,             -632001,             "error.CP7.below.min",      "1 year AP FRS with below min value NOT abridged"),
      ("2016-01-01",     "2016-12-31",     true,              -632001,             "error.CP7.below.min",      "1 year AP FRS with below min value abridged"),

      ("2015-12-01",     "2016-05-31",     false,             0,                   "",                                        "6 month with leap year pre AP FRS with zero value NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              0,                   "",                                        "6 month with leap year pre AP FRS with zero value abridged"),
      ("2015-12-01",     "2016-05-31",     false,             1,                   "",                                        "6 month with leap year pre AP FRS with valid value NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              1,                   "",                                        "6 month with leap year pre AP FRS with valid value abridged"),
      ("2015-12-01",     "2016-05-31",     false,             316000,              "",                                        "6 month with leap year pre AP FRS with max value on threshold NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              316000,              "",                                        "6 month with leap year pre AP FRS with max value on threshold abridged"),
      ("2015-12-01",     "2016-05-31",     false,             -316000,             "",                                        "6 month with leap year pre AP FRS with min value on threshold NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              -316000,             "",                                        "6 month with leap year pre AP FRS with min value on threshold abridged"),
      ("2015-12-01",     "2016-05-31",     false,             316001,              "error.CP7.above.max",      "6 month with leap year pre AP FRS with above max value NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              316001,              "error.CP7.above.max",      "6 month with leap year pre AP FRS with above max value abridged"),
      ("2015-12-01",     "2016-05-31",     false,             -316001,             "error.CP7.below.min",      "6 month with leap year pre AP FRS with below min value NOT abridged"),
      ("2015-12-01",     "2016-05-31",     true,              -316001,             "error.CP7.below.min",      "6 month with leap year pre AP FRS with below min value abridged"),

      ("2020-01-01",     "2020-06-30",     false,             0,                   "",                                        "6 month with leap year AP post FRS with zero value NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              0,                   "",                                        "6 month with leap year AP post FRS with zero value abridged"),
      ("2020-01-01",     "2020-06-30",     false,             1,                   "",                                        "6 month with leap year AP post FRS with valid value NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              1,                   "",                                        "6 month with leap year AP post FRS with valid value abridged"),
      ("2020-01-01",     "2020-06-30",     false,             314273,              "",                                        "6 month with leap year AP post FRS with max value on threshold NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              314273,              "",                                        "6 month with leap year AP post FRS with max value on threshold abridged"),
      ("2020-01-01",     "2020-06-30",     false,             -314273,             "",                                        "6 month with leap year AP post FRS with min value on threshold NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              -314273,             "",                                        "6 month with leap year AP post FRS with min value on threshold abridged"),
      ("2020-01-01",     "2020-06-30",     false,             314274,              "error.CP7.above.max",      "6 month with leap year AP post FRS with above max value NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              314274,              "error.CP7.above.max",      "6 month with leap year AP post FRS with above max value abridged"),
      ("2020-01-01",     "2020-06-30",     false,             -314274,             "error.CP7.below.min",      "6 month with leap year AP post FRS with below min value NOT abridged"),
      ("2020-01-01",     "2020-06-30",     true,              -314274,             "error.CP7.below.min",      "6 month with leap year AP post FRS with below min value abridged"),

      ("2015-07-01",     "2015-12-31",     false,             0,                   "",                                        "6 month with non leap year pre AP FRS with zero value NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              0,                   "",                                        "6 month with non leap year pre AP FRS with zero value abridged"),
      ("2015-07-01",     "2015-12-31",     false,             1,                   "",                                        "6 month with non leap year pre AP FRS with valid value NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              1,                   "",                                        "6 month with non leap year pre AP FRS with valid value abridged"),
      ("2015-07-01",     "2015-12-31",     false,             318597,              "",                                        "6 month with non leap year pre AP FRS with max value on threshold NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              318597,              "",                                        "6 month with non leap year pre AP FRS with max value on threshold abridged"),
      ("2015-07-01",     "2015-12-31",     false,             -318597,             "",                                        "6 month with non leap year pre AP FRS with min value on threshold NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              -318597,             "",                                        "6 month with non leap year pre AP FRS with min value on threshold abridged"),
      ("2015-07-01",     "2015-12-31",     false,             318598,              "error.CP7.above.max",      "6 month with non leap year pre AP FRS with above max value NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              318598,              "error.CP7.above.max",      "6 month with non leap year pre AP FRS with above max value abridged"),
      ("2015-07-01",     "2015-12-31",     false,             -318598,             "error.CP7.below.min",      "6 month with non leap year pre AP FRS with below min value NOT abridged"),
      ("2015-07-01",     "2015-12-31",     true,              -318598,             "error.CP7.below.min",      "6 month with non leap year pre AP FRS with below min value abridged"),

      ("2017-07-01",     "2017-12-31",     false,             0,                   "",                                        "6 month with non leap year AP post FRS with zero value NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              0,                   "",                                        "6 month with non leap year AP post FRS with zero value abridged"),
      ("2017-07-01",     "2017-12-31",     false,             1,                   "",                                        "6 month with non leap year AP post FRS with valid value NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              1,                   "",                                        "6 month with non leap year AP post FRS with valid value abridged"),
      ("2017-07-01",     "2017-12-31",     false,             318597,              "",                                        "6 month with non leap year AP post FRS with max value on threshold NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              318597,              "",                                        "6 month with non leap year AP post FRS with max value on threshold abridged"),
      ("2017-07-01",     "2017-12-31",     false,             -318597,             "",                                        "6 month with non leap year AP post FRS with min value on threshold NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              -318597,             "",                                        "6 month with non leap year AP post FRS with min value on threshold abridged"),
      ("2017-07-01",     "2017-12-31",     false,             318598,              "error.CP7.above.max",      "6 month with non leap year AP post FRS with above max value NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              318598,              "error.CP7.above.max",      "6 month with non leap year AP post FRS with above max value abridged"),
      ("2017-07-01",     "2017-12-31",     false,             -318598,             "error.CP7.below.min",      "6 month with non leap year AP post FRS with below min value NOT abridged"),
      ("2017-07-01",     "2017-12-31",     true,              -318598,             "error.CP7.below.min",      "6 month with non leap year AP post FRS with below min value abridged"),

      ("2016-02-29",     "2016-12-31",     false,             0,                   "",                                        "short AP starting 29 Feb in leap year post FRS with zero value NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              0,                   "",                                        "short AP starting 29 Feb in leap year post FRS with zero value abridged"),
      ("2016-02-29",     "2016-12-31",     false,             1,                   "",                                        "short AP starting 29 Feb in leap year post FRS with valid value NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              1,                   "",                                        "short AP starting 29 Feb in leap year post FRS with valid value abridged"),
      ("2016-02-29",     "2016-12-31",     false,             530120,              "",                                        "short AP starting 29 Feb in leap year post FRS with max value on threshold NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              530120,              "",                                        "short AP starting 29 Feb in leap year post FRS with max value on threshold abridged"),
      ("2016-02-29",     "2016-12-31",     false,             -530120,             "",                                        "short AP starting 29 Feb in leap year post FRS with min value on threshold NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              -530120,             "",                                        "short AP starting 29 Feb in leap year post FRS with min value on threshold abridged"),
      ("2016-02-29",     "2016-12-31",     false,             530121,              "error.CP7.above.max",      "short AP starting 29 Feb in leap year post FRS with above max value NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              530121,              "error.CP7.above.max",      "short AP starting 29 Feb in leap year post FRS with above max value abridged"),
      ("2016-02-29",     "2016-12-31",     false,             -530121,             "error.CP7.below.min",      "short AP starting 29 Feb in leap year post FRS with below min value NOT abridged"),
      ("2016-02-29",     "2016-12-31",     true,              -530121,             "error.CP7.below.min",      "short AP starting 29 Feb in leap year post FRS with below min value abridged"),

      ("2019-06-01",     "2020-05-31",     false,             0,                   "",                                        "1 year AP ending in leap year post FRS with zero value NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              0,                   "",                                        "1 year AP ending in leap year post FRS with zero value abridged"),
      ("2019-06-01",     "2020-05-31",     false,             1,                   "",                                        "1 year AP ending in leap year post FRS with valid value NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              1,                   "",                                        "1 year AP ending in leap year post FRS with valid value abridged"),
      ("2019-06-01",     "2020-05-31",     false,             632000,              "",                                        "1 year AP ending in leap year post FRS with max value on threshold NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              632000,              "",                                        "1 year AP ending in leap year post FRS with max value on threshold abridged"),
      ("2019-06-01",     "2020-05-31",     false,             -632000,             "",                                        "1 year AP ending in leap year post FRS with min value on threshold NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              -632000,             "",                                        "1 year AP ending in leap year post FRS with min value on threshold abridged"),
      ("2019-06-01",     "2020-05-31",     false,             632001,              "error.CP7.above.max",      "1 year AP ending in leap year post FRS with above max value NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              632001,              "error.CP7.above.max",      "1 year AP ending in leap year post FRS with above max value abridged"),
      ("2019-06-01",     "2020-05-31",     false,             -632001,             "error.CP7.below.min",      "1 year AP ending in leap year post FRS with below min value NOT abridged"),
      ("2019-06-01",     "2020-05-31",     true,              -632001,             "error.CP7.below.min",      "1 year AP ending in leap year post FRS with below min value abridged")
    )

    executeTests(isHmrcFiling = isHmrcFiling, isCoHoFiling = isCoHoFiling)(CompanyTypes.AllCompanyTypes -- CompanyTypes.AllCharityTypes)(testTable)
  }

  def executeTests(isHmrcFiling: Boolean, isCoHoFiling: Boolean)(companyTypes: Set[CompanyType])(table: TableFor6[String, String, Boolean, Int, String, String]): Unit = {
    companyTypes.foreach { companyType =>

      s"testing validation for $companyType" when {
        forAll(table) { (startDateString: String, endDateString: String, abridgedFiling: Boolean, cp7Value: Int, expectedErrorKey: String, message: String) =>
          val boxRetriever = mock[TestComputationsBoxRetriever]

          when(boxRetriever.cp1()).thenReturn(CP1(new LocalDate(startDateString)))
          when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate(endDateString)))
          when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(isHmrcFiling))
          when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(isCoHoFiling))
          when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(abridgedFiling))
          when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))

          s"$message : $cp7Value" in {
            val validationResult = CP7(Some(cp7Value)).validate(boxRetriever)
            if (expectedErrorKey.isEmpty) {
              withClue(s"HMRC: $isHmrcFiling, CoHo: $isCoHoFiling ::: $message")(validationResult shouldBe empty)
            }
            else {
              val error = validationResult.find { error =>
                error.boxId.contains("CP7") && error.errorMessageKey == expectedErrorKey && error.args.map { args => args.size == 2}.getOrElse(false)
              }
              withClue(s"HMRC: $isHmrcFiling, CoHo: $isCoHoFiling ::: $message : $validationResult"){error should not be empty}
            }
          }

        }
      }
    }
  }
}

case class CP7Holder(cp7: CP7)

trait TestComputationsBoxRetriever extends ComputationsBoxRetriever with FilingAttributesBoxValueRetriever
