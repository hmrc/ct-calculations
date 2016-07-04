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

package uk.gov.hmrc.ct.accounts.frs10x.retriever


import uk.gov.hmrc.ct.accounts.frs10x._
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, BoxValues, FilingAttributesBoxValueRetriever}

trait Frs10xAccountsBoxRetriever extends BoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def retrieveAC8023(): AC8023

  def retrieveAC8051(): AC8051

  def retrieveAC8052(): AC8052

  def retrieveAC8053(): AC8053

  def retrieveAC8054(): AC8054
}
