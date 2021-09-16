/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A55(value: Option[Int]) extends CtBoxIdentifier(name = "A55 - Information about loans made during the return period which have been repaid more than nine months after the end of the period and relief is due now")
with CtOptionalInteger

object A55 extends Calculated[A55, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

 override def calculate(fieldValueRetriever: CT600ABoxRetriever): A55 = {
  import fieldValueRetriever._
  calculateA55(cp2(), loansToParticipators(), lpq07())
 }
}
