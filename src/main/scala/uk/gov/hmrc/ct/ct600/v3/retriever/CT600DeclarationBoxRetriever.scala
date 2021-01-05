/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3.retriever

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v3.{B975, B980, B985, N092}

trait CT600DeclarationBoxRetriever extends BoxRetriever {

  def b975(): B975

  def b985(): B985

  def b980(): B980

  def n092(): N092

}
