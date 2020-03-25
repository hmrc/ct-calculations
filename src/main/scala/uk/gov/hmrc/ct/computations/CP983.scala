/*
 * Copyright 2020 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP983(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover from off-payroll working") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever]   {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val cp980 = boxRetriever.cp980()
    val cp981 = boxRetriever.cp981()
    val cp982 = boxRetriever.cp982()

      validateValueEqualToAdditions(cp980, cp981, cp982)
  }

  def validateValueEqualToAdditions(cp980: CP980, cp981: CP981, cp982: CP982): Set[CtValidation] = {
    if(cp980.value.getOrElse(0) + cp981.value.getOrElse(0) + cp982.value.getOrElse(0) != value.getOrElse(0)){
      Set(CtValidation(Some(id), s"error.$id.not.equalToAdditions"))
    } else {
      Set.empty
    }
  }
}

object CP983 {
  def apply(int: Int): CP983 = CP983(Some(int))
}