/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC7210A(value: Option[Int]) extends CtBoxIdentifier(name = "Dividends paid in current period")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
        cannotExistErrorIf(value.nonEmpty && !boxRetriever.ac7200().orFalse ),
        validateMoney(value, min = 0),
        failIf(value.isEmpty && boxRetriever.ac7200.orFalse && boxRetriever.ac7210B.noValue)
                  (Set(CtValidation(None, "error.abridged.additional.dividend.note.one.box.required")))
      )
  }
}
