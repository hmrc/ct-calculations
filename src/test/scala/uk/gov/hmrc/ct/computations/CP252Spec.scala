/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


class CP252Spec extends WordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] with BeforeAndAfterEach {

  val boxRetriever = mock[ComputationsBoxRetriever]

  override def setUpMocks = {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
    when(boxRetriever.cp79()).thenReturn(CP79(Some(333)))
    when(boxRetriever.cp1()).thenReturn(CP1(new LocalDate("2019-04-01")))
  }

  override def beforeEach = setUpMocks

  testBoxIsZeroOrPositive("CP252", CP252.apply)

  testCannotExistWhen("CP252", CP252.apply) {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(true)))
    when(boxRetriever.cp79()).thenReturn(CP79(Some(333))).getMock[ComputationsBoxRetriever]
  }

  "fail validation when higher than cp79" in {
    CP252(Some(444)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP252"), "error.CP252.exceedsRelevantFYAExpenditure", None))
  }

  "fail validation AP past April 2020" in {
    val thisBoxRetriever = mock[ComputationsBoxRetriever]
    when(thisBoxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
    when(thisBoxRetriever.cp79()).thenReturn(CP79(Some(333)))
    when(thisBoxRetriever.cp1()).thenReturn(CP1(new LocalDate("2020-04-01")))

    CP252(Some(333)).validate(thisBoxRetriever) shouldBe Set(CtValidation(Some("CP252"), "error.CP252.cannot.exist", None))
  }
}
