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

package uk.gov.hmrc.ct.accounts.frsse2008.stubs

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frsse2008._
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait StubbedAccountsBoxRetriever extends Frsse2008AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  override def companyAddress(): CompanyAddress = ???

  override def ac1(): AC1 = ???

  override def ac2(): AC2 = ???

  override def ac19(): AC19 = ???

  override def ac31(): AC31 = ???

  override def ac34(): AC34 = ???

  override def ac13(): AC13 = ???

  override def ac28(): AC28 = ???

  override def ac4(): AC4 = ???

  override def ac22(): AC22 = ???

  override def ac21(): AC21 = ???

  override def ac39(): AC39 = ???

  override def ac18(): AC18 = ???

  override def ac30(): AC30 = ???

  override def ac12(): AC12 = ???

  override def ac15(): AC15 = ???

  override def ac3(): AC3 = ???

  override def ac29(): AC29 = ???

  override def ac14(): AC14 = ???

  override def ac20(): AC20 = ???

  override def ac35(): AC35 = ???

  override def ac38(): AC38 = ???

  override def ac23(): AC23 = ???

  override def ac405(): AC405 = ???

  override def ac406(): AC406 = ???

  override def ac410(): AC410 = ???

  override def ac411(): AC411 = ???

  override def ac415(): AC415 = ???

  override def ac416(): AC416 = ???

  override def ac420(): AC420 = ???

  override def ac421(): AC421 = ???

  override def ac425(): AC425 = ???

  override def ac426(): AC426 = ???

  override def ac206(): AC206 = ???

  override def ac205(): AC205 = ???

  override def generateValues: Map[String, CtValue[_]] = ???
}
