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

package uk.gov.hmrc.ct.box

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._

trait Validators {

  protected val boxId = getClass.getSimpleName

  protected def And(predicates: (() => Boolean)*)(): Boolean = {

    def allPredicatesTrue = predicates.forall {predicate => predicate()}
    allPredicatesTrue
  }

  protected def Or(predicates: (() => Boolean)*)(): Boolean = {
    def existsTruePredicate = predicates.exists(predicate => predicate())
    existsTruePredicate
  }

  protected def requiredIf(predicate: => Boolean)(): Set[CtValidation] = {
      if (predicate)
        Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      else
        Set.empty
  }

  protected def exceedsMax(value: Option[Int], max: Int = MAX_MONEY_AMOUNT_ALLOWED)(): Set[CtValidation] = {
    value match {
      case (Some(v)) if v > max => Set(CtValidation(Some(boxId), s"error.$boxId.exceeds.max", Some(Seq(max.toString))))
      case _ => Set.empty
    }
  }

  protected def belowMin(value: Option[Int], min: Int = MIN_MONEY_AMOUNT_ALLOWED)(): Set[CtValidation] = {
    value match {
      case (Some(v)) if v < min => Set(CtValidation(Some(boxId), s"error.$boxId.below.min", Some(Seq(min.toString))))
      case _ => Set.empty
    }
  }

  protected def validateMoney(value: Option[Int], min: Int = -99999999, max: Int = 99999999)(): Set[CtValidation] = {
    value match {
      case Some(x) if x < min => Set(CtValidation(boxId = Some(boxId), s"error.$boxId.below.min", Some(Seq(min.toString, max.toString))))
      case Some(x) if x > max => Set(CtValidation(boxId = Some(boxId), s"error.$boxId.above.max", Some(Seq(min.toString, max.toString))))
      case _ => Set.empty
    }
  }

  protected def cannotExistIf(predicate: => Boolean)(): Set[CtValidation] = {
    if (predicate)
      Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
    else
      Set.empty
  }

  protected def failIf(condition: => Boolean)(validationErrors: => Set[CtValidation])(): Set[CtValidation] = if(condition) validationErrors else Set()

  protected def passIf(condition: => Boolean)(validationErrors: => Set[CtValidation])(): Set[CtValidation] = if(condition) Set() else validationErrors

  protected def nonEmpty(value: Option[_]): Boolean = value.nonEmpty

  protected def isEmpty(value: Option[_]): Boolean = value.isEmpty

  protected def collectWithBoxId(newBoxId: String) (errors: Set[CtValidation]): Set[CtValidation] = {
    val (globalErrors, boxErrors) = errors.partition(_.isGlobalError)
    val transformedBoxErrors = boxErrors.map (error => error.copy(boxId = Some(newBoxId)))
    globalErrors ++ transformedBoxErrors
  }

  protected def replaceBoxId(newBoxId: String, errors: () => Set[CtValidation])(): Set[CtValidation] = errors().map(error => error.copy(boxId = Some(newBoxId)))


  protected def collectErrors(predicates: (() => Set[CtValidation])*): Set[CtValidation] = {
    predicates.flatMap { predicate =>
      predicate()
    }.toSet
  }
}
