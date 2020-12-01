/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever

class AC5123Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter
  with MockFrs102AccountsRetriever with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC5123", AC5123)
}
