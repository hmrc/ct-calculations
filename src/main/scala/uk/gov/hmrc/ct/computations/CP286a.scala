/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP286a(value: Option[Int])
  extends CtBoxIdentifier("How much of those losses were made in the Northern Ireland")
    with CtOptionalInteger
    with Input
    with ValidatableBox[ComputationsBoxRetriever] with NorthernIrelandRateValidation with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(value.isEmpty && boxRetriever.cpQ18().isTrue
        && mayHaveNirLosses(boxRetriever) &&
        !hasValue),
      cannotExistErrorIf(value.nonEmpty && !boxRetriever.cpQ18().orFalse),
      validateIntegerRange("CP286a", this, 0, boxRetriever.cp286().orZero)
    )
  }

}

object CP286a {

  def apply(int: Int): CP286a = CP286a(Some(int))
}
