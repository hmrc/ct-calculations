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

package uk.gov.hmrc.ct.accounts.frs105.retriever

import uk.gov.hmrc.ct.accounts.frs105.boxes._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs105AccountsBoxRetriever extends AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def ac450(): AC450

  def ac451(): AC451

  def ac460(): AC460

  def ac461(): AC461

  def ac455(): AC455

  def ac456(): AC456

  def ac465(): AC465

  def ac466(): AC466

  def ac58(): AC58

  def ac59(): AC59

  def ac60(): AC60

  def ac61(): AC61

  def ac64(): AC64

  def ac65(): AC65

  def ac66(): AC66

  def ac67(): AC67

  def ac470(): AC470

  def ac471(): AC471

 }
