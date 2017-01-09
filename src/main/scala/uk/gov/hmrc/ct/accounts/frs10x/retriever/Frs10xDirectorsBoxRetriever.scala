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

package uk.gov.hmrc.ct.accounts.frs10x.retriever

import uk.gov.hmrc.ct.accounts.frs102._
import uk.gov.hmrc.ct.accounts.frs102.helper.DirectorsReportEnabled
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{Directors, _}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs10xDirectorsBoxRetriever extends AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def ac8021(): AC8021

  def directors(): Directors

  def ac8033(): AC8033

  def ac8023(): AC8023

  def acQ8003(): ACQ8003

  def acQ8009(): ACQ8009
  
  def ac8051(): AC8051

  def ac8052(): AC8052

  def ac8053(): AC8053

  def ac8054(): AC8054

  def ac8899(): AC8899

  def directorsReportEnabled(): DirectorsReportEnabled = DirectorsReportEnabled.calculate(this)
}
