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

package uk.gov.hmrc.ct.ct600.v3.retriever

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v3._

trait RepaymentsBoxRetriever extends BoxRetriever {

  def b860(): B860

  def b865(): B865 = {
    this match {
      case br: CT600BoxRetriever => B865(br.b605())
      case _ => B865(None)
    }
  }

  def b870(): B870 = {
    this match {
      case br: CT600BoxRetriever => B870(br.b520())
      case _ => B870(None)
    }
  }

  def b920(): B920

  def b925(): B925

  def b930(): B930

  def b935(): B935

  def b940(): B940

  def b945(): B945

  def b950(): B950 = B950.calculate(this)

  def b955(): B955

  def b960(): B960

  def b960_1(): B960_1

  def b960_2(): B960_2

  def b960_3(): B960_3

  def b960_5(): B960_5

  def b965(): B965

  def b970(): B970

  def payeeQ1(): PAYEEQ1

  def repaymentsQ1(): REPAYMENTSQ1
}
