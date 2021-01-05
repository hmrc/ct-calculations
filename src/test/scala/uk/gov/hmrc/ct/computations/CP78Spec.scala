/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


class CP78Spec extends WordSpec with MachineryAndPlantCalculator with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] {

  val boxRetriever = mock[ComputationsBoxRetriever]

  override def setUpMocks = {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
    when(boxRetriever.cp666()).thenReturn(CP666(Some(333)))
  }

  testBoxIsZeroOrPositive("CP78", CP78.apply)

}
