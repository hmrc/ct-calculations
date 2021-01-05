/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Linked}

case class CoHoAccountsApprovalRequired(value: Boolean) extends CtBoxIdentifier("True if approval required for CoHo accounts") with CtBoolean

object CoHoAccountsApprovalRequired extends Linked[CompaniesHouseFiling, CoHoAccountsApprovalRequired] {

  override def apply(coHoFiling: CompaniesHouseFiling): CoHoAccountsApprovalRequired = new CoHoAccountsApprovalRequired(coHoFiling.value)
}
