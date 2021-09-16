/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, SelfValidatableBox, _}

case class AC7998(value: Option[Int]) extends CtBoxIdentifier(name = "Employee information note") with CtOptionalInteger with Input with SelfValidatableBox[Frs105AccountsBoxRetriever, Option[Int]] {

  private val minNumberOfEmployees = 0
  private val maxNumberOfEmployees = 99999
  private val mandatoryNotesStartDate: LocalDate = new LocalDate(2017,1,1)

  override def validate(boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {

    val startOfAccountingPeriod: LocalDate = boxRetriever.ac3().value

    passIf(startOfAccountingPeriod.isBefore(mandatoryNotesStartDate)) {
      collectErrors(
        validateIntegerRange(minNumberOfEmployees, maxNumberOfEmployees),
        validateIntegerAsMandatory()
      )
    }
  }
}
