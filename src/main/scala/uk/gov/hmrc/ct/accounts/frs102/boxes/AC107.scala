/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC107(value: Option[Int]) extends CtBoxIdentifier(name = "Average number of employees (previous PoA)")
  with CtOptionalInteger
  with Input
  with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[Int]]
  with Validators
  with CtTypeConverters {

  private val minNumberOfEmployees = 0
  private val maxNumberOfEmployees = 99999

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    val doesNotHavePreviousPoA = !ac205().hasValue

    passIf(doesNotHavePreviousPoA) {
      collectErrors(
        validateAsMandatory(),
          validateIntegerRange(minNumberOfEmployees, maxNumberOfEmployees)
      )
    }
  }
}