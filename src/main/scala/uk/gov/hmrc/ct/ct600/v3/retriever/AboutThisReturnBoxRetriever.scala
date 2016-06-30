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

package uk.gov.hmrc.ct.ct600.v3.retriever

import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.ct600e.v3.B115
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.ct600j.v3.B140

trait AboutThisReturnBoxRetriever extends BoxRetriever {

  self: Frsse2008AccountsBoxRetriever =>

  def retrieveB30(): B30 = {
    this match {
      case computationsBoxRetriever: ComputationsBoxRetriever => B30(computationsBoxRetriever.retrieveCP1())
      case charityRetriever: CT600EBoxRetriever => B30(charityRetriever.retrieveE3())
      case _ => throw new IllegalStateException(s"This box retriever [$this] does not have an AP start date.")
    }
  }

  def retrieveB35(): B35 = {
    this match {
      case compsRet : ComputationsBoxRetriever => B35(compsRet.retrieveCP2())
      case charityRetriever: CT600EBoxRetriever => B35(charityRetriever.retrieveE4())
      case _ => throw new IllegalStateException(s"This box retriever [$this] does not have an AP start date.")
    }
  }

  def retrieveB40(): B40

  def retrieveB45(): B45 = B45.calculate(this)

  def retrieveB45Input(): B45Input

  def retrieveB50(): B50 = B50.calculate(this)

  def retrieveB55(): B55

  def retrieveB65(): B65

  def retrieveB80A(): B80A

  def retrieveB85A(): B85A

  def retrieveB90A(): B90A

  def retrieveB95(): B95 = {
    this match {
      case r: CT600ABoxRetriever => B95(r.retrieveLPQ01)
      case _ => B95(false)
    }
  }

  def retrieveB115(): B115 = {
    this match {
      case r: CT600EBoxRetriever => B115(true)
      case _ => B115(false)
    }
  }

  def retrieveB140(): B140 = B140(retrieveB65())
}
