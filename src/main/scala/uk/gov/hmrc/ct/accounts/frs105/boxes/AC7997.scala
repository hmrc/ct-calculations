/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC7997(value: Option[String]) extends CtBoxIdentifier(name = "Advances and credits note") with CtOptionalString
with Input
with SelfValidatableBox[Frs105AccountsBoxRetriever, Option[String]] {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever) = {
    import boxRetriever._
    collectErrors (
      cannotExistErrorIf(value.nonEmpty && ac7992().isFalse),

      failIf (boxRetriever.ac7992().isTrue) (
        collectErrors (
          validateStringAsMandatory(),
          validateOptionalStringByLength(1, StandardCohoTextFieldLimit),
          validateCoHoStringReturnIllegalChars()
        )
      )
    )
  }
}
