/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever


case class B595(value: Option[BigDecimal]) extends CtBoxIdentifier("Tax already paid ( and not already repaid)") with CtOptionalBigDecimal with Input with ValidatableBox[CT600BoxRetriever] {

  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = validateZeroOrPositiveBigDecimal(this)
}
