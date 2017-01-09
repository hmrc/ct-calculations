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

package uk.gov.hmrc.ct.domain

import play.api.data.validation.ValidationError
import play.api.libs.json._


object CompanyTypes {

  case object UkTradingCompany extends CompanyType(0)

  case object CompanyLimitedByGuarantee extends CompanyType(0)

  case object CASC extends CompanyType(0)

  case object LimitedByGuaranteeCASC extends CompanyType(0)

  case object LimitedBySharesCASC extends CompanyType(0)

  case object CompanyInLiquidation extends CompanyType(3)

  case object MembersClub extends CompanyType(6)

  case object Charity extends CompanyType(8)

  case object LimitedByGuaranteeCharity extends CompanyType(8)

  case object LimitedBySharesCharity extends CompanyType(8)

  case object MembersVoluntaryLiquidation extends CompanyType(0)

  case object UnitTrust extends CompanyType(1)

  case object CloseInvestmentHoldingCompany extends CompanyType(2)

  case object InvestmentTrustWithHousingInvestmentProfits extends CompanyType(9)

  case object InsuranceCompany extends CompanyType(5)

  case object PropertyManagementCompany extends CompanyType(7)

  case object RealEstateInvestmentTrust extends CompanyType(9)

  case object PublicLimitedCompany extends CompanyType(0)

  case object LimitedLiabilityPartnership extends CompanyType(0)

  case object Ineligible extends CompanyType(99)

  sealed abstract class CompanyType(val hmrcCode: Int) {
    override def toString: String = this.getClass.getSimpleName.filterNot(_ == '$')
  }

  val AllCompanyTypes: Set[CompanyType] = Set(
    UkTradingCompany, CompanyLimitedByGuarantee, CASC, LimitedByGuaranteeCASC, LimitedBySharesCASC,
    CompanyInLiquidation, MembersClub, Charity, MembersVoluntaryLiquidation, UnitTrust, CloseInvestmentHoldingCompany,
    InvestmentTrustWithHousingInvestmentProfits, InsuranceCompany, PropertyManagementCompany,
    RealEstateInvestmentTrust, PublicLimitedCompany, LimitedLiabilityPartnership, Ineligible, LimitedByGuaranteeCharity, LimitedBySharesCharity
  )

  implicit val reads: Reads[CompanyType] =
    Reads.StringReads.collect(ValidationError("Not a valid company type.")){case companyClassName if registeredTypes.contains(companyClassName) => registeredTypes(companyClassName)}

  implicit val writes: Writes[CompanyType] = new Writes[CompanyType] {
    def writes(o: CompanyType): JsValue = JsString(o.toString)
  }

  private val allCompanyTypesSerializable: Set[CompanyType with Product with Serializable] = AllCompanyTypes.asInstanceOf[Set[CompanyType with Product with Serializable]]
  private val registeredTypes: Map[String, CompanyType] = allCompanyTypesSerializable.map(t => t.productPrefix -> t).toMap

  val LimitedByGuaranteeCompanyTypes: Set[CompanyType] = Set(CompanyLimitedByGuarantee, LimitedByGuaranteeCASC, LimitedByGuaranteeCharity)
}
