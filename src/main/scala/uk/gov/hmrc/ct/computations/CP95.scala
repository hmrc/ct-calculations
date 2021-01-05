/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

/*
  This was labelled as @deprecated("This box is no longer in use", "5-11-2015 or earlier")
  This was used for a filing period before the date provided.
 */
case class CP95(value: Option[Int]) extends CtBoxIdentifier("Total Allowances") with CtOptionalInteger

object CP95 extends Linked[CP93, CP95]{

  override def apply(source: CP93): CP95 = CP95(source.value)
}
