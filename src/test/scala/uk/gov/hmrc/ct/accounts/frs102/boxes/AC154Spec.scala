

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC154Spec extends WordSpec with Matchers with MockitoSugar {

  "AC154" should {

    "have no errors if AC58 has the same value" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(50)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC154(Some(50)).validate(boxRetriever) shouldBe empty
    }


    "have no errors if AC58 and AC154 are both None" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(None))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC154(None).validate(boxRetriever) shouldBe empty
    }

    "have no errors if AC58 and AC154 are both 0" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(0)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC154(Some(0)).validate(boxRetriever) shouldBe empty
    }

    "return error if AC58 is 0 and AC154 is None" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(0)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC154(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.creditors.within.one.year.note.current.total.not.equal.balance.sheet"))
    }

    "return error if AC58 is 0 and AC154 is 50" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(0)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC154(Some(50)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.creditors.within.one.year.note.current.total.not.equal.balance.sheet"))
    }

  }

}
