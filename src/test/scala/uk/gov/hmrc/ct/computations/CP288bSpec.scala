/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP288bSpec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever()

  testMandatoryWhen("CP288b", CP288b.apply) {
    makeBoxRetriever()
  }

  testBoxIsZeroOrPositive("CP288b", CP288b.apply)

  private def makeBoxRetriever(CP281bValue: Option[Int] = Some(1)) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp281b()).thenReturn(CP281b(CP281bValue))
    retriever
  }
}
