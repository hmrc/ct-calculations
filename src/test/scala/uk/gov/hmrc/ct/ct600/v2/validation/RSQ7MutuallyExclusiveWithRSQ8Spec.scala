/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2.validation

import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600.v2.{RSQ7, RSQ8}
import uk.gov.hmrc.ct.ct600.v2.retriever.ReturnStatementsBoxRetriever

class RSQ7MutuallyExclusiveWithRSQ8Spec extends WordSpec with Matchers with MockitoSugar with RSQ7MutuallyExclusiveWithRSQ8 {

  "validateMutualExclusivity" should {

    "return no errors if both RSQ7 & RSQ8 are empty" in {

      val retriever = mock[ReturnStatementsBoxRetriever]

      when(retriever.rsq7()).thenReturn(RSQ7(None))
      when(retriever.rsq8()).thenReturn(RSQ8(None))

      validateMutualExclusivity(retriever) shouldBe empty
    }

    "return an error if both RSQ7 & RSQ8 are true" in {

      val retriever = mock[ReturnStatementsBoxRetriever]

      when(retriever.rsq7()).thenReturn(RSQ7(Some(true)))
      when(retriever.rsq8()).thenReturn(RSQ8(Some(true)))

      val expectedErrors = Set(CtValidation(Some("RSQ7"), "error.RSQ7.mutuallyExclusive"), CtValidation(Some("RSQ8"), "error.RSQ8.mutuallyExclusive"))
      validateMutualExclusivity(retriever) shouldBe expectedErrors
    }

    "return no errors if both RSQ7 is false & RSQ8 is true" in {

      val retriever = mock[ReturnStatementsBoxRetriever]

      when(retriever.rsq7()).thenReturn(RSQ7(Some(false)))
      when(retriever.rsq8()).thenReturn(RSQ8(Some(true)))

      validateMutualExclusivity(retriever) shouldBe Set.empty
    }

    "return no errors if both RSQ7 is true & RSQ8 is empty" in {

      val retriever = mock[ReturnStatementsBoxRetriever]

      when(retriever.rsq7()).thenReturn(RSQ7(Some(true)))
      when(retriever.rsq8()).thenReturn(RSQ8(None))

      validateMutualExclusivity(retriever) shouldBe Set.empty
    }

  }

}
