package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP298(value: Option[Int]) extends CtBoxIdentifier with CtOptionalInteger

object CP298 extends Calculated[CP298, ComputationsBoxRetriever] with SBACalculator {

  def getCostOfAllBuildings(computationsBoxRetriever: ComputationsBoxRetriever) = computationsBoxRetriever.sba01().buildings.map(_.cost)

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP298 = CP298(sumAmount(getCostOfAllBuildings(boxRetriever)))
}
