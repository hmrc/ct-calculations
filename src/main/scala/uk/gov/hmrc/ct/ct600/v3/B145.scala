/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.{CP7, CP983}

case class B145(value: Option[Int]) extends CtBoxIdentifier(name = "Total turnover from trade") with CtOptionalInteger

object B145{
  def apply(source: CP7, source2: CP983): B145 = {
    (source.value, source2.value) match {
      case (None, None) => B145(None)
      case (s, s2) => B145(Some(s.getOrElse(0) + s2.getOrElse(0)))
    }
  }
}
