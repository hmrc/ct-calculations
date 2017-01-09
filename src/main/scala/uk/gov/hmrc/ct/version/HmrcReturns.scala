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

package uk.gov.hmrc.ct.version

import uk.gov.hmrc.ct.version.CoHoAccounts.{CoHoAbridgedAccounts, CoHoMicroEntityAccounts, CoHoStatutoryAccounts}

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

  case object HmrcUploadedAccounts extends ReturnType with HmrcAccounts {
    override def key(): String = "HmrcUploadedAccounts"
  }

  case object HmrcMicroEntityAccounts extends ReturnType with HmrcAccounts with CoHoEquivalent {
    override def key(): String = "HmrcMicroEntityAccounts"

    override def coHoReturnType: Accounts = CoHoMicroEntityAccounts
  }

  case object HmrcStatutoryAccounts extends ReturnType with HmrcAccounts with CoHoEquivalent {
    override def key(): String = "HmrcStatutoryAccounts"

    override def coHoReturnType: Accounts = CoHoStatutoryAccounts
  }

  case object HmrcAbridgedAccounts extends ReturnType with HmrcAccounts with CoHoEquivalent {
    override def key(): String = "HmrcAbridgedAccounts"

    override def coHoReturnType: Accounts = CoHoAbridgedAccounts
  }

  val returns: Set[ReturnType] = Set(Computations, CT600, CT600a, CT600e, CT600j, HmrcMicroEntityAccounts, HmrcStatutoryAccounts, HmrcUploadedAccounts, HmrcAbridgedAccounts)

  def fromKey(key: String): ReturnType = {
    returns.find(_.key() == key).getOrElse(throw new IllegalArgumentException(s"Unknown key for HmrcReturn: $key"))
  }
}

sealed trait CoHoEquivalent {
  def coHoReturnType: Accounts
}

sealed trait HmrcAccounts extends Accounts
