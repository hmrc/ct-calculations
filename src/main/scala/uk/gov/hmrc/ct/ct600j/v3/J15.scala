/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

case class J15(value: Option[String]) extends SchemeReferenceNumberBox {

  override def validate(boxRetriever: CT600JBoxRetriever): Set[CtValidation] =
    validateSchemeReferenceNumber(boxRetriever.j10(), boxRetriever.j10A(), boxRetriever.j15A())

}
