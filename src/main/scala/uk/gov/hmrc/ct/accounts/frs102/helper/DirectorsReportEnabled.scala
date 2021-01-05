/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.helper

import uk.gov.hmrc.ct.accounts.frs102.validation.DirectorsReportEnabledCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}

case class DirectorsReportEnabled(value: Boolean) extends CtBoxIdentifier(name = "Do you want to file a directors' report to Companies House?") with CtBoolean

object DirectorsReportEnabled extends Calculated[DirectorsReportEnabled, Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever] with DirectorsReportEnabledCalculator {

  override def calculate(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): DirectorsReportEnabled = {
    DirectorsReportEnabled(calculateDirectorsReportEnabled(boxRetriever))
  }
}
