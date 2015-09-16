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
import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.{CP1, CP2}
import uk.gov.hmrc.ct.version.CoHoAccounts.{CoHoMicroEntityAbridgedAccounts, CoHoMicroEntityAccounts, CoHoStatutoryAbbreviatedAccounts, CoHoStatutoryAccounts}
import uk.gov.hmrc.ct.version.CoHoVersions.AccountsVersion1
import uk.gov.hmrc.ct.version.HmrcReturns._
import uk.gov.hmrc.ct.version.HmrcVersions._
import uk.gov.hmrc.ct.version.Return

object ReturnVersionsCalculator extends ReturnVersionsCalculator

trait ReturnVersionsCalculator {

  def doCalculation[A <: BoxRetriever](boxRetriever: A): Set[Return] = {
    boxRetriever match {
      case boxRetriever: ComputationsBoxRetriever with FilingAttributesBoxValueRetriever =>
        calculateReturnVersions(cp1 = Some(boxRetriever.retrieveCP1()),
                                cp2 = Some(boxRetriever.retrieveCP2()),
                                coHoFiling = boxRetriever.retrieveCompaniesHouseFiling(),
                                hmrcFiling = boxRetriever.retrieveHMRCFiling(),
                                microEntityFiling = boxRetriever.retrieveMicroEntityFiling(),
                                statutoryAccountsFiling = boxRetriever.retrieveStatutoryAccountsFiling(),
                                abridgedFiling = boxRetriever.retrieveAbridgedFiling(),
                                abbreviatedAccountsFiling = boxRetriever.retrieveAbbreviatedAccountsFiling())

      case boxRetriever: FilingAttributesBoxValueRetriever =>
        calculateReturnVersions(cp1 = None,
                                cp2 = None,
                                coHoFiling = boxRetriever.retrieveCompaniesHouseFiling(),
                                hmrcFiling = boxRetriever.retrieveHMRCFiling(),
                                microEntityFiling = boxRetriever.retrieveMicroEntityFiling(),
                                statutoryAccountsFiling = boxRetriever.retrieveStatutoryAccountsFiling(),
                                abridgedFiling = boxRetriever.retrieveAbridgedFiling(),
                                abbreviatedAccountsFiling = boxRetriever.retrieveAbbreviatedAccountsFiling())
      case _ => throw new IllegalArgumentException("The box retriever passed in must implement FilingAttributesBoxValueRetriever")
    }
  }

  def calculateReturnVersions(cp1: Option[CP1] = None, cp2: Option[CP2] = None,
                              coHoFiling: CompaniesHouseFiling = CompaniesHouseFiling(false),
                              hmrcFiling: HMRCFiling = HMRCFiling(false),
                              microEntityFiling: MicroEntityFiling = MicroEntityFiling(false),
                              statutoryAccountsFiling: StatutoryAccountsFiling = StatutoryAccountsFiling(false),
                              abridgedFiling: AbridgedFiling = AbridgedFiling(false),
                              abbreviatedAccountsFiling: AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)): Set[Return] = {

    val cohoReturn: Set[Return] = (coHoFiling, microEntityFiling, statutoryAccountsFiling, abridgedFiling, abbreviatedAccountsFiling) match {
      case (CompaniesHouseFiling(true), MicroEntityFiling(true), _, AbridgedFiling(false), _) =>
        Set(Return(CoHoMicroEntityAccounts, AccountsVersion1))

      case (CompaniesHouseFiling(true), MicroEntityFiling(true), _, AbridgedFiling(true), _) =>
        Set(Return(CoHoMicroEntityAbridgedAccounts, AccountsVersion1))

      case (CompaniesHouseFiling(true), _, StatutoryAccountsFiling(true), _, AbbreviatedAccountsFiling(false)) =>
        Set(Return(CoHoStatutoryAccounts, AccountsVersion1))

      case (CompaniesHouseFiling(true), _, StatutoryAccountsFiling(true), _, AbbreviatedAccountsFiling(true)) =>
        Set(Return(CoHoStatutoryAbbreviatedAccounts, AccountsVersion1))

      case _ => Set.empty
    }

    val hmrcAccounts = (hmrcFiling, microEntityFiling, statutoryAccountsFiling) match {
      case (HMRCFiling(true), MicroEntityFiling(true), _) => Set(Return(HmrcMicroEntityAccounts, AccountsVersion1))

      case (HMRCFiling(true), _, StatutoryAccountsFiling(true)) => Set(Return(HmrcStatutoryAccounts, AccountsVersion1))

      case _ => Set.empty
    }

    val ct600Returns = (hmrcFiling, cp1) match {
      case (HMRCFiling(true), Some(CP1(startDate))) if startDate.isAfter(LocalDate.parse("2015-03-31")) =>
        Set(Return(CT600, CT600Version3),
            Return(CT600a, CT600Version3),
            Return(CT600j, CT600Version3))

      case (HMRCFiling(true), Some(CP1(startDate))) =>
        Set(Return(CT600, CT600Version2),
            Return(CT600a, CT600Version2),
            Return(CT600j, CT600Version2))

      case _ => Set.empty
    }

    val compsReturns = (hmrcFiling, cp1, cp2) match {
      case (HMRCFiling(true), Some(CP1(startDate)), _) if startDate.isAfter(LocalDate.parse("2015-03-31")) =>
        Set(Return(Computations, ComputationsCT20150201))
      case (HMRCFiling(true), _, Some(CP2(endDate))) if endDate.isAfter(LocalDate.parse("2013-03-31")) =>
        Set(Return(Computations, ComputationsCT20141001))
      case (HMRCFiling(true), _, Some(CP2(endDate))) if endDate.isAfter(LocalDate.parse("2008-03-31")) =>
        Set(Return(Computations, ComputationsCT20130721))
      case _ => Set.empty
    }

    cohoReturn ++ hmrcAccounts ++ ct600Returns ++ compsReturns
  }
}
