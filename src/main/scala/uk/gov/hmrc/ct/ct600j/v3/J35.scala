

package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

case class J35(value: Option[String]) extends SchemeReferenceNumberBox{

  override def validate(boxRetriever: CT600JBoxRetriever): Set[CtValidation] =
    validateSchemeReferenceNumber(boxRetriever.j30(), boxRetriever.j30A(), boxRetriever.j35A())

}
