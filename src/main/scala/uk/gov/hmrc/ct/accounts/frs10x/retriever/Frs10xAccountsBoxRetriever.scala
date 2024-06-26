/*
 * Copyright 2024 HM Revenue & Customs
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

import uk.gov.hmrc.ct.accounts.frs10x.boxes.AC24
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC15, AC16, AC17, AC25, AC8081, AC8082, AC8083, AC8088}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs10xAccountsBoxRetriever extends AccountsBoxRetriever {
  self: FilingAttributesBoxValueRetriever =>

  def ac15(): AC15

  def ac16(): AC16

  def ac17(): AC17

  def ac24(): AC24

  def ac25(): AC25

  def ac8081(): AC8081

  def ac8082(): AC8082

  def ac8083(): AC8083

  def ac8088(): AC8088
}
