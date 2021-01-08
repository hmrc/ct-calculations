/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBoolean}
import uk.gov.hmrc.ct.ct600a.v2.A6
import uk.gov.hmrc.ct.ct600a.v2.A6._
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class B80(value: Option[Boolean]) extends CtBoxIdentifier(name = "B80 - Completed box A11 in the Supplementary Pages CT600A") with CtOptionalBoolean

object B80 extends Calculated[B80, CT600ABoxRetriever] {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): B80 = {
    calculateB80(fieldValueRetriever.a11())
  }
}
