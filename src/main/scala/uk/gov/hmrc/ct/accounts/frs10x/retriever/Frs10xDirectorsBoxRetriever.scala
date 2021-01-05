/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.retriever

import uk.gov.hmrc.ct.accounts.frs102._
import uk.gov.hmrc.ct.accounts.frs102.helper.DirectorsReportEnabled
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{Directors, _}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs10xDirectorsBoxRetriever extends AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def ac8021(): AC8021

  def directors(): Directors

  def ac8033(): AC8033

  def ac8023(): AC8023

  def acQ8003(): ACQ8003

  def acQ8009(): ACQ8009
  
  def ac8051(): AC8051

  def ac8052(): AC8052

  def ac8053(): AC8053

  def ac8054(): AC8054

  def ac8899(): AC8899

  def directorsReportEnabled(): DirectorsReportEnabled = DirectorsReportEnabled.calculate(this)
}
