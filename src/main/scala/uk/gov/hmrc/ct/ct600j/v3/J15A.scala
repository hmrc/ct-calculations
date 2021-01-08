/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

case class J15A(value: Option[LocalDate]) extends SchemeDateBox{

  override def validate(boxRetriever: CT600JBoxRetriever): Set[CtValidation] =
    validateSchemeDate(boxRetriever.j10(), boxRetriever.j10A(), boxRetriever.j15())

}
