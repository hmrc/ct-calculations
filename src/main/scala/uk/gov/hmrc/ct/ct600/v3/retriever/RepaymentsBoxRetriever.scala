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

package uk.gov.hmrc.ct.ct600.v3.retriever

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v3._

trait RepaymentsBoxRetriever extends BoxRetriever {

  def retrieveB860(): B860

  def retrieveB865(): B865 = {
    this match {
      case br: CT600BoxRetriever => B865(br.retrieveB605())
      case _ => B865(None)
    }
  }

  def retrieveB870(): B870 = {
    this match {
      case br: CT600BoxRetriever => B870(br.retrieveB520())
      case _ => B870(None)
    }
  }

  def retrieveB920(): B920

  def retrieveB925(): B925

  def retrieveB930(): B930

  def retrieveB935(): B935

  def retrieveB940(): B940

  def retrieveB945(): B945

  def retrieveB950(): B950 = B950.calculate(this)

  def retrieveB955(): B955

  def retrieveB960(): B960

  def retrieveB960_1(): B960_1

  def retrieveB960_2(): B960_2

  def retrieveB960_3(): B960_3

  def retrieveB960_5(): B960_5

  def retrieveB965(): B965

  def retrieveB970(): B970

  def retrievePAYEEQ1(): PAYEEQ1

  def retrieveREPAYMENTSQ1(): REPAYMENTSQ1
}
