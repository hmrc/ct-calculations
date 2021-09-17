/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600ei.v3.retriever


import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600ei.v3.{DIT001, DIT002, DIT003}

trait CT600EiBoxRetriever extends BoxRetriever {


  def dit001(): DIT001
  def dit002(): DIT002
  def dit003(): DIT003

}
