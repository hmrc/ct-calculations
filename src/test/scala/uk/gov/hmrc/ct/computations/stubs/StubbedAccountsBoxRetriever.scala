/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.stubs

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait StubbedAccountsBoxRetriever extends AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  override def retrieveAC1(): AC1 = ???

  override def retrieveAC19(): AC19 = ???

  override def retrieveAC31(): AC31 = ???

  override def retrieveAC34(): AC34 = ???

  override def retrieveAC13(): AC13 = ???

  override def retrieveAC28(): AC28 = ???

  override def retrieveAC4(): AC4 = ???

  override def retrieveAC22(): AC22 = ???

  override def retrieveAC21(): AC21 = ???

  override def retrieveAC206(): AC206 = ???

  override def retrieveAC39(): AC39 = ???

  override def retrieveAC18(): AC18 = ???

  override def retrieveAC30(): AC30 = ???

  override def retrieveAC12(): AC12 = ???

  override def retrieveAC15(): AC15 = ???

  override def retrieveAC3(): AC3 = ???

  override def retrieveAC29(): AC29 = ???

  override def retrieveAC205(): AC205 = ???

  override def retrieveAC14(): AC14 = ???

  override def retrieveAC20(): AC20 = ???

  override def retrieveAC35(): AC35 = ???

  override def retrieveAC38(): AC38 = ???

  override def retrieveAC23(): AC23 = ???

  override def generateValues: Map[String, CtValue[_]] = ???
}
