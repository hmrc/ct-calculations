/*
 * Copyright 2018 HM Revenue & Customs
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


package uk.gov.hmrc.ct.computations.stubs

import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever

class NorthernIrelandStubbedComputationsBoxRetriever extends StubbedComputationsBoxRetriever with AboutThisReturnBoxRetriever {

  override def b5(): B5 = ???

  override def b6(): B6 = ???

  override def b7(): B7 = ???

  override def b8(): B8 = ???

  override def b40(): B40 = ???

  override def b45Input(): B45Input = ???

  override def b55(): B55 = ???

  override def b65(): B65 = ???

  override def b80A(): B80A = ???

  override def b85A(): B85A = ???

  override def b90A(): B90A = ???
}
