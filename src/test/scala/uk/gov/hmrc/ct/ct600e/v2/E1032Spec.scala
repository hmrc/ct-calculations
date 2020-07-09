/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._

class E1032Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter{

  val boxRetriever = mock[CT600EBoxRetriever]
  val NOW = DateHelper.now()
  val APEnd = NOW.minusMonths(1)

  before{
    when(boxRetriever.e1022()).thenReturn(E1022(APEnd))
  }

  "E1032" should {
    "validate" when {
      "false when is empty" in {
        E1032(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("E1032"), errorMessageKey = "error.E1032.required"))
      }

      val INVALID_DATE_ERROR_MESSAGE: Set[CtValidation] = Set(CtValidation(Some("E1032"), s"error.E1032.not.betweenInclusive", Some(Seq(toErrorArgsFormat(APEnd), toErrorArgsFormat(NOW)))))

      "false when date before AP end date" in {
        E1032(Some(APEnd.minusDays(1))).validate(boxRetriever) shouldBe INVALID_DATE_ERROR_MESSAGE
      }
      "false when date after current date" in {
        E1032(Some(NOW.plusDays(1))).validate(boxRetriever) shouldBe INVALID_DATE_ERROR_MESSAGE
      }
      "true when date is current date" in {
        E1032(Some(NOW)).validate(boxRetriever) shouldBe Set()
      }
      "true when date is AP end date" in {
        E1032(Some(APEnd)).validate(boxRetriever) shouldBe Set()
      }
      "true when date is between now and AP end date" in {
        E1032(Some(APEnd.plusDays(1))).validate(boxRetriever) shouldBe Set()
      }
    }
  }

}
