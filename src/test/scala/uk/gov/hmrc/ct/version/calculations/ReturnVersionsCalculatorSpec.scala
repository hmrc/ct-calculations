/*
 * Copyright 2015 HM Revenue & Customs
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
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
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

      "match successfully for AccountsBoxRetriever" in {
        val expectedResult = Set(Return(CoHoMicroEntityAccounts, AccountsVersion1))

        val accountsBoxRetriever = new AccountsBoxRetriever with FilingAttributesBoxValueRetriever {

          override def generateValues: Map[String, CtValue[_]] = ???

          override def retrieveAC4(): AC4 = ???
          override def retrieveAC1(): AC1 = ???
          override def retrieveAC206(): AC206 = ???
          override def retrieveAC3(): AC3 = ???
          override def retrieveAC205(): AC205 = ???

          override def retrieveProductName(): ProductName = ???
          override def retrieveCompanyType(): FilingCompanyType = ???
          override def retrieveAbbreviatedAccountsFiling(): AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)
          override def retrieveStatutoryAccountsFiling(): StatutoryAccountsFiling = StatutoryAccountsFiling(false)
          override def retrieveMicroEntityFiling(): MicroEntityFiling = MicroEntityFiling(true)
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

        ReturnVersionsCalculator.doCalculation(new ComputationsBoxRetrieverForTest) shouldBe expectedResult
      }
    }

    "for HMRC only filing" when {
      "return HMRC version for full Micro entity accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))

        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-03-31"))),
                                cp2 = Some(CP2(LocalDate.parse("2015-12-31"))),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe expectedResult
      }
      "return HMRC version for full Micro entity accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))

        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-04-01"))),
                                cp2 = Some(CP2(LocalDate.parse("2016-03-31"))),
                                hmrcFiling = HMRCFiling(true),
                                microEntityFiling = MicroEntityFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for abridged Micro entity accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcMicroEntityAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-03-31"))),
                                cp2 = Some(CP2(LocalDate.parse("2015-12-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-04-01"))),
                                cp2 = Some(CP2(LocalDate.parse("2016-03-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-03-31"))),
                                cp2 = Some(CP2(LocalDate.parse("2015-12-31"))),
                                hmrcFiling = HMRCFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe expectedResult
      }

      "return HMRC version for full statutory accounts for AP starting after 2015-03-31" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version3),
                                 Return(CT600a, CT600Version3),
                                 Return(CT600j, CT600Version3),
                                 Return(Computations, ComputationsCT20150201))
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-04-01"))),
                                cp2 = Some(CP2(LocalDate.parse("2016-03-31"))),
                                hmrcFiling = HMRCFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true)) shouldBe expectedResult
      }

      "return HMRC versions for abbreviated statutory accounts for AP starting before 2015-04-01" in new ReturnVersionsCalculator {
        val expectedResult = Set(Return(HmrcStatutoryAccounts, AccountsVersion1),
                                 Return(CT600, CT600Version2),
                                 Return(CT600a, CT600Version2),
                                 Return(CT600j, CT600Version2),
                                 Return(Computations, ComputationsCT20141001))
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-03-31"))),
                                cp2 = Some(CP2(LocalDate.parse("2015-12-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-04-01"))),
                                cp2 = Some(CP2(LocalDate.parse("2016-03-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-03-31"))),
                                cp2 = Some(CP2(LocalDate.parse("2015-12-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-04-01"))),
                                cp2 = Some(CP2(LocalDate.parse("2016-03-31"))),
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
            val versions = calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse(start))),
                                               cp2 = Some(CP2(LocalDate.parse(end))),
                                               hmrcFiling = HMRCFiling(true))
            versions.find( v => v.submission == Computations).get.version shouldBe version
          }
        }
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

        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-03-31"))),
                                cp2 = Some(CP2(LocalDate.parse("2015-12-31"))),
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

        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-04-01"))),
                                cp2 = Some(CP2(LocalDate.parse("2016-03-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-03-31"))),
                                cp2 = Some(CP2(LocalDate.parse("2015-12-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-04-01"))),
                                cp2 = Some(CP2(LocalDate.parse("2016-03-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-03-31"))),
                                cp2 = Some(CP2(LocalDate.parse("2015-12-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-04-01"))),
                                cp2 = Some(CP2(LocalDate.parse("2016-03-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-03-31"))),
                                cp2 = Some(CP2(LocalDate.parse("2015-12-31"))),
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
        calculateReturnVersions(cp1 = Some(CP1(LocalDate.parse("2015-04-01"))),
                                cp2 = Some(CP2(LocalDate.parse("2016-03-31"))),
                                hmrcFiling = HMRCFiling(true),
                                coHoFiling = CompaniesHouseFiling(true),
                                statutoryAccountsFiling = StatutoryAccountsFiling(true),
                                abbreviatedAccountsFiling = AbbreviatedAccountsFiling(true)) shouldBe expectedResult
      }
    }
  }
}

class ComputationsBoxRetrieverForTest extends ComputationsBoxRetriever with FilingAttributesBoxValueRetriever {

  override def retrieveCP1(): CP1 = CP1(LocalDate.parse("2015-03-31"))

  override def retrieveCP2(): CP2 = CP2(LocalDate.parse("2015-12-31"))

  override def retrieveAbbreviatedAccountsFiling(): AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)

  override def retrieveStatutoryAccountsFiling(): StatutoryAccountsFiling = StatutoryAccountsFiling(false)

  override def retrieveMicroEntityFiling(): MicroEntityFiling = MicroEntityFiling(true)

  override def retrieveAbridgedFiling(): AbridgedFiling = AbridgedFiling(false)

  override def retrieveCompaniesHouseFiling(): CompaniesHouseFiling = CompaniesHouseFiling(true)

  override def retrieveHMRCFiling(): HMRCFiling = HMRCFiling(true)

  // Unimplemented functions.
  override def generateValues: Map[String, CtValue[_]] = ???

  override def retrieveProductName(): ProductName = ???

  override def retrieveCompanyType(): FilingCompanyType = ???

  override def retrieveCP36(): CP36 = ???

  override def retrieveCP303(): CP303 = ???

  override def retrieveCP501(): CP501 = ???

  override def retrieveCATO11(): CATO11 = ???

  override def retrieveCP287(): CP287 = ???

  override def retrieveCP21(): CP21 = ???

  override def retrieveCP15(): CP15 = ???

  override def retrieveCPQ20(): CPQ20 = ???

  override def retrieveCP24(): CP24 = ???

  override def retrieveCP18(): CP18 = ???

  override def retrieveCP281(): CP281 = ???

  override def retrieveCP86(): CP86 = ???

  override def retrieveLEC01(): LEC01 = ???

  override def retrieveCPQ17(): CPQ17 = ???

  override def retrieveCP674(): CP674 = ???

  override def retrieveCP668(): CP668 = ???

  override def retrieveCP80(): CP80 = ???

  override def retrieveCP89(): CP89 = ???

  override def retrieveCP53(): CP53 = ???

  override def retrieveCP302(): CP302 = ???

  override def retrieveCP35(): CP35 = ???

  override def retrieveCP83(): CP83 = ???

  override def retrieveCPQ1000(): CPQ1000 = ???

  override def retrieveCP503(): CP503 = ???

  override def retrieveCP23(): CP23 = ???

  override def retrieveCP91Input(): CP91Input = ???

  override def retrieveCP17(): CP17 = ???

  override def retrieveCP667(): CP667 = ???

  override def retrieveCPQ19(): CPQ19 = ???

  override def retrieveCP47(): CP47 = ???

  override def retrieveCP673(): CP673 = ???

  override def retrieveCP26(): CP26 = ???

  override def retrieveCP32(): CP32 = ???

  override def retrieveCP20(): CP20 = ???

  override def retrieveCPQ8(): CPQ8 = ???

  override def retrieveCP286(): CP286 = ???

  override def retrieveCP82(): CP82 = ???

  override def retrieveCP29(): CP29 = ???

  override def retrieveCP8(): CP8 = ???

  override def retrieveCP85(): CP85 = ???

  override def retrieveCP79(): CP79 = ???

  override def retrieveCP46(): CP46 = ???

  override def retrieveCPQ10(): CPQ10 = ???

  override def retrieveCP52(): CP52 = ???

  override def retrieveCP88(): CP88 = ???

  override def retrieveCP34(): CP34 = ???

  override def retrieveCP55(): CP55 = ???

  override def retrieveCP49(): CP49 = ???

  override def retrieveCP87Input(): CP87Input = ???

  override def retrieveCPQ7(): CPQ7 = ???

  override def retrieveCP301(): CP301 = ???

  override def retrieveCP28(): CP28 = ???

  override def retrieveCP22(): CP22 = ???

  override def retrieveCP502(): CP502 = ???

  override def retrieveCPQ21(): CPQ21 = ???

  override def retrieveCP81Input(): CP81Input = ???

  override def retrieveCP16(): CP16 = ???

  override def retrieveCP43(): CP43 = ???

  override def retrieveCP37(): CP37 = ???

  override def retrieveCP31(): CP31 = ???

  override def retrieveCPQ18(): CPQ18 = ???

  override def retrieveCP19(): CP19 = ???

  override def retrieveCP672(): CP672 = ???

  override def retrieveCP666(): CP666 = ???

  override def retrieveCP25(): CP25 = ???

  override def retrieveCP285(): CP285 = ???

  override def retrieveCP78(): CP78 = ???

  override def retrieveCATO12(): CATO12 = ???

  override def retrieveCP7(): CP7 = ???

  override def retrieveCP84(): CP84 = ???

  override def retrieveCP57(): CP57 = ???

  override def retrieveCP30(): CP30 = ???

  override def retrieveCP51(): CP51 = ???

  override def retrieveCP510(): CP510 = ???

  override def retrieveCP33(): CP33 = ???

  override def retrieveCP48(): CP48 = ???

  override def retrieveCP27(): CP27 = ???
}
