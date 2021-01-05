/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtValue, Input}
import uk.gov.hmrc.ct.domain.CompanyTypes
import uk.gov.hmrc.ct.domain.CompanyTypes.CompanyType

case class FilingCompanyType(value: CompanyType) extends CtBoxIdentifier("Company Type") with CtValue[CompanyType] with Input {

  def isLimitedByGuarantee: Boolean = CompanyTypes.LimitedByGuaranteeCompanyTypes.contains(value)
}
