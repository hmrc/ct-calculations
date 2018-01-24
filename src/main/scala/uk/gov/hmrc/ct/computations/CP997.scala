/*
 * Copyright 2018 HM Revenue & Customs
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
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP997(value: Option[Int]) extends CtBoxIdentifier("Losses from previous AP after 01/04/2017 set against non trading profits this AP")
  with CtOptionalInteger
  with Input

object CP997 extends Calculated[CP997, ComputationsBoxRetriever] with NorthernIrelandRateValidation {

  def apply(int: Int): CP997 = CP997(Some(int))

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP997 = {
    if (boxRetriever.cato01().value > 0) {
      val retriverVal = boxRetriever.cp997d().orZero + boxRetriever.cp997e().orZero
      CP997(
        if (mayHaveNirLosses(boxRetriever) && retriverVal > 0 ) Some(boxRetriever.cp997d().orZero + boxRetriever.cp997e().orZero)
        else boxRetriever.cp997b.value
      )
    } else CP997(None)
  }

}
