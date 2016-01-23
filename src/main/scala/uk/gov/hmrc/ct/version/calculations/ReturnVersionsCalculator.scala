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
import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.{CP1, CP2}
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.domain.CompanyTypes.{MembersClub, LimitedByGuaranteeCharity, Charity, UkTradingCompany}
import uk.gov.hmrc.ct.version.CoHoAccounts.{CoHoMicroEntityAbridgedAccounts, CoHoMicroEntityAccounts, CoHoStatutoryAbbreviatedAccounts, CoHoStatutoryAccounts}
import uk.gov.hmrc.ct.version.CoHoVersions.AccountsVersion1
import uk.gov.hmrc.ct.version.HmrcReturns._
import uk.gov.hmrc.ct.version.HmrcVersions._
import uk.gov.hmrc.ct.version.{Version, Return}

object ReturnVersionsCalculator extends ReturnVersionsCalculator

trait ReturnVersionsCalculator {

  def doCalculation[A <: BoxRetriever](boxRetriever: A): Set[Return] = {
    boxRetriever match {

      case br: CT600EBoxRetriever with FilingAttributesBoxValueRetriever =>
        calculateReturnVersions(apStartDate = Some(br.retrieveE3().value),
                                apEndDate = Some(br.retrieveE4().value),
                                coHoFiling = br.retrieveCompaniesHouseFiling(),
                                hmrcFiling = br.retrieveHMRCFiling(),
                                microEntityFiling = br.retrieveMicroEntityFiling(),
                                statutoryAccountsFiling = br.retrieveStatutoryAccountsFiling(),
                                abridgedFiling = br.retrieveAbridgedFiling(),
                                abbreviatedAccountsFiling = br.retrieveAbbreviatedAccountsFiling(),
                                companyType = br.retrieveCompanyType(),
                                charityAllExempt = br.retrieveE20().value)

      case br: ComputationsBoxRetriever with FilingAttributesBoxValueRetriever =>
        calculateReturnVersions(apStartDate = Some(br.retrieveCP1().value),
                                apEndDate = Some(br.retrieveCP2().value),
                                coHoFiling = br.retrieveCompaniesHouseFiling(),
                                hmrcFiling = br.retrieveHMRCFiling(),
                                microEntityFiling = br.retrieveMicroEntityFiling(),
                                statutoryAccountsFiling = br.retrieveStatutoryAccountsFiling(),
                                abridgedFiling = br.retrieveAbridgedFiling(),
                                abbreviatedAccountsFiling = br.retrieveAbbreviatedAccountsFiling(),
                                companyType = br.retrieveCompanyType())

      case br: FilingAttributesBoxValueRetriever =>
        calculateReturnVersions(apStartDate = None,
                                apEndDate = None,
                                coHoFiling = br.retrieveCompaniesHouseFiling(),
                                hmrcFiling = br.retrieveHMRCFiling(),
                                microEntityFiling = br.retrieveMicroEntityFiling(),
                                statutoryAccountsFiling = br.retrieveStatutoryAccountsFiling(),
                                abridgedFiling = br.retrieveAbridgedFiling(),
                                abbreviatedAccountsFiling = br.retrieveAbbreviatedAccountsFiling(),
                                companyType = br.retrieveCompanyType())
      case _ => throw new IllegalArgumentException("The box retriever passed in must implement FilingAttributesBoxValueRetriever")
    }
  }

  def calculateReturnVersions(apStartDate: Option[LocalDate] = None, apEndDate: Option[LocalDate] = None,
                              coHoFiling: CompaniesHouseFiling = CompaniesHouseFiling(false),
                              hmrcFiling: HMRCFiling = HMRCFiling(false),
                              microEntityFiling: MicroEntityFiling = MicroEntityFiling(false),
                              statutoryAccountsFiling: StatutoryAccountsFiling = StatutoryAccountsFiling(false),
                              abridgedFiling: AbridgedFiling = AbridgedFiling(false),
                              abbreviatedAccountsFiling: AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false),
                              companyType: FilingCompanyType = FilingCompanyType(UkTradingCompany),
                              charityAllExempt: Option[Boolean] = None): Set[Return] = {

    val accountsVersion = AccountsVersion1

    val cohoReturn: Set[Return] = (coHoFiling, microEntityFiling, statutoryAccountsFiling, abridgedFiling, abbreviatedAccountsFiling) match {
      case (CompaniesHouseFiling(true), MicroEntityFiling(true), _, AbridgedFiling(false), _) =>
        Set(Return(CoHoMicroEntityAccounts, accountsVersion))

      case (CompaniesHouseFiling(true), MicroEntityFiling(true), _, AbridgedFiling(true), _) =>
        Set(Return(CoHoMicroEntityAbridgedAccounts, accountsVersion))

      case (CompaniesHouseFiling(true), _, StatutoryAccountsFiling(true), _, AbbreviatedAccountsFiling(false)) =>
        Set(Return(CoHoStatutoryAccounts, accountsVersion))

      case (CompaniesHouseFiling(true), _, StatutoryAccountsFiling(true), _, AbbreviatedAccountsFiling(true)) =>
        Set(Return(CoHoStatutoryAbbreviatedAccounts, accountsVersion))

      case _ => Set.empty
    }

    val hmrcAccounts = (hmrcFiling, microEntityFiling, statutoryAccountsFiling, companyType) match {
      case (HMRCFiling(true), MicroEntityFiling(true), _, _) =>
        Set(Return(HmrcMicroEntityAccounts, accountsVersion))

      case (HMRCFiling(true), _, StatutoryAccountsFiling(false), FilingCompanyType(LimitedByGuaranteeCharity)) =>
        Set(Return(HmrcStatutoryAccounts, accountsVersion))

      case (HMRCFiling(true), _, StatutoryAccountsFiling(true), _) =>
        Set(Return(HmrcStatutoryAccounts, accountsVersion))

      case (HMRCFiling(true), MicroEntityFiling(false), StatutoryAccountsFiling(false), _) =>
        Set(Return(HmrcUploadedAccounts, UploadedAccounts))

      case _ => Set.empty
    }

    val ct600Returns = (hmrcFiling, apStartDate, companyType, charityAllExempt) match {

      case (HMRCFiling(true), Some(startDate), FilingCompanyType(LimitedByGuaranteeCharity), Some(all)) =>
        ct600ReturnsForCharity(startDate, all)

      case (HMRCFiling(true), Some(startDate), FilingCompanyType(Charity), Some(all)) =>
        ct600ReturnsForCharity(startDate, all)

      case (HMRCFiling(true), Some(startDate), FilingCompanyType(MembersClub), _) =>
        ct600ReturnsForMembersClub(startDate)

      case (HMRCFiling(true), Some(startDate), _, _) =>
        ct600ReturnsForCompany(startDate)

      case _ => Set.empty
    }

    val compsReturns = (hmrcFiling, apStartDate, apEndDate, charityAllExempt) match {
      case (HMRCFiling(true), _, _, Some(true)) =>
        Set.empty

      case (HMRCFiling(true), Some(startDate), Some(endDate), _) =>
        Set(Return(Computations, computationsVersionBasedOnDate(startDate, endDate)))

      case _ => Set.empty
    }

    cohoReturn ++ hmrcAccounts ++ ct600Returns ++ compsReturns
  }

  private def computationsVersionBasedOnDate(apStartDate: LocalDate, apEndDate: LocalDate): Version = {
    (apStartDate, apEndDate) match {
      case (startDate, _) if startDate.isAfter(LocalDate.parse("2015-03-31")) =>
        ComputationsCT20150201
      case (_, endDate) if endDate.isAfter(LocalDate.parse("2013-03-31")) =>
        ComputationsCT20141001
      case (_, endDate) if endDate.isAfter(LocalDate.parse("2008-03-31")) =>
        ComputationsCT20130721
      case _ => throw new IllegalArgumentException(s"Cannot calculate the Computations Version for the provided dates: $apStartDate -> $apEndDate")
    }
  }

  private def ct600VersionBasedOnApStartDate(apStartDate: LocalDate): Version = if (apStartDate.isAfter(LocalDate.parse("2015-03-31"))) CT600Version3 else CT600Version2

  private def ct600ReturnsForCharity(apStartDate: LocalDate, charityAllExempt: Boolean): Set[Return] = {

    val version = ct600VersionBasedOnApStartDate(apStartDate)
    if (charityAllExempt) {
      Set(Return(CT600e, version))
    }
    else {
      Set(Return(CT600e, version),
          Return(CT600, version),
          Return(CT600j, version))
    }
  }

  private def ct600ReturnsForMembersClub(apStartDate: LocalDate): Set[Return] = {
    val version = ct600VersionBasedOnApStartDate(apStartDate)
    Set(Return(CT600, version),
        Return(CT600j, version))
  }

  private def ct600ReturnsForCompany(startDate: LocalDate): Set[Return] = {
    val version = ct600VersionBasedOnApStartDate(startDate)
    Set(Return(CT600, version),
        Return(CT600a, version),
        Return(CT600j, version))
  }
}
