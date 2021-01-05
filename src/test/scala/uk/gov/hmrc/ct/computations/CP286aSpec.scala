/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP286aSpec extends WordSpec with Matchers with MockitoSugar with NorthernIrelandRateValidation {

  "CP286a" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
    "when empty and Northern Ireland journey is active" when {
      val CP286a = new CP286a(None){
        override val boxId = "CP286a"
        override def mayHaveNirLosses (boxRetriever:ComputationsBoxRetriever): Boolean = true
      }

      "pass validation when CPQ18 is empty" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(None))
        when(boxRetriever.cp286()).thenReturn(CP286(None))
        when(boxRetriever.cp286b()).thenReturn(CP286b(None))
        CP286a.validate(boxRetriever) shouldBe empty
      }

      "pass validation when CPQ18 is false" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(false)))
        when(boxRetriever.cp286()).thenReturn(CP286(None))
        when(boxRetriever.cp286b()).thenReturn(CP286b(None))
        CP286a.validate(boxRetriever) shouldBe empty
      }

      "fail validation when CPQ18 is true" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        when(boxRetriever.cp286()).thenReturn(CP286(None))
        when(boxRetriever.cp286b()).thenReturn(CP286b(None))

        CP286a.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286a"), "error.CP286a.required"))
      }
    }

    "when has value" when {
      "pass validation when cpQ18 is true and value is < limit" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        when(boxRetriever.cp286()).thenReturn(CP286(Some(1000)))
        when(boxRetriever.cp286b()).thenReturn(CP286b(Some(500)))

        val CP286a = new CP286a(Some(499))

        CP286a.validate(boxRetriever) shouldBe empty
      }
    }

    "pass validation when CPQ18 is true and value == limit" in {
      when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
      when(boxRetriever.cp286()).thenReturn(CP286(Some(1000)))
      when(boxRetriever.cp286b()).thenReturn(CP286b(Some(500)))
      val CP286a = new CP286a(Some(500))

      CP286a.validate(boxRetriever) shouldBe empty
    }

    "pass validation when CPQ18 is true, cp286 is 1000, cp286b is 500 and value == 0" in {
      when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
      when(boxRetriever.cp286()).thenReturn(CP286(Some(1000)))
      when(boxRetriever.cp286b()).thenReturn(CP286b(Some(500)))
      val CP286a = new CP286a(Some(0))

      CP286a.validate(boxRetriever) shouldBe empty
    }

    "fail validation when CPQ18 is true, cp286 is 1000, cp286b is 0 and value > cp286" in {
      when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
      when(boxRetriever.cp286()).thenReturn(CP286(Some(1000)))
      when(boxRetriever.cp286b()).thenReturn(CP286b(Some(0)))
      val CP286a = new CP286a(Some(1001))

      CP286a.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286a"),"error.CP286a.outOfRange",Some(List("0", "1,000"))))
    }

  }
}
