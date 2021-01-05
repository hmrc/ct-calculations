/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.retriever

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.{CATO10, CATO11, CATO12}

trait DeclarationBoxRetriever extends BoxRetriever {

  def cato10(): CATO10

  def cato11(): CATO11

  def cato12(): CATO12
}
