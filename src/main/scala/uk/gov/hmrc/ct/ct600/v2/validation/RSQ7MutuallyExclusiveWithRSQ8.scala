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

package uk.gov.hmrc.ct.ct600.v2.validation

import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600.v2.retriever.ReturnStatementsBoxRetriever

trait RSQ7MutuallyExclusiveWithRSQ8 {

  private def error(boxId: String) = CtValidation(Some(boxId), s"error.$boxId.mutuallyExclusive")

  def validateMutualExclusivity(boxRetriever: ReturnStatementsBoxRetriever): Set[CtValidation] =
    (boxRetriever.rsq7().value, boxRetriever.rsq8().value) match {
      case (Some(rsq7), Some(rsq8)) if rsq7 && rsq8 => Set(error("RSQ7"), error("RSQ8"))
      case _ => Set.empty
    }

}
