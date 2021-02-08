/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.utils

import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Mocks extends MockitoSugar {
  val mockComputationsBoxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
  val mockFrs10xAccountsBoxRetriever: Frs10xAccountsBoxRetriever = mock[Frs10xAccountsBoxRetriever]
  val mockFilingAttributesBoxRetriever: FilingAttributesBoxValueRetriever = mock[FilingAttributesBoxValueRetriever]

  val magic: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever = mock[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever]
}
