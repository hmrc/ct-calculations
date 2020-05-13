

package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

case class J10(value: Option[String]) extends SchemeReferenceNumberBox {

  override def validate(boxRetriever: CT600JBoxRetriever): Set[CtValidation] =
    validateSchemeReferenceNumber(boxRetriever.j5(), boxRetriever.j5A(), boxRetriever.j10A())
}
