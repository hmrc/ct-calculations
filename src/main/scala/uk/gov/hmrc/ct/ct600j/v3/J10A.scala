/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

case class J10A(value: Option[LocalDate]) extends SchemeDateBox {

  override def validate(boxRetriever: CT600JBoxRetriever): Set[CtValidation] =
    validateSchemeDate(boxRetriever.j5(), boxRetriever.j5A(), boxRetriever.j10())
}
