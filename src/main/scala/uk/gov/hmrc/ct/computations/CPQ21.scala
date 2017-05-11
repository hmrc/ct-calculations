/*
 * Copyright 2017 HM Revenue & Customs
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
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ21(value: Option[Boolean]) extends CtBoxIdentifier(name = "Donations made?")
  with CtOptionalBoolean with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateBooleanAsMandatory("CPQ21", this),
      failIf(isTrue && !boxRetriever.cp301().isPositive && !boxRetriever.cp302().isPositive && !boxRetriever.cp303().isPositive) {
        Set(CtValidation(None, "error.CPQ21.no.charitable.donations"))
      },
      failIf(isTrue && boxRetriever.cp301().plus(boxRetriever.cp302()) > boxRetriever.cato13()) {
        Set(CtValidation(None, "error.CPQ21.cannot.exceed.net.profit"))
      }
    )
  }
}


