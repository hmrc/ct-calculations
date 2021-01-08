/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.retriever

import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait AbridgedAccountsBoxRetriever extends Frs102AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

}
