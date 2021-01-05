/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

abstract class SchemeDateBox extends CtBoxIdentifier("Accounting period in which the expected advantage arises") with CtOptionalDate with Input with ValidatableBox[CT600JBoxRetriever] {

  val earliestSchemeDate = new LocalDate(2004, 3, 17)

  def validateSchemeDate(previousSchemeNumberBox: CtOptionalString, previousSchemeDateBox: CtOptionalDate, schemeReferenceNumberBox: CtOptionalString) =
    (previousSchemeNumberBox.value, previousSchemeDateBox.value, schemeReferenceNumberBox.value) match {
      case (None, None, _) => validateDateAsBlank(id, this)
      case (_, _, Some(_)) => validateAsMandatory(this) ++ validateDateAsAfter(id, this, earliestSchemeDate)
      case _ => Set[CtValidation]()
    }
}
