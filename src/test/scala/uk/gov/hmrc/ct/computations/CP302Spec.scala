/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP302Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever(true)

  testMandatoryWhen("CP302", CP302.apply) {
    makeBoxRetriever(cpq21Value = true)
  }

  testBoxIsZeroOrPositive("CP302", CP302.apply)

  testCannotExistWhen("CP302", CP302.apply) {
    makeBoxRetriever(cpq21Value = false)
  }

  private def makeBoxRetriever(cpq21Value: Boolean) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cpQ21()).thenReturn(CPQ21(Some(cpq21Value)))
    retriever
  }
}
