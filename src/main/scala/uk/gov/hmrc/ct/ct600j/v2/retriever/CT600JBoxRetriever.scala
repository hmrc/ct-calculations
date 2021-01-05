/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v2.retriever

import uk.gov.hmrc.ct.ct600j.v2.TAQ01

trait CT600JBoxRetriever {

  def taq01(): TAQ01
}
