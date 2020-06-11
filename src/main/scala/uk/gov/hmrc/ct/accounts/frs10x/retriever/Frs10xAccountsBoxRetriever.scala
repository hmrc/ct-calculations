/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.retriever

import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC8081, AC8082, AC8083, AC8088}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs10xAccountsBoxRetriever extends AccountsBoxRetriever {
  self: FilingAttributesBoxValueRetriever =>


  def ac8081(): AC8081

  def ac8082(): AC8082

  def ac8083(): AC8083

  def ac8088(): AC8088
}
