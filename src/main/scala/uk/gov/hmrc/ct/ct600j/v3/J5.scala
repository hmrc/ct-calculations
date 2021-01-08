/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

case class J5(value: Option[String]) extends SchemeReferenceNumberBox {

  override def validate(boxRetriever: CT600JBoxRetriever): Set[CtValidation] = {
    boxRetriever match {
      case r: CT600BoxRetriever =>
        r.b140().value match {
          case Some(true) => validateStringAsMandatory(id, this) ++ validateOptionalStringByRegex(id, this, taxAvoidanceSchemeNumberRegex)
          case _ => Set.empty
        }
      case _ => Set.empty
    }
  }
}
