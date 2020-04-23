/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.{Frs105TestBoxRetriever, ValidateAssetsEqualSharesSpec}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class AC68Spec extends ValidateAssetsEqualSharesSpec[Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def addOtherBoxValue100Mock(mockRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever) =
    when(mockRetriever.ac490()).thenReturn(AC490(Some(100)))

  override def addOtherBoxValueNoneMock(mockRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever) =
    when(mockRetriever.ac490()).thenReturn(AC490(None))

  testAssetsEqualToSharesValidation("AC68", AC68.apply)

  override def createMock(): Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever = mock[Frs105TestBoxRetriever]
}
