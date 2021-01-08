/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.calculations

import uk.gov.hmrc.ct.box.{CtOptionalInteger, Debit}

import scala.annotation.tailrec

trait DebitAwareCalculation {

  protected final def sum[T](boxes: CtOptionalInteger*)(builder: Option[Int] => T): T = {
    if (boxes.exists(_.value.nonEmpty))
      builder(Some(doSum(0, Seq(boxes:_*))))
    else
      builder(None)
  }

  @tailrec
  private def doSum(currentValue: Int, boxes: Seq[CtOptionalInteger]): Int = {

    def valueAsSum(box: CtOptionalInteger): Int = {
      box match {
        case b: Debit => -b.orZero
        case _ => box.orZero
      }
    }

    boxes match {
      case head :: tail => doSum(currentValue + valueAsSum(head), tail)
      case Nil => currentValue
    }
  }
}
