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

package uk.gov.hmrc.ct.ct600.v2.retriever

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v2._

trait ReturnStatementsBoxRetriever extends BoxRetriever {

  def retrieveRSQ1(): RSQ1

  def retrieveRSQ2(): RSQ2

  def retrieveRSQ3(): RSQ3

  def retrieveRSQ4(): RSQ4

  def retrieveRSQ7(): RSQ7

  def retrieveRSQ8(): RSQ8
}
