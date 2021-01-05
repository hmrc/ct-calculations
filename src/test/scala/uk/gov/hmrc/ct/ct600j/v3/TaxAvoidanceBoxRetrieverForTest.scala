/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

trait TaxAvoidanceBoxRetrieverForTest extends CT600BoxRetriever with CT600JBoxRetriever with Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever
