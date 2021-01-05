/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A40(value: Option[Int]) extends CtBoxIdentifier(name = "A40 - Total amount of loans made during the return period which have been repaid, released or written off after the end of the period but earlier than nine months and one day after the end of the period")
with CtOptionalInteger

object A40 extends Calculated[A40, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

 override def calculate(fieldValueRetriever: CT600ABoxRetriever): A40 = {
  calculateA40(fieldValueRetriever.a30(), fieldValueRetriever.a35())
 }
}
