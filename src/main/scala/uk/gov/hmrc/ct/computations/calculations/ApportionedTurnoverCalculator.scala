/*
 * Copyright 2019 HM Revenue & Customs
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

import org.joda.time.{Days, LocalDate}
import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.ct.accounts.frs105.boxes.AC405
import uk.gov.hmrc.ct.accounts.{AC12, AC3, AC4}
import uk.gov.hmrc.ct.computations._

import scala.math.BigDecimal.RoundingMode

trait ApportionedTurnoverCalculator {

  def turnoverApportionedBeforeAccountingPeriod(ac3: AC3,
                                                ac4: AC4,
                                                cp1: CP1,
                                                cp2: CP2,
                                                ac12: AC12,
                                                otherIncome: Option[Int] = None): AP1 = {
    AP1(apportionPeriodOfAccountsTurnover(ac3, ac4, cp1, cp2, ac12, otherIncome).beforeAccountingPeriod)
  }

  def turnoverApportionedDuringAccountingPeriod(ac3: AC3,
                                                ac4: AC4,
                                                cp1: CP1,
                                                cp2: CP2,
                                                ac12: AC12,
                                                otherIncome: Option[Int] = None): AP2 = {
    AP2(None, apportionPeriodOfAccountsTurnover(ac3, ac4, cp1, cp2, ac12, otherIncome).duringAccountingPeriod)
  }

  def turnoverApportionedAfterAccountingPeriod(ac3: AC3,
                                               ac4: AC4,
                                               cp1: CP1,
                                               cp2: CP2,
                                               ac12: AC12,
                                               otherIncome: Option[Int] = None): AP3 = {
    AP3(apportionPeriodOfAccountsTurnover(ac3, ac4, cp1, cp2, ac12, otherIncome).afterAccountingPeriod)
  }

  def apportionPeriodOfAccountsTurnover(ac3: AC3,
                                        ac4: AC4,
                                        cp1: CP1,
                                        cp2: CP2,
                                        ac12: AC12,
                                        otherIncome: Option[Int] = None): ApportionedTurnover = {
    val periodOfAccountsStart = ac3.value
    val periodOfAccountsEnd   = ac4.value
    val accountingPeriodStart = cp1.value
    val accountingPeriodEnd   = cp2.value
    val turnover              = (ac12.value ++ otherIncome).reduceOption(_+_)

    val periodOfAccounts       = Days.daysBetween(periodOfAccountsStart, periodOfAccountsEnd).plus(1)
    val beforeAccountingPeriod = Days.daysBetween(periodOfAccountsStart, accountingPeriodStart)
    val duringAccountingPeriod = Days.daysBetween(accountingPeriodStart, accountingPeriodEnd).plus(1)
    val afterAccountingPeriod  = Days.daysBetween(accountingPeriodEnd, periodOfAccountsEnd)

    def prorateTurnover(period: Days): Option[Int] = {
      def prorate: Option[Int] = {
        val proportion = BigDecimal.valueOf(period.getDays) / BigDecimal.valueOf(periodOfAccounts.getDays)

        turnover match {
          case None => None
          case Some(x) =>
            Some((BigDecimal.valueOf(x) * proportion).setScale(0, RoundingMode.DOWN).toInt)
        }
      }

      if (periodOfAccounts.getDays > 0 && period.getDays > 0) prorate else None
    }

    val apportionedTurnover = ApportionedTurnover(beforeAccountingPeriod = prorateTurnover(beforeAccountingPeriod),
                                                  duringAccountingPeriod = prorateTurnover(duringAccountingPeriod),
                                                  afterAccountingPeriod  = prorateTurnover(afterAccountingPeriod))

    val residual = turnover match {
      case Some(t) => t - apportionedTurnover.total
      case _ => 0
    }

    def reapportionRoundingErrors = {
      apportionedTurnover match {
        case ApportionedTurnover(Some(before), Some(current), Some(after)) if residual % 2 == 0 =>
          apportionedTurnover.copy(beforeAccountingPeriod = Some(before + residual / 2), afterAccountingPeriod = Some(after + residual / 2))

        case ApportionedTurnover(Some(before), Some(current), _) =>
          apportionedTurnover.copy(beforeAccountingPeriod = Some(before + residual))

        case ApportionedTurnover(None, Some(current), Some(after)) =>
          apportionedTurnover.copy(afterAccountingPeriod = Some(after + residual))

        case _ => throw new IllegalStateException("Turnover apportioned to each accounting period does not sum to turnover for period of accounts")
      }
    }

    if (residual != 0) reapportionRoundingErrors else apportionedTurnover
  }
}

case class ApportionedTurnover(beforeAccountingPeriod: Option[Int],
                               duringAccountingPeriod: Option[Int],
                               afterAccountingPeriod: Option[Int]) {

  def total: Int = afterAccountingPeriod.getOrElse(0) + beforeAccountingPeriod.getOrElse(0) + duringAccountingPeriod.getOrElse(0)
}

object ApportionedTurnover {

  implicit val apportionedTurnoverFormat: Format[ApportionedTurnover] = Json.format[ApportionedTurnover]
}
