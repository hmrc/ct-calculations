/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.{Frs105TestBoxRetriever, ValidateAssetsEqualSharesSpec}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class AC69Spec extends ValidateAssetsEqualSharesSpec[Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def addOtherBoxValue100Mock(mockRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever) =
    when(mockRetriever.ac491()).thenReturn(AC491(Some(100)))

  override def addOtherBoxValueNoneMock(mockRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever) =
    when(mockRetriever.ac491()).thenReturn(AC491(None))

  testAssetsEqualToSharesValidation("AC69", AC69.apply)

  override def createMock(): Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever = mock[Frs105TestBoxRetriever]
}
