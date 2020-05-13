

package uk.gov.hmrc

import uk.gov.hmrc.ct.box.CtValidation
import scala.language.implicitConversions

package object ct {

  implicit def validationsToValidationsFunction(xs: Set[CtValidation]): () => Set[CtValidation] = () => xs

}
