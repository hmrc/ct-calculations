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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, CtTypeConverters}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP999(value: Int) extends CtBoxIdentifier(name = "Total Donations") with CtInteger

object CP999 extends Calculated[CP999, ComputationsBoxRetriever] with CtTypeConverters {

  override def calculate(retriever: ComputationsBoxRetriever): CP999 = {
    CP999(calculateCharitableDonations(retriever) + calculateGrassrootsDonations(retriever))
  }

  private def calculateCharitableDonations(retriever: ComputationsBoxRetriever) = {

    retriever.cpQ21().value match {
      case Some(true) => retriever.cp302() + retriever.cp301()
      case _ => 0
    }
  }

  private def calculateGrassrootsDonations(retriever: ComputationsBoxRetriever) = {
    retriever.cpQ321().value match {
      case Some(true) => retriever.cp3020() + retriever.cp3010()
      case _ => 0
    }
  }
}
