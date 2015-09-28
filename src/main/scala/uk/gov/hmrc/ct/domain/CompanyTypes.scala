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

package uk.gov.hmrc.ct.domain

import play.api.data.validation.ValidationError
import play.api.libs.json._


object CompanyTypes {

  case object UkTradingCompany extends BaseCompanyType(0, "UK trading or professional services company")

  case object CompanyLimitedByGuarantee extends BaseCompanyType(0, "Company Limited by Guarantee")

  case object CommunityAmateurSportsClub extends BaseCompanyType(0, "(CASC) Community Amateur Sports Club registered as a UK limited company")

  case object CompanyInLiquidation extends BaseCompanyType(3, "Company in liquidation (second or subsequent year of liquidation)")

  case object MembersClub extends BaseCompanyType(6, "Members' club or voluntary association")

  case object Charity extends BaseCompanyType(8, "Charity or owned by a charity")

  case object MembersVoluntaryLiquidation extends BaseCompanyType(0, "Members' Voluntary Liquidation ( MVL )")

  case object UnitTrust extends BaseCompanyType(1, "Unit trust or Open-ended Investment Company")

  case object CloseInvestmentHoldingCompany extends BaseCompanyType(2, "Close investment holding company")

  case object InvestmentTrustWithHousingInvestmentProfits extends BaseCompanyType(9, "Investment trust with housing investment profits")

  case object InsuranceCompany extends BaseCompanyType(5, "Insurance company")

  case object PropertyManagementCompany extends BaseCompanyType(7, "Property Management company")

  case object RealEstateInvestmentTrust extends BaseCompanyType(9, "Real Estate Investment Trust C - residual company")

  case object PublicLimitedCompany extends BaseCompanyType(0, "Public Limited Company")

  case object LimitedLiabilityPartnership extends BaseCompanyType(0, "Limited Liability Partnership")

  sealed abstract class BaseCompanyType(val hmrcCode: Int, val name: String) {
    override def toString: String = this.getClass.getSimpleName.filterNot(_ == '$')
  }

  val AllCompanyTypes = Seq(
    UkTradingCompany, CompanyLimitedByGuarantee, CommunityAmateurSportsClub, CompanyInLiquidation,
    MembersClub, Charity, MembersVoluntaryLiquidation, UnitTrust, CloseInvestmentHoldingCompany,
    InvestmentTrustWithHousingInvestmentProfits, InsuranceCompany, PropertyManagementCompany,
    RealEstateInvestmentTrust, PublicLimitedCompany, LimitedLiabilityPartnership
  )

  implicit val reads: Reads[BaseCompanyType] =
    Reads.StringReads.collect(ValidationError("Not a valid company type.")){case companyClassName if registeredTypes.contains(companyClassName) => registeredTypes(companyClassName)}

  implicit val writes: Writes[BaseCompanyType] = new Writes[BaseCompanyType] {
    def writes(o: BaseCompanyType): JsValue = JsString(o.toString)
  }

  private val registeredTypes: Map[String, BaseCompanyType] = AllCompanyTypes.map(t => t.productPrefix -> t).toMap

}
