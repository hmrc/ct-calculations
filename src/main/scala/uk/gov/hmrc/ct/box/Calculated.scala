/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box

import uk.gov.hmrc.ct.box.retriever.BoxRetriever

trait Calculated[T, C <: BoxRetriever] {

   def calculate(boxRetriever: C): T

}
