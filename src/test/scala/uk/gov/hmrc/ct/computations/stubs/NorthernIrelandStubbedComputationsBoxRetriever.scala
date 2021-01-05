/*
 * Copyright 2021 HM Revenue & Customs
 *
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
