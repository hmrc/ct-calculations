/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600ei.v3.retriever


import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.ct600ei.v3.{DIT001, DIT002, DIT003}

trait CT600EiBoxRetriever extends BoxRetriever {

  self: FilingAttributesBoxValueRetriever  =>

  def dit001(): DIT001

  def dit002(): DIT002

  def dit003(): DIT003

}
