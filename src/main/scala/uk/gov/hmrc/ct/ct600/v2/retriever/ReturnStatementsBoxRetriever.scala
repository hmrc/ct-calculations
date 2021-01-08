/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2.retriever

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v2._

trait ReturnStatementsBoxRetriever extends BoxRetriever {

  self: AccountsBoxRetriever =>

  def rsq1(): RSQ1

  def rsq2(): RSQ2

  def rsq3(): RSQ3 = RSQ3.calculate(this)

  def rsq4(): RSQ4

  def rsq7(): RSQ7

  def rsq8(): RSQ8
}
