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
import uk.gov.hmrc.ct.accounts.frsse2008.boxes._
import uk.gov.hmrc.ct.accounts.frsse2008.boxes.micro._
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

  override def ac492(): AC492 = ???

  override def ac493(): AC493 = ???

  override def ac494(): AC494 = ???

  override def generateValues: Map[String, CtValue[_]] = ???

  override def acq8001(): ACQ8001 = ???

  override def acq8003(): ACQ8003 = ???

  override def acq8004(): ACQ8004 = ???

  override def acq8101(): ACQ8101 = ???

  override def acq8102(): ACQ8102 = ???

  override def acq8103(): ACQ8103 = ???

  override def acq8104(): ACQ8104 = ???

  override def acq8105(): ACQ8105 = ???

  override def acq8106(): ACQ8106 = ???

  override def acq8107(): ACQ8107 = ???

  override def acq8108(): ACQ8108 = ???

  override def acq8109(): ACQ8109 = ???

  override def acq8110(): ACQ8110 = ???

  override def acq8111(): ACQ8111 = ???

  override def acq8112(): ACQ8112 = ???

  override def acq8113(): ACQ8113 = ???

  override def acq8114(): ACQ8114 = ???

  override def acq8115(): ACQ8115 = ???

  override def acq8116(): ACQ8116 = ???

  override def acq8117(): ACQ8117 = ???

  override def acq8118(): ACQ8118 = ???

  override def acq8119(): ACQ8119 = ???

  override def acq8120(): ACQ8120 = ???

  override def acq8121(): ACQ8121 = ???

  override def acq8122(): ACQ8122 = ???

  override def acq8123(): ACQ8123 = ???

  override def acq8124(): ACQ8124 = ???

  override def acq8999(): ACQ8999 = ???

  override def acq8125(): ACQ8125 = ???

  override def acq8126(): ACQ8126 = ???

  override def acq8130(): ACQ8130 = ???

  override def acq8200(): ACQ8200 = ???

  override def acq8201(): ACQ8201 = ???

  override def acq8210(): ACQ8210 = ???

  override def acq8211(): ACQ8211 = ???

  override def acq8212(): ACQ8212 = ???

  override def acq8213(): ACQ8213 = ???

  override def acq8214(): ACQ8214 = ???
}
