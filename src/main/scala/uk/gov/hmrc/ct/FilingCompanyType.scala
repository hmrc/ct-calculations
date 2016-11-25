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

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtValue, Input}
import uk.gov.hmrc.ct.domain.CompanyTypes
import uk.gov.hmrc.ct.domain.CompanyTypes.CompanyType

case class FilingCompanyType(value: CompanyType) extends CtBoxIdentifier("Company Type") with CtValue[CompanyType] with Input {

  def isLimitedByGuarantee: Boolean = CompanyTypes.LimitedByGuaranteeCompanyTypes.contains(value)
}
