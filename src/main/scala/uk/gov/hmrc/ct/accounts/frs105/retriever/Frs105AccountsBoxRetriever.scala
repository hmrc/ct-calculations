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

package uk.gov.hmrc.ct.accounts.frs105.retriever

import uk.gov.hmrc.ct.accounts.frs105.boxes._
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs105AccountsBoxRetriever extends Frs10xAccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def ac13(): AC13

  def ac34(): AC34

  def ac35(): AC35

  def ac58(): AC58

  def ac59(): AC59

  def ac60(): AC60 = AC60.calculate(this)

  def ac61(): AC61 = AC61.calculate(this)

  def ac62(): AC62 = AC62.calculate(this)

  def ac63(): AC63 = AC63.calculate(this)

  def ac64(): AC64

  def ac65(): AC65

  def ac66(): AC66

  def ac67(): AC67

  def ac68(): AC68 = AC68.calculate(this)

  def ac69(): AC69 = AC69.calculate(this)

  def ac405(): AC405

  def ac406(): AC406

  def ac410(): AC410

  def ac411(): AC411

  def ac415(): AC415

  def ac416(): AC416

  def ac420(): AC420

  def ac421(): AC421

  def ac425(): AC425

  def ac426(): AC426

  def ac435(): AC435 = AC435.calculate(this)

  def ac436(): AC436 = AC436.calculate(this)

  def ac450(): AC450

  def ac451(): AC451

  def ac455(): AC455

  def ac456(): AC456

  def ac460(): AC460

  def ac461(): AC461

  def ac465(): AC465

  def ac466(): AC466

  def ac470(): AC470

  def ac471(): AC471

  def ac490(): AC490

  def ac491(): AC491

  def ac8087(): AC8087

  def ac7991(): AC7991

  def ac7992(): AC7992

  def ac7995(): AC7995

  def ac7997(): AC7997

 }
