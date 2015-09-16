package uk.gov.hmrc.ct.accounts.retriever

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, BoxValues}

object AccountsBoxRetriever extends BoxValues[AccountsBoxRetriever]

trait AccountsBoxRetriever extends BoxRetriever {
  def retrieveAC1(): AC1
  def retrieveAC3(): AC3
  def retrieveAC4(): AC4

  def retrieveAC205(): AC205
  def retrieveAC206(): AC206
}
