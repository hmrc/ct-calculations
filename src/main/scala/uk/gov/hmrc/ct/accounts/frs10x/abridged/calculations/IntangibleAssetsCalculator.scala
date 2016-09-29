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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations

import uk.gov.hmrc.ct.accounts.frs10x.abridged._

trait IntangibleAssetsCalculator {

  def calculateAC117(ac114: AC114, ac115: AC115, ac116: AC116, ac209: AC209, ac210: AC210): AC117 = {
    Set(ac114.value, ac115.value, ac116.value, ac209.value, ac210.value).exists(_.nonEmpty) match {
      case true => AC117(Some(ac114.orZero + ac115.orZero - ac116.orZero + ac209.orZero + ac210.orZero))
      case _ => AC117(None)
    }
  }

  def calculateAC121(ac118: AC118, ac119: AC119, ac120: AC120, ac211: AC211): AC121 = {
    Set(ac118.value, ac119.value, ac120.value, ac211.value).exists(_.nonEmpty) match {
      case true => AC121(Some(ac118.orZero + ac119.orZero - ac120.orZero + ac211.orZero))
      case _ => AC121(None)
    }
  }

  def calculateAC123(ac114: AC114, ac118: AC118): AC123 = {
    Set(ac114.value, ac118.value).exists(_.nonEmpty) match {
      case true => AC123(Some(ac114.orZero - ac118.orZero))
      case _ => AC123(None)
    }
  }

  def calculateAC122(ac117: AC117, ac121: AC121): AC122 = {
    Set(ac117.value, ac121.value).exists(_.nonEmpty) match {
      case true => AC122(Some(ac117.orZero - ac121.orZero))
      case _ => AC122(None)
    }
  }

}
