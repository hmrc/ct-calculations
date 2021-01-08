/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, CtTypeConverters}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP999(value: Int) extends CtBoxIdentifier(name = "Total Donations") with CtInteger

object CP999 extends Calculated[CP999, ComputationsBoxRetriever] with CtTypeConverters {

  override def calculate(retriever: ComputationsBoxRetriever): CP999 = {
    CP999(calculateCharitableDonations(retriever) + calculateGrassrootsDonations(retriever))
  }

  private def calculateCharitableDonations(retriever: ComputationsBoxRetriever) = {

    retriever.cpQ21().value match {
      case Some(true) => retriever.cp302() + retriever.cp301()
      case _ => 0
    }
  }

  private def calculateGrassrootsDonations(retriever: ComputationsBoxRetriever) = {
    retriever.cpQ321().value match {
      case Some(true) => retriever.cp3020() + retriever.cp3010()
      case _ => 0
    }
  }
}
