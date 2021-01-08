/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}


case class CP248(value: Option[Int]) extends CtBoxIdentifier("Total allowances") with CtOptionalInteger

object CP248 extends Linked[CP186, CP248]{

  override def apply(source: CP186): CP248 = CP248(source.value)
}
