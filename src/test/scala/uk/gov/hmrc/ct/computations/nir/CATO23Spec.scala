/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.nir

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.computations.{CP997NI, CP998}
import uk.gov.hmrc.ct.{BoxValidationFixture, CATO01, CATO23}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CATO23Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever: ComputationsBoxRetriever = makeBoxRetriever()

  private def makeBoxRetriever(
                                cato01Value: Int = 500,
                                cp997NIValue: Option[Int] = Some(200),
                                cp998: Option[Int] = None
                              ) = {

    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cato01()).thenReturn(CATO01(cato01Value))
    when(retriever.cp997NI()).thenReturn(CP997NI(cp997NIValue))
    when(retriever.cp998()).thenReturn(CP998(cp998))
    retriever
  }

  "CATO23" should{
    "be calculated" in{
      CATO23.calculate(makeBoxRetriever()) shouldBe CATO23(300)
    }

    "be calculated with CPQ19 set to yes" in{
      CATO23.calculate(makeBoxRetriever(cp998 = Some(300))) shouldBe CATO23(0)
    }
  }

}
