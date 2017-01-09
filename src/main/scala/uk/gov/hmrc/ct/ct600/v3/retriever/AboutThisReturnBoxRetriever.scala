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

package uk.gov.hmrc.ct.ct600.v3.retriever

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.ct600e.v3.B115
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.ct600j.v3.B140

trait AboutThisReturnBoxRetriever extends BoxRetriever {

  self: AccountsBoxRetriever =>

  def b30(): B30 = {
    this match {
      case computationsBoxRetriever: ComputationsBoxRetriever => B30(computationsBoxRetriever.cp1())
      case charityRetriever: CT600EBoxRetriever => B30(charityRetriever.e3())
      case _ => throw new IllegalStateException(s"This box retriever [$this] does not have an AP start date.")
    }
  }

  def b35(): B35 = {
    this match {
      case compsRet : ComputationsBoxRetriever => B35(compsRet.cp2())
      case charityRetriever: CT600EBoxRetriever => B35(charityRetriever.e4())
      case _ => throw new IllegalStateException(s"This box retriever [$this] does not have an AP start date.")
    }
  }

  def b40(): B40

  def b45(): B45 = B45.calculate(this)

  def b45Input(): B45Input

  def b50(): B50 = B50.calculate(this)

  def b55(): B55

  def b65(): B65

  def b80A(): B80A

  def b85A(): B85A

  def b90A(): B90A

  def b95(): B95 = {
    this match {
      case r: CT600ABoxRetriever => B95(r.lpq01)
      case _ => B95(false)
    }
  }

  def b115(): B115 = {
    this match {
      case r: CT600EBoxRetriever => B115(true)
      case _ => B115(false)
    }
  }

  def b140(): B140 = B140(b65())
}
