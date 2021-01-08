/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP986(value: Int) extends CtBoxIdentifier("Take this away from profit") with CtInteger

object CP986 extends Calculated[CP986, ComputationsBoxRetriever] {

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP986 = {
    CP986(
      boxRetriever.cp980().orZero + boxRetriever.cp981().orZero + boxRetriever.cp982().orZero
    )
  }

}
