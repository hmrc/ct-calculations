/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.retriever

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}

trait AccountsBoxRetriever extends BoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def companyAddress(): CompanyAddress

  def ac1(): AC1

  def ac2(): AC2

  def ac3(): AC3
  
  def ac4(): AC4

  def ac12(): AC12

  def ac205(): AC205

  def ac206(): AC206
}
