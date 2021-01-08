/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A35(value: Option[Int]) extends CtBoxIdentifier(name = "A35 - Amount released or written off after the end of the period but earlier than nine months and one day after the end of the period") with CtOptionalInteger

object A35 extends Calculated[A35, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A35 = {
    calculateA35(fieldValueRetriever.cp2(), fieldValueRetriever.loansToParticipators())
  }
}
