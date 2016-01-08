/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E180(value: Option[Boolean]) extends CtBoxIdentifier("Qualifying investments and loans") with CtOptionalBoolean with Input

object E180 extends ValidatableBox[CT600EBoxRetriever] {

  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    if (retrieveE180().value.isEmpty) {
      Set(CtValidation(boxId = Some("E180"), errorMessageKey = "error.E180.required"))
    } else {
      Set.empty
    } ++ ((retrieveE180(), retrieveE185()) match {
      case (E180(Some(true)), E185(Some(_))) => Set(CtValidation(boxId = Some("E180"), errorMessageKey = "error.E180.cannot.be.true.when.E185.has.value"))
      case _ => Set.empty
    })
  }

}