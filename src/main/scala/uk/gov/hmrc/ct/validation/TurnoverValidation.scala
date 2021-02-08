/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.validation

import org.joda.time.{Days, LocalDate}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox.{commaForThousands, _}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.domain.CompanyTypes
import uk.gov.hmrc.ct.utils.DateImplicits._

trait TurnoverValidation extends Validators {

  self: OptionalIntIdBox =>

  private def daysBetweenDates(start: LocalDate, end: LocalDate): Int = {
    Days.daysBetween(start, end).getDays + 1
  }

  protected def isFrs10xHmrcAbridgedReturnWithLongPoA[BR <: AccountsBoxRetriever with FilingAttributesBoxValueRetriever](start: (BR) => StartDate, end: (BR) => EndDate)(boxRetriever: BR): Boolean = {
    boxRetriever.hmrcFiling().value &&
      boxRetriever.abridgedFiling().value &&
      isFRS10x(boxRetriever) &&
      isLongPoA(boxRetriever, start, end)
  }

  protected def isFRS10x(boxRetriever: AccountsBoxRetriever): Boolean = {
    boxRetriever.ac3().value >= new LocalDate(2016, 1, 1)
  }

  private def isLongPoA[BR <: BoxRetriever](boxRetriever: BR, start: (BR) => StartDate, end: (BR) => EndDate): Boolean = {
    start(boxRetriever).value.plusMonths(12) <= end(boxRetriever).value
  }

  protected def getDaysInYear[BR <: BoxRetriever](boxRetriever: BR, start: (BR) => StartDate, end: (BR) => EndDate): Int = {
    val poaStartDate = start(boxRetriever).value
    val poaEndDate = end(boxRetriever).value

    val daysInYearFromStartDate = daysBetweenDates(poaStartDate, poaStartDate.plusYears(1).minusDays(1))
    val daysInYearEndingEndDate = daysBetweenDates(poaEndDate.minusYears(1).plusDays(1), poaEndDate)

    (isLongPoA(boxRetriever, start, end), poaStartDate.getDayOfMonth, poaStartDate.getMonthOfYear) match {
      case (_, 29, 2) => 366
      case (true, _, _) => daysInYearFromStartDate max daysInYearEndingEndDate
      case (false, _, _) => daysInYearFromStartDate min daysInYearEndingEndDate
    }
  }

  protected def validateHmrcTurnover[BR <: BoxRetriever](boxRetriever: BR,
                                                         start: (BR) => StartDate,
                                                         end: (BR) => EndDate,
                                                         errorSuffix: String = ".hmrc.turnover",
                                                         secondaryIncome: Int = 0,
                                                         minimumAmount:Option[Int]=None)(): Set[CtValidation] = {
    val daysInPoa = daysBetweenDates(start(boxRetriever).value, end(boxRetriever).value)
    val daysInYear = getDaysInYear(boxRetriever, start, end)

    val isCharity = boxRetriever match {
      case fabr: FilingAttributesBoxValueRetriever => CompanyTypes.AllCharityTypes.contains(fabr.companyType().value)
      case _ => false
    }

    val maxHmrcTurnover = if (isCharity) 6500000.0 else 632000.0
    val maximumTurnoverInYear = Math.floor(maxHmrcTurnover * daysInPoa / daysInYear).toInt
    val minimumValue =minimumAmount.getOrElse(-maximumTurnoverInYear)
    validateTurnoverRangeWithMinAndMaxMessages(this,  s"error.${this.id}$errorSuffix", minimumValue, maximumTurnoverInYear, secondaryIncome)
  }

  protected def validateCoHoTurnover(boxRetriever: AccountsBoxRetriever, start: (AccountsBoxRetriever) => StartDate, end: (AccountsBoxRetriever) => EndDate)(): Set[CtValidation] = {
    val daysInPoa = daysBetweenDates(boxRetriever.ac3().value, boxRetriever.ac4().value)
    val daysInYear = getDaysInYear(boxRetriever, start, end)
    val maxTurnover = if (isFRS10x(boxRetriever)) 10200000.0 else 6500000.0
    val maximumTurnoverInYear = Math.floor(maxTurnover * daysInPoa / daysInYear).toInt
    validateTurnoverRangeWithMinAndMaxMessages(this, s"error.${this.id}.coho.turnover", -maximumTurnoverInYear, maximumTurnoverInYear, 0)
  }

  protected def validateTurnoverRangeWithMinAndMaxMessages(box: OptionalIntIdBox, message: String, min: Int, max: Int, secondaryIncome: Int)(): Set[CtValidation] = {
    box.value match {
      case Some(x) => {
        collectErrors(
          failIf(x + secondaryIncome < min) {
            // TODO: use proper localised currency values in the message args
            Set(CtValidation(Some(boxId), message+".below.min", Some(Seq(commaForThousands(min), commaForThousands(Math.abs(max))))))
          },
          failIf(x + secondaryIncome > max) {
            Set(CtValidation(Some(boxId), message+".above.max", Some(Seq(commaForThousands(min), commaForThousands(max)))))
          }
        )
      }
      case _ => Set()
    }
  }
}
