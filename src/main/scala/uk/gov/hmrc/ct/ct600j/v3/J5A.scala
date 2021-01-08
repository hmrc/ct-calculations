/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

case class J5A(value: Option[LocalDate]) extends SchemeDateBox {

  override def validate(boxRetriever: CT600JBoxRetriever): Set[CtValidation] = {
    boxRetriever match {
      case r: CT600BoxRetriever =>
        r.b140().value match {
          case Some(true) => validateDateAsMandatory(id, this) ++ validateDateAsAfter(id, this, earliestSchemeDate)
          case _ => Set.empty
        }
      case _ => Set.empty
    }
  }

}
