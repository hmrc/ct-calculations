/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.Days
import uk.gov.hmrc.ct.CATO03
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

import scala.math.BigDecimal.RoundingMode._

trait WritingDownAllowanceCalculator extends CtTypeConverters {

  import uk.gov.hmrc.ct.computations.calculations.WritingDownAllowanceCalculator._

  def calculate(cp1: CP1,
                cp2: CP2,
                cp78: CP78,
                cp82: CP82,
                cp84: CP84,
                cp88: CP88): CATO03 = {
    val poolResult = pool(cp78, cp82, cp84, cp88)

    val value = if (poolResult <= smallPoolsAllowance(cp1, cp2))
      poolResult
    else writtenDownAllowanceLimit(cp1, cp2, cp78, cp82, cp84, cp88)
    CATO03(value)
  }

  private[computations] def smallPoolsAllowance(cp1: CP1,
                                                cp2: CP2): Int = {
    round(apportionOfAccountingPeriod(cp1, cp2) * SmallPoolsAllowanceLimit, 0).toInt
  }

  private[computations] def writtenDownAllowanceLimit(cp1: CP1,
                                                      cp2: CP2,
                                                      cp78: CP78,
                                                      cp82: CP82,
                                                      cp84: CP84,
                                                      cp88: CP88): Int = {
    val apportionOfAccountingPeriodResult = apportionOfAccountingPeriod(cp1, cp2)
    val poolResult = pool(cp78, cp82, cp84, cp88)

    (poolResult * FixedAssetsLimit * apportionOfAccountingPeriodResult).toInt
  }

  private[computations] def pool(cp78: CP78,
                                 cp82: CP82,
                                 cp84: CP84,
                                 cp88: CP88): Int = cp78 + cp82 - cp84 - cp88 max 0

  private def apportionOfAccountingPeriod(cp1: CP1,
                                          cp2: CP2): BigDecimal = {
    val daysInAccountingPeriod = BigDecimal(Days.daysBetween(cp1.value, cp2.value).getDays + 1)
    val daysInYear = daysInAccountingPeriod max DaysInNormalYear

    round(daysInAccountingPeriod / daysInYear, 2)
  }
}

object WritingDownAllowanceCalculator {

  val DaysInNormalYear = BigDecimal(365)
  val SmallPoolsAllowanceLimit = BigDecimal(1000)
  val FixedAssetsLimit = BigDecimal(0.18)

  def round(value: BigDecimal, decimalPlaces: Int = 0): BigDecimal = {
    value.setScale(decimalPlaces, HALF_UP)
  }
}
