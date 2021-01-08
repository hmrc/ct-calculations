/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC304A(value: Option[String]) extends CtBoxIdentifier(name = "Director Name loaned too")
  with CtOptionalString
  with Input
  with SelfValidatableBox[Frs102AccountsBoxRetriever with Frs10xDirectorsBoxRetriever, Option[String]]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with Frs10xDirectorsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      validateAsMandatory(),
      validateOptionalStringByLength(1, StandardCohoNameFieldLimit),
      validateCohoOptionalNameField(),
      validateCustomDirectorName(boxRetriever)
    )

  }

  private def validateCustomDirectorName(boxRetriever: Frs102AccountsBoxRetriever with Frs10xDirectorsBoxRetriever)(): Set[CtValidation] = {

      if (boxRetriever.ac8021().orFalse && !boxRetriever.directors().directors.exists(d => d.ac8001 == this.value.getOrElse(""))) {
        Set(CtValidation(Some("AC304A"), "error.loansToDirectors.invalidDirectorName"))
      } else
        Set.empty

  }
}
