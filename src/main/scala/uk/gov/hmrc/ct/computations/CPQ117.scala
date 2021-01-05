/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.losses.lossReform2017Applies
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever
import uk.gov.hmrc.ct.computations.losses._

case class CPQ117(value: Option[Boolean])
  extends CtBoxIdentifier(name = "Did any of these losses arise from NIR activity?")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever ): Set[CtValidation] = {
    val isNorthernIrelandActive = boxRetriever match {
      case a: AboutThisReturnBoxRetriever => northernIrelandJourneyActive(a)
      case _ => false
    }

    val lossesReformApplies = lossReform2017Applies(boxRetriever.cp2())
    collectErrors(
      requiredErrorIf( (value.isEmpty && isNorthernIrelandActive) &&
        boxRetriever.cpQ17().isTrue && lossesReformApplies),
      cannotExistErrorIf(value.nonEmpty &&
        (boxRetriever.cpQ17().isFalse || !lossesReformApplies || !isNorthernIrelandActive))
    )
  }
}
