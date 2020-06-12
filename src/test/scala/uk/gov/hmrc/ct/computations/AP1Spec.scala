/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class AP1Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = mock[ComputationsBoxRetriever]

  testBoxIsZeroOrPositive("AP1", v => AP1(v))
}
