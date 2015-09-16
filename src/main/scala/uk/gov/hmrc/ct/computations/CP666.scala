package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP666(value: Option[Int]) extends CtBoxIdentifier(name = "Written down value of special rate pool brought forward") with CtOptionalInteger with Input

object CP666 {

  def apply(value: Int): CP666 = CP666(Some(value))
}