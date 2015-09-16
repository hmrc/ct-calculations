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

package uk.gov.hmrc.ct.version

object CoHoAccounts {

  case object CoHoMicroEntityAccounts extends ReturnType with Accounts {
    override def key(): String = "CoHoMicroEntityAccounts"
  }

  case object CoHoMicroEntityAbridgedAccounts extends ReturnType with Accounts {
    override def key(): String = "CoHoMicroEntityAbridgedAccounts"
  }

  case object CoHoStatutoryAccounts extends ReturnType with Accounts {
    override def key(): String = "CoHoStatutoryAccounts"
  }

  case object CoHoStatutoryAbbreviatedAccounts extends ReturnType with Accounts {
    override def key(): String = "CoHoStatutoryAbbreviatedAccounts"
  }

  val returns: Set[ReturnType] = Set(CoHoMicroEntityAccounts, CoHoMicroEntityAbridgedAccounts, CoHoStatutoryAccounts, CoHoStatutoryAbbreviatedAccounts)

  def fromKey(key: String): ReturnType = {
    returns.find(_.key() == key).getOrElse(throw new IllegalArgumentException(s"Unknown key for CoHoAccounts: $key"))
  }
}
