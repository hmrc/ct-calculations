/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
