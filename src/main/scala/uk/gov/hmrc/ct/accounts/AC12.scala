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

package uk.gov.hmrc.ct.accounts

import org.joda.time.{Days, LocalDate}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.utils.DateImplicits._
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.domain.CompanyTypes


case class AC12(value: Option[Int]) extends CtBoxIdentifier(name = "Current Turnover/Sales")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with Validators  {

  private val maximumCoHoTurnover = 10200000
  private val maximumHmrcTurnover = 632000

  override def validate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
      val errors = collectErrors(
        failIf(isFrs10xHmrcAbridgedReturnWithLongPoA(boxRetriever)) {
          validateAsMandatory(this)
        },
        failIf(boxRetriever.hmrcFiling().value)(
          collectErrors(
            validateHmrcTurnover(boxRetriever)
          )
        ),
        failIf(!boxRetriever.hmrcFiling().value && boxRetriever.companiesHouseFiling().value)(
          collectErrors(
            validateCoHoTurnover(boxRetriever)
          )
        )
      )

    if(errors.isEmpty) {
      validateMoney(value)
    } else {
      errors
    }
  }

  private def isFrs10xHmrcAbridgedReturnWithLongPoA(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Boolean = {
    boxRetriever.hmrcFiling().value &&
    boxRetriever.abridgedFiling().value &&
    isFRS10x(boxRetriever) &&
    isLongPoA(boxRetriever)
  }

  private def isFRS10x(boxRetriever: AccountsBoxRetriever): Boolean = {
    boxRetriever.ac3().value >= new LocalDate(2016, 1, 1)
  }

  private def isLongPoA(boxRetriever: AccountsBoxRetriever): Boolean = {
    boxRetriever.ac3().value.plusMonths(12) <= boxRetriever.ac4().value
  }

  private def daysBetweenDates(start: LocalDate, end: LocalDate): Int = {
    Days.daysBetween(start, end).getDays + 1
  }

  private def getDaysInYear(boxRetriever: AccountsBoxRetriever): Int = {
    val poaStartDate = boxRetriever.ac3().value
    val poaEndDate = boxRetriever.ac4().value

    val daysInYearFromStartDate = daysBetweenDates(poaStartDate, poaStartDate.plusYears(1).minusDays(1))
    val daysInYearEndingEndDate = daysBetweenDates(poaEndDate.minusYears(1).plusDays(1), poaEndDate)

    (isLongPoA(boxRetriever), poaStartDate.getDayOfMonth, poaStartDate.getMonthOfYear) match {
      case (_, 29, 2) => 366
      case (true, _, _) => daysInYearFromStartDate max daysInYearEndingEndDate
      case (false, _, _) => daysInYearFromStartDate min daysInYearEndingEndDate
    }
  }

  private def validateCoHoTurnover(boxRetriever: AccountsBoxRetriever)(): Set[CtValidation] = {
    val daysInPoa = daysBetweenDates(boxRetriever.ac3().value, boxRetriever.ac4().value)
    val daysInYear = getDaysInYear(boxRetriever)
    val maxTurnover = if (isFRS10x(boxRetriever)) 10200000.0 else 6500000.0
    val maximumTurnoverInYear = Math.floor(maxTurnover * daysInPoa / daysInYear).toInt
    validateTurnoverRangeWithMinAndMaxMessages(this, s"error.${this.id}.coho.turnover", -maximumTurnoverInYear, maximumTurnoverInYear)
  }

  private def validateHmrcTurnover(boxRetriever: AccountsBoxRetriever)(): Set[CtValidation] = {
    val daysInPoa = daysBetweenDates(boxRetriever.ac3().value, boxRetriever.ac4().value)
    val daysInYear = getDaysInYear(boxRetriever)

    val isCharity = boxRetriever match {
      case fabr: FilingAttributesBoxValueRetriever => CompanyTypes.AllCharityTypes.contains(fabr.companyType().value)
      case _ => false
    }

    val maxHmrcTurnover = if (isCharity) 6500000.0 else 632000.0
    val maximumTurnoverInYear = Math.floor(maxHmrcTurnover * daysInPoa / daysInYear).toInt
    validateTurnoverRangeWithMinAndMaxMessages(this,  s"error.${this.id}.hmrc.turnover", -maximumTurnoverInYear, maximumTurnoverInYear)
  }

  private def validateTurnoverRangeWithMinAndMaxMessages(box: OptionalIntIdBox, message: String, min: Int, max: Int)(): Set[CtValidation] = {
    box.value match {
      case Some(x) => {
        collectErrors(
          failIf(x < min) {
            // TODO: use proper localised currency values in the message args
            Set(CtValidation(Some(boxId), message+".below.min", Some(Seq(commaForThousands(x), commaForThousands(Math.abs(min))))))
          },
          failIf(x > max) {
            Set(CtValidation(Some(boxId), message+".above.max", Some(Seq(commaForThousands(x), commaForThousands(max)))))
          }
        )
      }
      case _ => Set()
    }
  }
}

object AC12 {
  def apply(value: Int): AC12 = AC12(Some(value))
}
