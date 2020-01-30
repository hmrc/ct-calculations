/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.retriever

import uk.gov.hmrc.ct.computations.{CP296, CP297, SBA01, SBA01A, SBA01B}

trait ComputationsBuildingsBoxRetriever extends ComputationsBoxRetriever { //maybe just box retriever????

    def sba01(): SBA01

    def sba01A(): SBA01A

//    def sba01B(): SBA01B

    def sba02(): List[Option[Int]] = CP296.getAllowanceForEachBuilding(this)

    def cp296(): CP296 = CP296.calculate(this)

    def cp297(): CP297 = CP297.calculate(this)

}
