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

object HmrcVersions {

  case object CT600Version3 extends Version {
    override def name: String = "v3"
  }

  case object CT600Version2 extends Version {
    override def name: String = "v2"
  }

  case object ComputationsCT20150201 extends Version {
    override def name: String = "ct-2015-02-01"
  }

  case object ComputationsCT20141001 extends Version {
    override def name: String = "ct-2014-10-01"
  }

  case object ComputationsCT20130721 extends Version {
    override def name: String = "ct-2013-07-21"
  }

  case object UploadedAccounts extends Version {
    override def name: String = "uploaded-1.0"
  }
}
