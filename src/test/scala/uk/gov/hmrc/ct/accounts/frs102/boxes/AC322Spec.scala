/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC322Spec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac322()).thenReturn(AC322(Some("text")))
  }

  testTextFieldValidation("AC322", AC322, testUpperLimit = Some(StandardCohoTextFieldLimit))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC322", AC322)

}
