/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever

case class B860(value: Option[Int]) extends CtBoxIdentifier("Repayment amount upper bound") with CtOptionalInteger with Input with ValidatableBox[RepaymentsBoxRetriever] {
  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = {
    val repaymentsQ1 = boxRetriever.repaymentsQ1()

    (repaymentsQ1.value, value) match {
      case (Some(false), _) => validateAsMandatory(this) ++ validateZeroOrPositiveInteger(this)
      case (Some(true), Some(_)) => validateIntegerAsBlank("B860", this)
      case _ => Set()
    }
  }
}
