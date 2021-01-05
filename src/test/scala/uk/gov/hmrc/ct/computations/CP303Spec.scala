/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP303Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever(true)

  testMandatoryWhen("CP303", CP303.apply) {
    makeBoxRetriever(cpq21Value = true)
  }

  testBoxIsZeroOrPositive("CP303", CP303.apply)

  testCannotExistWhen("CP303", CP303.apply) {
    makeBoxRetriever(false)
  }

  private def makeBoxRetriever(cpq21Value: Boolean) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cpQ21()).thenReturn(CPQ21(Some(cpq21Value)))
    retriever
  }
}
