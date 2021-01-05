/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

/*
  This was labelled as @deprecated("This box is no longer in use", "5-11-2015 or earlier")
  This was used for a filing period before the date provided.
 */
case class CP85(value: Option[Int]) extends CtBoxIdentifier(name = "Relevant first year allowances claimed") with CtOptionalInteger with Input

object CP85 {

  def apply(int: Int): CP85 = CP85(Some(int))

}
