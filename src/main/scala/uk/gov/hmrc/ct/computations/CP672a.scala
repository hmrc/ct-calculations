/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class CP672a(value: Option[Int]) extends CtBoxIdentifier(name = "Out Of Proceeds from disposals from main pool")  with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {

  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    val hasCompanyCeasedTrading = boxRetriever.cpQ8().isTrue

    val max = {
      if (hasCompanyCeasedTrading) {
        val proceedsFromDisposals = boxRetriever.cp84().orZero

        exceedsMax(value, proceedsFromDisposals, "CP84.exceeds.max")
      }
      else {
        val proceedsFromDisposalsFromMainPool = boxRetriever.cp672().orZero

        exceedsMax(value, proceedsFromDisposalsFromMainPool, "CP672.exceeds.max")
      }
    }

    collectErrors(
      validateZeroOrPositiveInteger(),
      max
    )

  }
}
object CP672a {

  def apply(value: Int): CP672a = CP672a(Some(value))
}


