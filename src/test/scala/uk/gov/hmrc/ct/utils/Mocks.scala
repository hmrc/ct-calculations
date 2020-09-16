/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.utils

import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import org.scalatestplus.mockito.MockitoSugar

trait Mocks extends MockitoSugar {
  val mockComputationsBoxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

}
