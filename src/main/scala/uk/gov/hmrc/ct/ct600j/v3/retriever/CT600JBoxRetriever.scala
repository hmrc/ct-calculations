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

import uk.gov.hmrc.ct.box.retriever.BoxValues
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600j.v3._

object CT600JBoxRetriever extends BoxValues[CT600JBoxRetriever]

trait CT600JBoxRetriever {

  self: CT600BoxRetriever =>

  def retrieveB65(): B65

  def retrieveJ1(): J1 = J1(retrieveB1())
  def retrieveJ2(): J2 = J2(retrieveB3())
  def retrieveJ3(): J3 = J3(retrieveB30())
  def retrieveJ4(): J4 = J4(retrieveB35())

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
