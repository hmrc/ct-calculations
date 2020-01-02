package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever, MockFrs105AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox.StandardCohoTextFieldLimit


class AC200Spec extends WordSpec with Matchers with MockitoSugar with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  private def validateAC200(inputField: Option[String], validationResult: Set[CtValidation]) = AC200(inputField).validate(boxRetriever) shouldBe validationResult

  "validation should pass successfully" when {

    val input = "Some very off balance arrangements"
    val validationSuccess: Set[CtValidation] = Set.empty

    "'Yes' button has been pressed and there is content in the text field" in {
      when(boxRetriever.ac200a()) thenReturn AC200A(Some(true))
      validateAC200(Some(input), validationSuccess)
    }
    "'No' button has been pressed and there is content in the text field" in {
      when(boxRetriever.ac200a()) thenReturn AC200A(Some(false))
      validateAC200(Some(input), validationSuccess)
    }
  }

  "validation should fail successfully" when {

    val fieldRequiredError = Set(CtValidation(Some("AC200"), "error.AC200.required", None))

    "'Yes' button has been pressed and there is no content in the text field" in {
      when(boxRetriever.ac200a()) thenReturn AC200A(Some(true))
      validateAC200(Some(""), fieldRequiredError)
      validateAC200(None, fieldRequiredError)
    }
  }

  "the string entered contains more than 20,000" in {
    when(boxRetriever.ac200a()) thenReturn AC200A(Some(true))
    val input = "a" * StandardCohoTextFieldLimit + 1
    val tooManyCharactersErrorMsg = Set(CtValidation(Some("AC200"), "error.AC200.text.sizeRange", Some(List("1", "20000"))))
    validateAC200(Some(input), tooManyCharactersErrorMsg)
  }
}
