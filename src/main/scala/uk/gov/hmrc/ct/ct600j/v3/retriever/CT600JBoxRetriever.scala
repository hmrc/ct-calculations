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

package uk.gov.hmrc.ct.ct600j.v3.retriever

import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.{AboutThisReturnBoxRetriever, CT600BoxRetriever}
import uk.gov.hmrc.ct.ct600j.v3._

trait CT600JBoxRetriever extends BoxRetriever {

  self: AboutThisReturnBoxRetriever =>

  def retrieveJ1(): J1 = {
    this match {
      case br: CT600BoxRetriever => J1(br.retrieveB1())
      case _ => throw new IllegalStateException("Could not the company name")
    }

  }

  def retrieveJ2(): J2 = {
    this match {
      case br: CT600BoxRetriever => J2(br.retrieveB3())
      case br: FilingAttributesBoxValueRetriever => J2(br.retrieveUTR().value)
    }
  }

  def retrieveJ3(): J3 = {
    this match {
      case br: CT600BoxRetriever => J3(br.retrieveB30())
      case br: ComputationsBoxRetriever => J3(br.retrieveCP1().value)
      case _ => throw new IllegalStateException("Could not get the AP start date.")
    }
  }

  def retrieveJ4(): J4 = {
    this match {
      case br: CT600BoxRetriever => J4(br.retrieveB35())
      case br: ComputationsBoxRetriever => J4(br.retrieveCP2().value)
      case _ => throw new IllegalStateException("Could not get the AP end date.")
    }
  }

  def retrieveJ5(): J5
  def retrieveJ10(): J10
  def retrieveJ15(): J15
  def retrieveJ20(): J20
  def retrieveJ25(): J25
  def retrieveJ30(): J30
  def retrieveJ35(): J35
  def retrieveJ40(): J40
  def retrieveJ45(): J45
  def retrieveJ50(): J50

  def retrieveJ5A(): J5A
  def retrieveJ10A(): J10A
  def retrieveJ15A(): J15A
  def retrieveJ20A(): J20A
  def retrieveJ25A(): J25A
  def retrieveJ30A(): J30A
  def retrieveJ35A(): J35A
  def retrieveJ40A(): J40A
  def retrieveJ45A(): J45A
  def retrieveJ50A(): J50A
}
