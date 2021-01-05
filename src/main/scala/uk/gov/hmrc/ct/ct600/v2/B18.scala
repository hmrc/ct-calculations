/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B18(value: Int) extends CtBoxIdentifier(name = " Net chargeable gains") with CtInteger

object B18 extends Calculated[B18, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B18 = {
//    B18(fieldValueRetriever.b16() - fieldValueRetriever.b17())
    ???
  }
}
