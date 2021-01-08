/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class RSQ2(inputValue: Option[Boolean], defaultValue: Option[Boolean]) extends CtBoxIdentifier
  with CtOptionalBoolean with InputWithDefault[Boolean] with ValidatableBox[BoxRetriever] with Validators {

  override def validate(boxRetriever: BoxRetriever): Set[CtValidation] = {
    boxRetriever match {
      case compsRetriever: ComputationsBoxRetriever => {
        collectErrors(
          cannotExistErrorIf(CP287GreaterThenZeroAndHaveInputValue(compsRetriever)),
          requiredErrorIf(CP287NotExistsAndNoInputValue(compsRetriever))
        )
      }
      case _ => validateAsMandatory(this) //Charities may not have Computations, but still need to validate as mandatory
    }

  }

  private def CP287GreaterThenZeroAndHaveInputValue(retriever: ComputationsBoxRetriever)() =
    retriever.cp287().value.exists(_ > 0) && inputValue.isDefined

  private def CP287NotExistsAndNoInputValue(retriever: ComputationsBoxRetriever)() =
    !retriever.cp287().value.exists(_ > 0) && inputValue.isEmpty
}

object RSQ2 {

  def apply(inputValue: Option[Boolean]): RSQ2 = RSQ2(inputValue, None)
}
