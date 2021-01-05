/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

/*
  This was labelled as deprecated("" ,"01-03-2016 or earlier").
  This was used for a filing period before the date provided.
 */
case class CP81Input(value: Option[Int]) extends CtBoxIdentifier(name = "Expenditure qualifying for first year allowance (FYA)") with CtOptionalInteger with Input

object CP81Input {

  def apply(int: Int): CP81Input = CP81Input(Some(int))
}
