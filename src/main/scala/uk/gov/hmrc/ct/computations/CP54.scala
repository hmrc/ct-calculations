/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TotalAdditionsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP54(value: Int) extends CtBoxIdentifier(name = "Total Additions") with CtInteger

object CP54 extends Calculated[CP54, ComputationsBoxRetriever] with TotalAdditionsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP54 = {
    totalAdditionsCalculation(
      cp46 = fieldValueRetriever.cp46(),
      cp47 = fieldValueRetriever.cp47(),
      cp48 = fieldValueRetriever.cp48(),
      cp49 = fieldValueRetriever.cp49(),
      cp51 = fieldValueRetriever.cp51(),
      cp52 = fieldValueRetriever.cp52(),
      cp53 = fieldValueRetriever.cp53(),
      cp503 = fieldValueRetriever.cp503(),
      cp980 = fieldValueRetriever.cp980(),
      cp981 = fieldValueRetriever.cp981(),
      cp982 = fieldValueRetriever.cp982())
  }
}
