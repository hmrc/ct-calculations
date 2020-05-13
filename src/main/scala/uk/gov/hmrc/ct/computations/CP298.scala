/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP298(value: Option[Int]) extends CtBoxIdentifier("Total Relief Claimable") with CtOptionalInteger

object CP298 extends Calculated[CP298, ComputationsBoxRetriever] with SBACalculator {

  def getTotalRelief(computationsBoxRetriever: ComputationsBoxRetriever): List[Option[Int]] = {
    computationsBoxRetriever.sba01().buildings.map(building =>

      getAmountClaimableForSBA(computationsBoxRetriever.cp1().value, computationsBoxRetriever.cp2().value, building.earliestWrittenContract, building.cost)
    )
  }

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP298 = CP298(sumAmount(getTotalRelief(boxRetriever)))
}
