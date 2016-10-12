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

package uk.gov.hmrc.ct.accounts.frs102.retriever

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait FullAccountsBoxRetriever extends Frs102AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def ac13(): AC13

  def ac14(): AC14

  def ac15(): AC15

  def ac16(): AC16

  def ac17(): AC17

  def ac22(): AC22

  def ac23(): AC23
}
