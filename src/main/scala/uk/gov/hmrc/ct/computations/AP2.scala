/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class AP2(inputValue: Option[Int], defaultValue: Option[Int]) extends CtBoxIdentifier(name = "Turnover apportioned during accounting period") with CtOptionalInteger with InputWithDefault[Int] with ValidatableBox[AccountsBoxRetriever with ComputationsBoxRetriever] with CtTypeConverters {
  override def validate(boxRetriever: AccountsBoxRetriever with ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateZeroOrPositiveInteger(this),
      totalErrors(boxRetriever)
    )
  }

  private def totalErrors(boxRetriever: AccountsBoxRetriever with ComputationsBoxRetriever) = {
    val ap2 = (inputValue, defaultValue) match {
      case (iv, None) => iv
      case (None, dv) => dv
    }

    failIf(boxRetriever.ap1() + ap2.getOrElse(0) + boxRetriever.ap3() != (boxRetriever.ac12().value.getOrElse(0) + boxRetriever.ac401().value.getOrElse(0))) {
      Set(CtValidation(None, "error.apportionmentTurnover.total"))
    }
  }
}

object AP2 {

  def apply(inputValue: Option[Int]): AP2 = AP2(inputValue = inputValue, defaultValue = None)
}
