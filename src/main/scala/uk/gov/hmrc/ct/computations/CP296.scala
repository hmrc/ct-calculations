/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.utils.DateImplicits._

case class CP296(value: Option[Int]) extends CtBoxIdentifier(name = "Total Structure and Building Allowance") with CtOptionalInteger

object CP296 extends Calculated[CP296, ComputationsBoxRetriever] with SBACalculator {

  def getCostForEachBuilding(boxRetriever: ComputationsBoxRetriever): List[Option[Int]] = {
    boxRetriever.sba01().buildings.filter(building => {
        building.nonResidentialActivityStart match {
          case None => false
          case Some(date) => date >= boxRetriever.cp1().value
        }
      }
    ).map(building => building.cost)
  }
  
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP296 = {
    CP296(sumAmount(getCostForEachBuilding(boxRetriever)))
  }
}