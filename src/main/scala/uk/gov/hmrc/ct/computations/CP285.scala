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

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.utils.DateImplicits._


case class CP285(value: Option[LocalDate]) extends CtBoxIdentifier(name = "End date of accounting period from which trading loss is being brought back")
  with CtOptionalDate
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val DateFormat = "dd/MM/yyyy"

    collectErrors(
      requiredIf({ value.isEmpty && boxRetriever.cpQ18().value.contains(true) }) ,
      cannotExistIf(value.nonEmpty && !boxRetriever.cpQ18().orFalse),
      { () =>
        failIf (value.nonEmpty) {
          val providedDate = value.getOrElse(throw new IllegalStateException("The value of CP285 is empty and that does not appear to be possible."))
          val cp2 = boxRetriever.cp2().value
          passIf (providedDate > cp2 && !(providedDate > cp2.plusYears(1))) {
            Set(CtValidation(Some(boxId), "error.CP285.date.outside.range", Some(Seq(cp2.plusDays(1).toString(DateFormat), cp2.plusYears(1).toString(DateFormat)))))
          }
        }
      }
    )
  }
}
