/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations

import org.joda.time.{Days, LocalDate}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.CP3020._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP3020(value: Option[Int]) extends CtBoxIdentifier(name = "Qualifying donations to grassroots sports clubs")
  with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(retriever.cpQ321().isTrue && !hasValue),
      validateZeroOrPositiveInteger(this),
      cannotExistErrorIf(hasValue && retriever.cpQ321().isFalse),
      apportionedLimitErrors(retriever)
    )
  }

  private def apportionedLimitErrors(retriever: ComputationsBoxRetriever) = {
    val limit = apportionedLimit(retriever.cp1.value, retriever.cp2.value)
    failIf(orZero > limit) {
      Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£" + limit.toString))))
    }
  }

  private def apportionedLimit(apStart: LocalDate, apEnd: LocalDate) = {
    val daysAfter010417 = Days.daysBetween(grassrootsStart, apEnd).getDays + 1
    val apDays = Days.daysBetween(apStart, apEnd).getDays + 1
    val eligibleDays =  (daysAfter010417 min apDays) max 0
    (eligibleDays * maxQualifyingAmount) / daysInYear(apStart, apEnd)
  }

  private def daysInYear(apStart: LocalDate, apEnd: LocalDate) = {
    val leapYearOption = (apStart.year().isLeap, apEnd.year().isLeap) match {
      case (true, _) => Some(apStart.getYear)
      case (_, true) => Some(apEnd.getYear)
      case _ => None
    }

    val leapDayInAp = leapYearOption
      .map(new LocalDate(_, 2, 29))
      .exists(l => !l.isBefore(apStart) && !l.isAfter(apEnd))

    if (leapDayInAp) 366 else 365
  }
}


object CP3020 {
  val grassrootsStart = LocalDate.parse("2017-04-01")
  val maxQualifyingAmount = 2500
  def apply(int: Int): CP3020 = CP3020(Some(int))
}
