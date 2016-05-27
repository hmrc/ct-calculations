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

package uk.gov.hmrc.ct.version

object HmrcReturns {
  case object Computations extends ReturnType {
    override def key(): String = "Computations"
  }

  case object CT600 extends ReturnType {
    override def key(): String = "CT600"
  }

  case object CT600a extends ReturnType {
    override def key(): String = "CT600a"
  }

  case object CT600j extends ReturnType {
    override def key(): String = "CT600j"
  }

  case object CT600e extends ReturnType {
    override def key(): String = "CT600e"
  }

  case object HmrcUploadedAccounts extends ReturnType with Accounts {
    override def key(): String = "HmrcUploadedAccounts"
  }

  case object HmrcMicroEntityAccounts extends ReturnType with Accounts {
    override def key(): String = "HmrcMicroEntityAccounts"
  }

  case object HmrcStatutoryAccounts extends ReturnType with Accounts {
    override def key(): String = "HmrcStatutoryAccounts"
  }

  case object HmrcAbridgedAccounts extends ReturnType with Accounts {
    override def key(): String = "HmrcAbridgedAccounts"
  }

  val returns: Set[ReturnType] = Set(Computations, CT600, CT600a, CT600e, CT600j, HmrcMicroEntityAccounts, HmrcStatutoryAccounts, HmrcUploadedAccounts)

  def fromKey(key: String): ReturnType = {
    returns.find(_.key() == key).getOrElse(throw new IllegalArgumentException(s"Unknown key for HmrcReturn: $key"))
  }
}
