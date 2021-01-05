/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}


case class CP514(value: Int) extends CtBoxIdentifier with CtInteger

object CP514 extends Linked[CP511, CP514]{

  override def apply(source: CP511): CP514 = CP514(source.value)
}
