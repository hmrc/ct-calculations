/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.retriever

import uk.gov.hmrc.ct.CATO24
import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs105.boxes.{AC24, AC25, AC415}
import uk.gov.hmrc.ct.accounts.frs10x.boxes.AC13
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}

trait AccountsBoxRetriever extends BoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def companyAddress(): CompanyAddress

  def ac1(): AC1

  def ac2(): AC2

  def ac3(): AC3
  
  def ac4(): AC4

  def ac12(): AC12

  def ac13(): AC13

  def ac24(): AC24

  def ac25(): AC25

  def ac401(): AC401

  def ac402(): AC402

  def ac403(): AC403

  def ac404(): AC404

  def cato24(): CATO24

  def ac415(): AC415

  def ac205(): AC205

  def ac206(): AC206
}
