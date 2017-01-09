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

package uk.gov.hmrc.ct.ct600a.v2.retriever

import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600a.v2._

trait CT600ABoxRetriever extends ComputationsBoxRetriever {

  self: AccountsBoxRetriever =>

  def lpq03(): LPQ03

  def lpq04(): LPQ04

  def lpq05(): LPQ05

  def lpq06(): LPQ06

  def lpq07(): LPQ07

  def lpq08(): LPQ08

  def lpq09(): LPQ09

  def lpq10(): LPQ10

  def lp02(): LP02

  def lp03(): LP03

  def lp04(): LP04

  def lpq01(): LPQ01 = LPQ01.calculate(this)

  def a1(): A1 = A1(lpq09())

  def a2() = A2.calculate(this)

  def a3() = A3.calculate(this)

  def a4() = A4.calculate(this)

  def a5() = A5.calculate(this)

  def a6() = A6.calculate(this)

  def a7() = A7.calculate(this)

  def a8() = A8.calculate(this)

  def a8Inverse() = A8Inverse.calculate(this)

  def a9() = A9.calculate(this)

  def a9Inverse() = A9Inverse.calculate(this)

  def a10() = A10.calculate(this)

  def a10Inverse() = A10Inverse.calculate(this)

  def a11() = A11.calculate(this)

  def a11Inverse() = A11Inverse.calculate(this)

  def a12() = A12.calculate(this)

  def a13() = A13.calculate(this)
}
