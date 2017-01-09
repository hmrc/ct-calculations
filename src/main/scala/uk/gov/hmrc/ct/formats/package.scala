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

package uk.gov.hmrc.ct

import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.ct.box.formats._

package object formats {

  implicit val cato01Format: Format[CATO01] = new IntegerFormat[CATO01](CATO01.apply)

  implicit val cato02Format: Format[CATO02] = new IntegerFormat[CATO02](CATO02.apply)

  implicit val cato03Format: Format[CATO03] = new IntegerFormat[CATO03](CATO03.apply)

  implicit val CATO04Format: Format[CATO04] = new BigDecimalFormat[CATO04](CATO04.apply)

  implicit val cato10Format: Format[CATO10] = new BooleanFormat[CATO10](CATO10.apply)

  implicit val cato11Format: Format[CATO11] = new OptionalStringFormat[CATO11](CATO11.apply)

  implicit val cato12Format: Format[CATO12] = new OptionalStringFormat[CATO12](CATO12.apply)

  implicit val cato13Format: Format[CATO13] = new IntegerFormat[CATO13](CATO13.apply)

  implicit val cato14Format: Format[CATO14] = new IntegerFormat[CATO14](CATO14.apply)

  implicit val cato15Format: Format[CATO15] = new IntegerFormat[CATO15](CATO15.apply)

  implicit val cato16Format: Format[CATO16] = new IntegerFormat[CATO16](CATO16.apply)

  implicit val companyTypeFormat: Format[FilingCompanyType] = Json.format[FilingCompanyType]

  implicit val abbreviatedAccountsFilingFormat: Format[AbbreviatedAccountsFiling] = new BooleanFormat[AbbreviatedAccountsFiling](AbbreviatedAccountsFiling.apply)

  implicit val abridgedFilingFormat: Format[AbridgedFiling] = new BooleanFormat[AbridgedFiling](AbridgedFiling.apply)

  implicit val companiesHouseFilingFormat: Format[CompaniesHouseFiling] = new BooleanFormat[CompaniesHouseFiling](CompaniesHouseFiling.apply)

  implicit val hmrcFilingFormat: Format[HMRCFiling] = new BooleanFormat[HMRCFiling](HMRCFiling.apply)

  implicit val companiesHouseSubmittedFormat: Format[CompaniesHouseSubmitted] = new BooleanFormat[CompaniesHouseSubmitted](CompaniesHouseSubmitted.apply)

  implicit val hmrcSubmittedFormat: Format[HMRCSubmitted] = new BooleanFormat[HMRCSubmitted](HMRCSubmitted.apply)

  implicit val hmrcAmendmentFormat: Format[HMRCAmendment] = new BooleanFormat[HMRCAmendment](HMRCAmendment.apply)

  implicit val microEntityFilingFormat: Format[MicroEntityFiling] = new BooleanFormat[MicroEntityFiling](MicroEntityFiling.apply)

  implicit val statutoryAccountsFilingFormat: Format[StatutoryAccountsFiling] = new BooleanFormat[StatutoryAccountsFiling](StatutoryAccountsFiling.apply)

  implicit val utrFormat: Format[UTR] = new StringFormat[UTR](UTR.apply)

  implicit val cato21Format: Format[CATO21] = new BigDecimalFormat[CATO21](CATO21.apply)

  implicit val cato22Format: Format[CATO22] = new BigDecimalFormat[CATO22](CATO22.apply)

  implicit val CountryOfRegistrationFormat: Format[CountryOfRegistration] = new OptionalStringFormat[CountryOfRegistration](CountryOfRegistration.apply)
}
