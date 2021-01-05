/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A5(value: Option[Int]) extends CtBoxIdentifier(name = "A5 - Amount released or written off after the end of the period but earlier than nine months and one day after the end of the period") with CtOptionalInteger

object A5 extends Calculated[A5, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

   override def calculate(fieldValueRetriever: CT600ABoxRetriever): A5 = {
     calculateA5(fieldValueRetriever.cp2(), fieldValueRetriever.lp03())
   }
 }
