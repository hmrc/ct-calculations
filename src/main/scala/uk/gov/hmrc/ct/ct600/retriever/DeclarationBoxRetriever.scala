package uk.gov.hmrc.ct.ct600.retriever

import uk.gov.hmrc.ct.box.retriever.{BoxValues, BoxRetriever}
import uk.gov.hmrc.ct.{CATO12, CATO11, CATO10}

object DeclarationBoxRetriever extends BoxValues[DeclarationBoxRetriever]

trait DeclarationBoxRetriever extends BoxRetriever {

  def retrieveCATO10(): CATO10

  def retrieveCATO11(): CATO11

  def retrieveCATO12(): CATO12
}
