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

package uk.gov.hmrc.ct.domain

import org.joda.time.LocalDate

object ValidationConstants {

  val MIN_MONEY_AMOUNT_ALLOWED = 1
  val MAX_MONEY_AMOUNT_ALLOWED = 99999999

  val ERROR_ARGS_DATE_FORMAT = "d MMMM YYYY"

  val EARLIEST_AP_END_DATE_CUTOFF = new LocalDate(2008, 3, 31)

  def toErrorArgsFormat(date: LocalDate) = date.toString(ERROR_ARGS_DATE_FORMAT)

}
