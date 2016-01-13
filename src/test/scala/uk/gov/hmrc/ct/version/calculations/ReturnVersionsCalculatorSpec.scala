/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.version.calculations

import org.joda.time.LocalDate
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.stubs.StubbedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.stubs.StubbedFilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever
import uk.gov.hmrc.ct.domain.CompanyTypes.{MembersClub, LimitedByGuaranteeCharity, Charity, UkTradingCompany}
import uk.gov.hmrc.ct.version.CoHoAccounts.{CoHoMicroEntityAbridgedAccounts, CoHoMicroEntityAccounts, CoHoStatutoryAbbreviatedAccounts, CoHoStatutoryAccounts}
import uk.gov.hmrc.ct.version.CoHoVersions.AccountsVersion1
import uk.gov.hmrc.ct.version.HmrcReturns._
import uk.gov.hmrc.ct.version.HmrcVersions._
import uk.gov.hmrc.ct.version.{Return, Version}

class ReturnVersionsCalculatorSpec extends WordSpec with Matchers {

  "Return Versions Calculator" should {
    "for CoHo only filing" when {
      "return accounts version for full Micro entity accounts" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAccounts, AccountsVersion1))
        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe expectedResult
      }

      "return accounts version for abridged Micro entity accounts" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAbridgedAccounts, AccountsVersion1))
        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true)) shouldBe expectedResult
      }

      "return accounts version for full statutory accounts" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAccounts, AccountsVersion1))
        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe expectedResult
      }

      "return accounts version for abbreviated statutory accounts" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAbbreviatedAccounts, AccountsVersion1))
        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe expectedResult
      }

      "return accounts version for abbreviated statutory accounts for LimitedByGuaranteeCharity" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAbbreviatedAccounts, AccountsVersion1))
        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity)) shouldBe expectedResult
      }

      "return accounts version for full statutory accounts for LimitedByGuaranteeCharity" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAccounts, AccountsVersion1))
        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(false),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity)) shouldBe expectedResult
      }

      "return accounts version for abridged micro entity accounts for LimitedByGuaranteeCharity" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAbridgedAccounts, AccountsVersion1))
        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity)) shouldBe expectedResult
      }

      "return accounts version for micro entity accounts for LimitedByGuaranteeCharity" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAccounts, AccountsVersion1))
        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(false),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity)) shouldBe expectedResult
      }

      "match successfully for AccountsBoxRetriever" in {
        val expectedResult = Set(Return(CoHoMicroEntityAccounts, AccountsVersion1))

        val accountsBoxRetriever = new StubbedAccountsBoxRetriever with StubbedFilingAttributesBoxValueRetriever {

          override def retrieveAbbreviatedAccountsFiling(): AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)
          override def retrieveStatutoryAccountsFiling(): StatutoryAccountsFiling = StatutoryAccountsFiling(false)
          override def retrieveMicroEntityFiling(): MicroEntityFiling = MicroEntityFiling(true)
          override def retrieveCompanyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)
          override def retrieveAbridgedFiling(): AbridgedFiling = AbridgedFiling(false)
          override def retrieveCompaniesHouseFiling(): CompaniesHouseFiling = CompaniesHouseFiling(true)
          override def retrieveHMRCFiling(): HMRCFiling = HMRCFiling(false)
        }

        ReturnVersionsCalculator.doCalculation(accountsBoxRetriever) shouldBe expectedResult
      }

      "match successfully for ComputationsBoxRetriever" in {
        val expectedResult = Set(Return(CT600j, CT600Version2),
                                 Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600a, CT600Version2),
                                 Return(CoHoMicroEntityAccounts, AccountsVersion1),
                                 Return(Computations, ComputationsCT20141001),
                                 Return(CT600, CT600Version2))

        ReturnVersionsCalculator.doCalculation(new ComputationsBoxRetrieverForTest with StubbedAccountsBoxRetriever {

        }) shouldBe expectedResult
      }
    }

    "for HMRC only filing" when {
      "return HMRC version for full Micro entity accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe expectedResult
      }
      "return HMRC version for full Micro entity accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for abridged Micro entity accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for abridged Micro entity accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for full statutory accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for full statutory accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe expectedResult
      }

      "return HMRC versions for abbreviated statutory accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe expectedResult
      }

      "return HMRC versions for abbreviated statutory accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe expectedResult
      }

      "return HMRC versions for uploaded accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(false),
                                abridgedFiling = AbridgedFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(false),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)) shouldBe expectedResult
      }


      "return HMRC versions for uploaded accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(false),
                                abridgedFiling = AbridgedFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(false),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)) shouldBe expectedResult
      }
    }

    val computationsVersions = org.scalatest.prop.Tables.Table (

      ("startDate", "endDate", "compsVersion"),
      ("2012-04-02", "2013-03-31", ComputationsCT20130721),
      ("2012-04-01", "2013-03-31", ComputationsCT20130721),
      ("2012-04-01", "2012-04-01", ComputationsCT20130721),
      ("2010-04-01", "2011-03-31", ComputationsCT20130721),
      ("2008-04-01", "2008-04-01", ComputationsCT20130721),

      ("2015-03-31", "2016-03-30", ComputationsCT20141001),
      ("2015-01-01", "2015-12-31", ComputationsCT20141001),
      ("2012-04-02", "2013-04-01", ComputationsCT20141001),

      ("2016-01-01", "2016-12-31", ComputationsCT20150201),
      ("2015-04-01", "2016-03-31", ComputationsCT20150201)
    )

    "for any HMRC filing" when {
      "return Computations version" in new ReturnVersionsCalculator {
        forAll(computationsVersions) {
          (start: String, end: String, version: Version) => {
            val versions = calculateReturnVersions(apStartDate = Some(LocalDate.parse(start)),
                                                   apEndDate = Some(LocalDate.parse(end)),
                                                   hmrcFiling = HMRCFiling(true))
            versions.find( v => v.submission == Computations).get.version shouldBe version
          }
        }
      }
    }

    "for Non company charity filing" when {
      "return versions for all proceeds used for charitable purposes for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version2))

        private val apStartDate = Some(LocalDate.parse("2015-03-31"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                companyType = FilingCompanyType(Charity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }
      "return versions for all proceeds used for charitable purposes for AP starting on or after 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version3))

        private val apStartDate = Some(LocalDate.parse("2015-04-01"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                companyType = FilingCompanyType(Charity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }
      "return versions when NOT all proceeds used for charitable purposes for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        private val apStartDate = Some(LocalDate.parse("2015-03-31"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                companyType = FilingCompanyType(Charity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }
      "return versions when NOT all proceeds used for charitable purposes for AP starting on or after 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        private val apStartDate = Some(LocalDate.parse("2015-04-01"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                companyType = FilingCompanyType(Charity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }
    }
    "for Member Club filing" when {
      "return versions for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        private val apStartDate = Some(LocalDate.parse("2015-03-31"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                companyType = FilingCompanyType(MembersClub)) shouldBe expectedResult
      }
      "return versions for AP starting on or after 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        private val apStartDate = Some(LocalDate.parse("2015-04-01"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                companyType = FilingCompanyType(MembersClub)) shouldBe expectedResult
      }
    }

    "for HMRC only filing for a Company (limited by guarantee) that is a charity" when {
      "return versions where all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version2))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version3))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "return versions for micro entity where all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version2))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions for micro entity where all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version3))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions for micro entity where NOT all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "return versions for micro entity where NOT all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }
    }

    "for Joint filing for a Company (limited by guarantee) that is a charity with full accounts" when {
      "return versions where all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAccounts, AccountsVersion1),
                                 Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version2))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAccounts, AccountsVersion1),
                                 Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version3))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAccounts, AccountsVersion1),
                                 Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAccounts, AccountsVersion1),
                                 Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }
    }

    "for Joint filing for a Company (limited by guarantee) that is a charity with micro entity accounts" when {
      "return versions where all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAccounts, AccountsVersion1),
                                 Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version2))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAccounts, AccountsVersion1),
                                 Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version3))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAccounts, AccountsVersion1),
                                 Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAccounts, AccountsVersion1),
                                 Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }
    }

    "for Joint filing for a Company (limited by guarantee) that is a charity with abbreviated accounts" when {
      "return versions where all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAbbreviatedAccounts, AccountsVersion1),
                                 Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version2))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAbbreviatedAccounts, AccountsVersion1),
                                 Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version3))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAbbreviatedAccounts, AccountsVersion1),
                                 Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoStatutoryAbbreviatedAccounts, AccountsVersion1),
                                 Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }
    }

    "for Joint filing for a Company (limited by guarantee) that is a charity with abridged micro entity accounts" when {
      "return versions where all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAbridgedAccounts, AccountsVersion1),
                                 Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version2))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAbridgedAccounts, AccountsVersion1),
                                 Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600e, CT600Version3))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAbridgedAccounts, AccountsVersion1),
                                 Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(CoHoMicroEntityAbridgedAccounts, AccountsVersion1),
                                 Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }
    }

    "for Joint filing" when {
      "return versions for full Micro entity accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CoHoMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe expectedResult
      }
      "return versions for full Micro entity accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CoHoMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe expectedResult
      }

      "return versions for abridged Micro entity accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CoHoMicroEntityAbridgedAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for abridged Micro entity accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CoHoMicroEntityAbridgedAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for full statutory accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CoHoStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for full statutory accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CoHoStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for abbreviated statutory accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CoHoStatutoryAbbreviatedAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for abbreviated statutory accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CoHoStatutoryAbbreviatedAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe expectedResult
      }
    }
  }
}

class ComputationsBoxRetrieverForTest extends StubbedComputationsBoxRetriever with StubbedFilingAttributesBoxValueRetriever {

  self: AccountsBoxRetriever =>

  override def retrieveCP1(): CP1 = CP1(LocalDate.parse("2015-03-31"))

  override def retrieveCP2(): CP2 = CP2(LocalDate.parse("2015-12-31"))

  override def retrieveCompanyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)

  override def retrieveAbbreviatedAccountsFiling(): AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)

  override def retrieveStatutoryAccountsFiling(): StatutoryAccountsFiling = StatutoryAccountsFiling(false)

  override def retrieveMicroEntityFiling(): MicroEntityFiling = MicroEntityFiling(true)

  override def retrieveAbridgedFiling(): AbridgedFiling = AbridgedFiling(false)

  override def retrieveCompaniesHouseFiling(): CompaniesHouseFiling = CompaniesHouseFiling(true)

  override def retrieveHMRCFiling(): HMRCFiling = HMRCFiling(true)

  override def retrieveHMRCAmendment(): HMRCAmendment = HMRCAmendment(false)
}
