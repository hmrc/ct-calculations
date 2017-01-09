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

package uk.gov.hmrc.ct.version.calculations

import org.joda.time.LocalDate
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.accounts.{AC2, AC205, AC206, AC3}
import uk.gov.hmrc.ct.accounts.frsse2008._
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frsse2008.stubs.StubbedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.stubs.StubbedFilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever
import uk.gov.hmrc.ct.domain.CompanyTypes._
import uk.gov.hmrc.ct.version.CoHoAccounts._
import uk.gov.hmrc.ct.version.CoHoVersions.{FRS102, FRS105, FRSSE2008}
import uk.gov.hmrc.ct.version.HmrcReturns._
import uk.gov.hmrc.ct.version.HmrcVersions._
import uk.gov.hmrc.ct.version.calculations.ReturnVersionsFixture.coHoOnlyMicroEntityFRSSE2008Returns
import uk.gov.hmrc.ct.version.{Return, Version}

class ReturnVersionsCalculatorSpec extends WordSpec with Matchers {

  import ReturnVersionsFixture._

  "Return Versions Calculator" should {
    "for CoHo only filing" when {
      "return accounts version for full Micro entity accounts" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe coHoOnlyMicroEntityFRSSE2008Returns
      }

      "return accounts version for abridged Micro entity accounts" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true)) shouldBe coHoOnlyAbridgedMicroEntityFRSSE2008Returns
      }

      "return accounts version for full statutory accounts" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe coHoOnlyStatutoryFRSSE2008Returns
      }

      "return accounts version for abbreviated statutory accounts" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe coHoOnlyAbbreviatedStatutoryFRSSE2008Returns
      }

      "return accounts version for abbreviated statutory accounts for LimitedByGuaranteeCharity" in new ReturnVersionsCalculatorWithDefaults {
        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity)) shouldBe coHoOnlyAbbreviatedStatutoryFRSSE2008Returns
      }

      "return accounts version for full statutory accounts for LimitedByGuaranteeCharity" in new ReturnVersionsCalculatorWithDefaults {
        calculateReturnVersions(coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(false),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity)) shouldBe coHoOnlyStatutoryFRSSE2008Returns
      }

      "return accounts version for Micro entity accounts with POA start after FRS102 epoch" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(
          poaStartDate = new LocalDate(2016, 1, 1),
          coHoFiling = CompaniesHouseFiling(true),
          microEntityFiling = MicroEntityFiling(true)
        ) shouldBe coHoOnlyFRS105Returns
      }

      "return accounts version for abridged statutory accounts with POA start after FRS102 epoch" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(
          poaStartDate = new LocalDate(2016, 1, 2),
          coHoFiling = CompaniesHouseFiling(true),
          abridgedFiling = AbridgedFiling(true)
        ) shouldBe coHoOnlyAbridgedFRS102Returns
      }

      "return accounts version for full statutory accounts with POA start after FRS102 epoch" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(
          poaStartDate = new LocalDate(2016, 1, 3),
          coHoFiling = CompaniesHouseFiling(true),
          statutoryAccountsFiling = StatutoryAccountsFiling(true)
        ) shouldBe coHoOnlyFullFRS102Returns
      }

      "match successfully for AccountsBoxRetriever" in {

        val accountsBoxRetriever = new StubbedAccountsBoxRetriever with StubbedFilingAttributesBoxValueRetriever {

          override def abbreviatedAccountsFiling(): AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)
          override def statutoryAccountsFiling(): StatutoryAccountsFiling = StatutoryAccountsFiling(false)
          override def microEntityFiling(): MicroEntityFiling = MicroEntityFiling(true)
          override def companyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)
          override def abridgedFiling(): AbridgedFiling = AbridgedFiling(false)
          override def companiesHouseFiling(): CompaniesHouseFiling = CompaniesHouseFiling(true)
          override def hmrcFiling(): HMRCFiling = HMRCFiling(false)
          override def countryOfRegistration(): CountryOfRegistration = CountryOfRegistration(Some("EW"))
          override def ac3(): AC3 = AC3(new LocalDate(2015,3,30))
          override def ac2(): AC2 = AC2(Some("Random company name"))
        }

        ReturnVersionsCalculator.doCalculation(accountsBoxRetriever) shouldBe coHoOnlyMicroEntityFRSSE2008Returns
      }

      "match successfully for ComputationsBoxRetriever" in {

        ReturnVersionsCalculator.doCalculation(new ComputationsBoxRetrieverForTest with StubbedAccountsBoxRetriever {
          override def ac3(): AC3 = AC3(new LocalDate(2015,3,30))
        }) shouldBe jointMicroFRSSE2008V2Returns
      }
    }

    "for HMRC only filing" when {
      "return HMRC version for full Micro entity accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe hmrcOnlyMicroFRSSE2008V2Returns
      }
      "return HMRC version for full Micro entity accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {


        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe hmrcOnlyMicroFRSSE2008V3Returns
      }

      "return HMRC version for abridged Micro entity accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true)) shouldBe hmrcOnlyMicroFRSSE2008V2Returns
      }

      "return HMRC version for abridged Micro entity accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true)) shouldBe hmrcOnlyMicroFRSSE2008V3Returns
      }

      "return HMRC version for full statutory accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe hmrcOnlyStatutoryFRSSE2008V2Returns
      }

      "return HMRC version for full statutory accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe hmrcOnlyStatutoryFRSSE2008V3Returns
      }

      "return HMRC versions for abbreviated statutory accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe hmrcOnlyStatutoryFRSSE2008V2Returns
      }

      "return HMRC versions for abbreviated statutory accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe hmrcOnlyStatutoryFRSSE2008V3Returns
      }

      "return HMRC versions for uploaded accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(false),
                                abridgedFiling = AbridgedFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(false),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)) shouldBe hmrcOnlyUploadAccountsV2Returns
      }

      "return HMRC versions for uploaded accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(false),
                                abridgedFiling = AbridgedFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(false),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)) shouldBe hmrcOnlyUploadAccountsV3Returns
      }

      "return HMRC version for micro accounts with POA start after FRS102 epoch" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(
          poaStartDate = new LocalDate(2016, 1, 1),
          apStartDate = Some(LocalDate.parse("2016-01-01")),
          apEndDate = Some(LocalDate.parse("2016-01-01")),
          hmrcFiling = HMRCFiling(true),
          microEntityFiling = MicroEntityFiling(true)) shouldBe hmrcOnlyMicroFRS105V3Returns
      }

      "return HMRC version for abridged accounts with POA start after FRS102 epoch" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(
          poaStartDate = new LocalDate(2016, 1, 1),
          apStartDate = Some(LocalDate.parse("2016-01-01")),
          apEndDate = Some(LocalDate.parse("2016-01-01")),
          hmrcFiling = HMRCFiling(true),
          abridgedFiling = AbridgedFiling(true)) shouldBe hmrcOnlyAbridgedFRS102V3Returns
      }

      "return HMRC version for full statutory accounts with POA start after FRS102 epoch" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(
          poaStartDate = new LocalDate(2016, 1, 3),
          apStartDate = Some(LocalDate.parse("2016-01-03")),
          apEndDate = Some(LocalDate.parse("2016-01-03")),
          hmrcFiling = HMRCFiling(true),
          statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe hmrcOnlyStatutoryFRS102V3Returns
      }

      "return HMRC versions for uploaded accounts with POA start after FRS102 epoch" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(
          poaStartDate = new LocalDate(2016, 1, 2),
          apStartDate = Some(LocalDate.parse("2016-01-02")),
          apEndDate = Some(LocalDate.parse("2016-01-02")),
          hmrcFiling = HMRCFiling(true),
          microEntityFiling = MicroEntityFiling(false),
          abridgedFiling = AbridgedFiling(false),
          statutoryAccountsFiling = StatutoryAccountsFiling(false),
          abbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)) shouldBe hmrcOnlyUploadAccountsV3Returns
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
      "return Computations version" in new ReturnVersionsCalculatorWithDefaults {
        forAll(computationsVersions) {
          (start: String, end: String, version: Version) => {
            val versions = calculateReturnVersions(apStartDate = Some(LocalDate.parse(start)),
                                                   apEndDate = Some(LocalDate.parse(end)),
                                                   hmrcFiling = HMRCFiling(true))
            versions.find( v => v.submission == Computations).get.version shouldBe version
          }
        }
      }
      "return CT600A return for UK Trading Company V2" in new ReturnVersionsCalculatorWithDefaults {
            val versions = calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-01-01")),
                                                   apEndDate = Some(LocalDate.parse("2015-12-31")),
                                                   hmrcFiling = HMRCFiling(true),
                                                   companyType = FilingCompanyType(UkTradingCompany))
            versions.find( v => v.submission == CT600a).get.version shouldBe CT600Version2
      }
      "NOT return CT600A return for a limited by guarantee company V2" in new ReturnVersionsCalculatorWithDefaults {
        val versions = calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-01-01")),
                                               apEndDate = Some(LocalDate.parse("2015-12-31")),
                                               hmrcFiling = HMRCFiling(true),
                                               companyType = FilingCompanyType(CompanyLimitedByGuarantee))

        versions.find( v => v.submission == CT600a) shouldBe empty
      }
      "return CT600A return for UK Trading Company V3" in new ReturnVersionsCalculatorWithDefaults {
        val versions = calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                                hmrcFiling = HMRCFiling(true),
                                                companyType = FilingCompanyType(UkTradingCompany))

        versions.find( v => v.submission == CT600a).get.version shouldBe CT600Version3
      }
      "NOT return CT600A return for a limited by guarantee company V3" in new ReturnVersionsCalculatorWithDefaults {
        val versions = calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                                hmrcFiling = HMRCFiling(true),
                                                companyType = FilingCompanyType(CompanyLimitedByGuarantee))

        versions.find( v => v.submission == CT600a) shouldBe empty
      }
    }

    "for Member Club filing" when {
      "return versions for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
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
      "return versions for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
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

    "for Non company charity filing" when {
      "return versions for all proceeds used for charitable purposes for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2))

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
      "return versions for all proceeds used for charitable purposes for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3))

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
      "return versions when NOT all proceeds used for charitable purposes for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
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
      "return versions when NOT all proceeds used for charitable purposes for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
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
      "return versions when no income for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2))

        private val apStartDate = Some(LocalDate.parse("2015-03-31"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                companyType = FilingCompanyType(Charity),
                                charityAllExempt = None,
                                charityNoIncome = Some(true)) shouldBe expectedResult
      }
      "return versions when no income for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3))

        private val apStartDate = Some(LocalDate.parse("2015-04-01"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                companyType = FilingCompanyType(Charity),
                                charityAllExempt = None,
                                charityNoIncome = Some(true)) shouldBe expectedResult
      }
      "return versions when not claiming for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
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
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
      "return versions when not claiming for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
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
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
    }

    "for Non company CASC filing" when {
      "return versions for all proceeds used for charitable purposes for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2))

        private val apStartDate = Some(LocalDate.parse("2015-03-31"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                companyType = FilingCompanyType(CASC),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }
      "return versions for all proceeds used for charitable purposes for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3))

        private val apStartDate = Some(LocalDate.parse("2015-04-01"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                companyType = FilingCompanyType(CASC),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }
      "return versions when NOT all proceeds used for charitable purposes for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
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
                                companyType = FilingCompanyType(CASC),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }
      "return versions when NOT all proceeds used for charitable purposes for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
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
                                companyType = FilingCompanyType(CASC),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }
      "return versions when no income for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2))

        private val apStartDate = Some(LocalDate.parse("2015-03-31"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                companyType = FilingCompanyType(CASC),
                                charityAllExempt = None,
                                charityNoIncome = Some(true)) shouldBe expectedResult
      }
      "return versions when no income for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3))

        private val apStartDate = Some(LocalDate.parse("2015-04-01"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                companyType = FilingCompanyType(CASC),
                                charityAllExempt = None,
                                charityNoIncome = Some(true)) shouldBe expectedResult
      }
      "return versions when not claiming for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
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
                                companyType = FilingCompanyType(CASC),
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
      "return versions when not claiming for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
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
                                companyType = FilingCompanyType(CASC),
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
    }

    "for HMRC only filing for a Company (limited by guarantee) that is a charity" when {
      "return versions where all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
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

      "return versions where NOT all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
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

      "throw illegal argument exception for micro entity charity for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        intercept[IllegalArgumentException](
          calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                  apEndDate = Some(LocalDate.parse("2015-12-31")),
                                  hmrcFiling = HMRCFiling(true),
                                  coHoFiling = CompaniesHouseFiling(false),
                                  microEntityFiling = MicroEntityFiling(true),
                                  companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                  charityAllExempt = Some(true))
        )
      }
      "return versions when not claiming for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
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
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
      "return versions when not claiming for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
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
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
    }

    "for HMRC only filing for a Company (limited by guarantee) that is a CASC" when {
      "return versions where all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCASC),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCASC),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600, CT600Version2),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCASC),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600, CT600Version3),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCASC),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "throw illegal argument exception for micro entity charity for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        intercept[IllegalArgumentException](
          calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                  apEndDate = Some(LocalDate.parse("2015-12-31")),
                                  hmrcFiling = HMRCFiling(true),
                                  coHoFiling = CompaniesHouseFiling(false),
                                  microEntityFiling = MicroEntityFiling(true),
                                  companyType = FilingCompanyType(LimitedByGuaranteeCASC),
                                  charityAllExempt = Some(true))
        )
      }
      "return versions when not claiming for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
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
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCASC),
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
      "return versions when not claiming for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
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
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCASC),
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
    }

    "for HMRC only filing for a Company (limited by shares) that is a charity" when {
      "return versions where all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCharity),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCharity),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "throw illegal argument exception for micro entity charity" in new ReturnVersionsCalculatorWithDefaults {
        intercept[IllegalArgumentException](
          calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                  apEndDate = Some(LocalDate.parse("2015-12-31")),
                                  hmrcFiling = HMRCFiling(true),
                                  coHoFiling = CompaniesHouseFiling(false),
                                  microEntityFiling = MicroEntityFiling(true),
                                  companyType = FilingCompanyType(LimitedBySharesCharity),
                                  charityAllExempt = Some(true))
        )
      }

      "return versions when not claiming for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        private val apStartDate = Some(LocalDate.parse("2015-03-31"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCharity),
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
      "return versions when not claiming for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        private val apStartDate = Some(LocalDate.parse("2015-04-01"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCharity),
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
    }

    "for HMRC only filing for a Company (limited by shares) that is a CASC" when {
      "return versions where all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCASC),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCASC),
                                charityAllExempt = Some(true)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600e, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCASC),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "return versions where NOT all proceeds used for charity for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600e, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCASC),
                                charityAllExempt = Some(false)) shouldBe expectedResult
      }

      "throw illegal argument exception for micro entity charity" in new ReturnVersionsCalculatorWithDefaults {
        intercept[IllegalArgumentException](
          calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                  apEndDate = Some(LocalDate.parse("2015-12-31")),
                                  hmrcFiling = HMRCFiling(true),
                                  coHoFiling = CompaniesHouseFiling(false),
                                  microEntityFiling = MicroEntityFiling(true),
                                  companyType = FilingCompanyType(LimitedBySharesCASC),
                                  charityAllExempt = Some(true))
        )
      }

      "return versions when not claiming for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        private val apStartDate = Some(LocalDate.parse("2015-03-31"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCASC),
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
      "return versions when not claiming for AP starting on or after 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        private val apStartDate = Some(LocalDate.parse("2015-04-01"))
        private val apEndDate = Some(LocalDate.parse("2015-12-31"))

        calculateReturnVersions(apStartDate = apStartDate,
                                apEndDate = apEndDate,
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(false),
                                microEntityFiling = MicroEntityFiling(false),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCASC),
                                charityAllExempt = None,
                                charityNoIncome = None) shouldBe expectedResult
      }
    }

    "for Joint filing for a Company (limited by guarantee) that is a charity throws illegal state exception" in new ReturnVersionsCalculatorWithDefaults {

      intercept[IllegalArgumentException](
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedByGuaranteeCharity),
                                charityAllExempt = Some(true))
      )
    }

    "for Joint filing for a Company (limited by share) that is a charity throws illegal state exception" in new ReturnVersionsCalculatorWithDefaults {

      intercept[IllegalArgumentException](
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true),
                                companyType = FilingCompanyType(LimitedBySharesCharity),
                                charityAllExempt = Some(true))
      )
    }

    "for Joint filing for a charity throws illegal state exception" in new ReturnVersionsCalculatorWithDefaults {

      intercept[IllegalArgumentException](
        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true),
                                companyType = FilingCompanyType(Charity),
                                charityAllExempt = Some(true))
      )
    }

    "for Joint filing" when {
      "return versions for full Micro entity accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe jointMicroFRSSE2008V2Returns
      }
      "return versions for full Micro entity accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe jointMicroFRSSE2008V3Returns
      }

      "return versions for abridged Micro entity accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true)) shouldBe jointAbridgedMicroFRSSE2008V2Returns
      }

      "return HMRC version for abridged Micro entity accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                microEntityFiling = MicroEntityFiling(true),
                                abridgedFiling = AbridgedFiling(true)) shouldBe jointAbridgedMicroFRSSE2008V3Returns
      }

      "return HMRC version for full statutory accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe jointStatutoryFRSSE2008V2Returns
      }

      "return HMRC version for full statutory accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe jointStatutoryFRSSE2008V3Returns
      }

      "return HMRC version for abbreviated statutory accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-03-31")),
                                apEndDate = Some(LocalDate.parse("2015-12-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe jointAbbreviatedStatutoryFRSSE2008V2Returns
      }

      "return HMRC version for abbreviated statutory accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculatorWithDefaults {

        calculateReturnVersions(apStartDate = Some(LocalDate.parse("2015-04-01")),
                                apEndDate = Some(LocalDate.parse("2016-03-31")),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe jointAbbreviatedStatutoryFRSSE2008V3Returns
      }
    }
  }
}

class ReturnVersionsCalculatorWithDefaults extends  ReturnVersionsCalculator {
  override def calculateReturnVersions(
                                        poaStartDate: LocalDate = new LocalDate(2015, 3, 30),
                                        apStartDate: Option[LocalDate] = None,
                                        apEndDate: Option[LocalDate] = None,
                                        coHoFiling: CompaniesHouseFiling = CompaniesHouseFiling(false),
                                        hmrcFiling: HMRCFiling = HMRCFiling(false),
                                        microEntityFiling: MicroEntityFiling = MicroEntityFiling(false),
                                        statutoryAccountsFiling: StatutoryAccountsFiling = StatutoryAccountsFiling(false),
                                        abridgedFiling: AbridgedFiling = AbridgedFiling(false),
                                        abbreviatedAccountsFiling: AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false),
                                        companyType: FilingCompanyType = FilingCompanyType(UkTradingCompany),
                                        charityAllExempt: Option[Boolean] = None,
                                        charityNoIncome: Option[Boolean] = None): Set[Return] = {

    super.calculateReturnVersions(
      poaStartDate,
      apStartDate,
      apEndDate,
      coHoFiling,
      hmrcFiling,
      microEntityFiling,
      statutoryAccountsFiling,
      abridgedFiling,
      abbreviatedAccountsFiling,
      companyType,
      charityAllExempt,
      charityNoIncome
    )
  }
}

class ComputationsBoxRetrieverForTest extends StubbedComputationsBoxRetriever with StubbedFilingAttributesBoxValueRetriever {

  self: Frsse2008AccountsBoxRetriever =>

  override def cp1(): CP1 = CP1(LocalDate.parse("2015-03-31"))

  override def cp2(): CP2 = CP2(LocalDate.parse("2015-12-31"))

  override def companyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)

  override def abbreviatedAccountsFiling(): AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)

  override def statutoryAccountsFiling(): StatutoryAccountsFiling = StatutoryAccountsFiling(false)

  override def microEntityFiling(): MicroEntityFiling = MicroEntityFiling(true)

  override def abridgedFiling(): AbridgedFiling = AbridgedFiling(false)

  override def companiesHouseFiling(): CompaniesHouseFiling = CompaniesHouseFiling(true)

  override def hmrcFiling(): HMRCFiling = HMRCFiling(true)

  override def hmrcAmendment(): HMRCAmendment = HMRCAmendment(false)

  override def countryOfRegistration(): CountryOfRegistration = CountryOfRegistration.EnglandWales

  override def ac205(): AC205 = ???

  override def ac206(): AC206 = ???

  override def ac2(): AC2 = ???
}
