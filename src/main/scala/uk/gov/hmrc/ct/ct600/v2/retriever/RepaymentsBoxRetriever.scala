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

trait RepaymentsBoxRetriever extends BoxRetriever {

  def retrieveB140(): B140

  def retrieveB149(): B149

  def retrieveB150(): B150

  def retrieveB151(): B151

  def retrieveB152(): B152

  def retrieveB153(): B153

  def retrieveB156(): B156

  def retrieveB1571(): B1571

  def retrieveB1572(): B1572

  def retrieveB1573(): B1573

  def retrieveB1574(): B1574

  def retrieveB1575(): B1575

  def retrieveB158(): B158
}
