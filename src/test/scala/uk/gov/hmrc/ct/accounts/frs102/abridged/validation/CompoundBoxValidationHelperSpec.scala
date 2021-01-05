/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.abridged.validation

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.validation.CompoundBoxValidationHelper
import uk.gov.hmrc.ct.box.CtValidation

class CompoundBoxValidationHelperSpec extends WordSpec with Matchers {

    "contextualiseErrorKey" should {

      "convert message key to format that includes container index and keyword 'compoundList" in {
        CompoundBoxValidationHelper.contextualiseErrorKey("container", "error.BoxId.some.message", 2) shouldBe
          "error.compoundList.container.2.BoxId.some.message"
      }

      "convert global message key to format that includes compound box name, container index and keywords 'compoundList'" in {
        CompoundBoxValidationHelper.contextualiseError("OwningBox", "container", CtValidation(Some("local"), "error.BoxId.some.message"), 2) shouldBe
          CtValidation(Some("OwningBox"), "error.compoundList.container.2.BoxId.some.message")

        CompoundBoxValidationHelper.contextualiseError("LoansToDirectors", "loans", CtValidation(None, "error.LoansToDirectors.one.field.required"), 2) shouldBe
          CtValidation(None, "error.LoansToDirectors.compoundList.loans.2.one.field.required")
      }
  }
}
