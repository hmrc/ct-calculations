/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A65(value: Option[Int]) extends CtBoxIdentifier(name = "A65 - Total amount of loans made during the return period which have been repaid, released, or written off more than nine months after the end of the period and relief is due now")
with CtOptionalInteger

object A65 extends Calculated[A65, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

 override def calculate(fieldValueRetriever: CT600ABoxRetriever): A65 = {
  calculateA65(fieldValueRetriever.a55(), fieldValueRetriever.a60())
 }
}
