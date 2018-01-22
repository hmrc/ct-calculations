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

import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever

package object nir {

  def mayHaveNirLosses(computationsBoxRetriever: ComputationsBoxRetriever): Boolean = computationsBoxRetriever match {
    case retriever: ComputationsBoxRetriever with AboutThisReturnBoxRetriever => retriever.b5().orFalse || retriever.cpQ117().orFalse
    case retriever: ComputationsBoxRetriever => retriever.cpQ117().orFalse
    case _ => false
  }


}
