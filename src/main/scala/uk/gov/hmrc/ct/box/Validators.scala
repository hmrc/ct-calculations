/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.box

import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._

trait Validators {

  protected val boxId = getClass.getSimpleName

  protected def And(predicates: ((ComputationsBoxRetriever) => Boolean)*)(boxRetriever: ComputationsBoxRetriever): Boolean = {
    !predicates.exists { p => !p(boxRetriever)}
  }

  protected def Or(predicates: ((ComputationsBoxRetriever) => Boolean)*)(boxRetriever: ComputationsBoxRetriever): Boolean = {
    predicates.exists { p => p(boxRetriever)}
  }

  protected def requiredIf(boxId: String = boxId)(predicate: (ComputationsBoxRetriever) => Boolean)(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    if (predicate(boxRetriever))
      Set(CtValidation(Some(boxId), s"error.$boxId.required"))
    else
      Set.empty
  }

  protected def exceedsMax(boxId: String = boxId)(value: Option[Int], max: Int = MAX_MONEY_AMOUNT_ALLOWED)(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    value match {
      case (Some(v)) if v > max => Set(CtValidation(Some(boxId), s"error.$boxId.exceeds.max", Some(Seq(max.toString))))
      case _ => Set.empty
    }
  }

  protected def belowMin(boxId: String = boxId)(value: Option[Int], min: Int = MIN_MONEY_AMOUNT_ALLOWED)(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    value match {
      case (Some(v)) if v < min => Set(CtValidation(Some(boxId), s"error.$boxId.below.min", Some(Seq(min.toString))))
      case _ => Set.empty
    }
  }

  protected def cannotExistIf(boxId: String = boxId)(predicate: (ComputationsBoxRetriever) => Boolean)(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    if (predicate(boxRetriever))
      Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
    else
      Set.empty
  }

  protected def collectErrors(predicates: Set[(ComputationsBoxRetriever) => Set[CtValidation]])(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    predicates.flatMap { predicate =>
      predicate(boxRetriever)
    }
  }
}
