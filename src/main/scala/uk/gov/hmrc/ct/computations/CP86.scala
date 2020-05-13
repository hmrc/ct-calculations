

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

/*
  This was labelled as @deprecated("This box is no longer in use", "5-11-2016 or earlier")
  This was used for a filing period before the date provided.
 */
case class CP86(value: Option[Int]) extends CtBoxIdentifier(name = "Other first year allowances claimed") with CtOptionalInteger with Input

object CP86 {

  def apply(int: Int): CP86 = CP86(Some(int))

}
