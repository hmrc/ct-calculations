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

package uk.gov.hmrc.ct.ct600e.v3.retriever

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600e.v3._

trait CT600EBoxRetriever extends BoxRetriever {

  def retrieveE50(): E50

  def retrieveE55(): E55

  def retrieveE60(): E60

  def retrieveE65(): E65

  def retrieveE70(): E70

  def retrieveE75(): E75

  def retrieveE80(): E80

  def retrieveE85(): E85

  def retrieveE90(): E90 = E90.calculate(this)
}
