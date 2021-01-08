/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC7999a(value: Option[Boolean]) extends CtBoxIdentifier(name = "Company does have off balance sheet arrangements")
  with CtOptionalBoolean
  with Input
  with SelfValidatableBox[Frs105AccountsBoxRetriever, Option[Boolean]] {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {

    val startOfAccountingPeriod: LocalDate = boxRetriever.ac3().value
    val mandatoryNotesStartDate: LocalDate = new LocalDate(2017,1,1)

    passIf(startOfAccountingPeriod.isBefore(mandatoryNotesStartDate)) {
      collectErrors(
        failIf(value.isEmpty) {
          validateAsMandatory()
        }
      )
    }
  }
}
