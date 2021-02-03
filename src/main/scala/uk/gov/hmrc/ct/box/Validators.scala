/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box

import uk.gov.hmrc.ct.domain.ValidationConstants._

trait Validators {
import ValidatableBox._
  protected val boxId = getClass.getSimpleName

  protected def And(predicates: (() => Boolean)*)(): Boolean = {

    def allPredicatesTrue = predicates.forall {predicate => predicate()}
    allPredicatesTrue
  }

  protected def Or(predicates: (() => Boolean)*)(): Boolean = {
    def existsTruePredicate = predicates.exists(predicate => predicate())
    existsTruePredicate
  }

  protected def requiredErrorIf(predicate: => Boolean)(): Set[CtValidation] = {
      if (predicate)
        errorMessage("required")
      else
        Set.empty
  }

  protected def exceedsMax(value: Option[Int], max: Int = MAX_MONEY_AMOUNT_ALLOWED)(): Set[CtValidation] = {
    value match {
      case Some(v) if v > max => errorMessage("exceeds.max", Seq(max))
      case _ => Set.empty
    }
  }

  protected def belowMin(value: Option[Int], min: Int = MIN_MONEY_AMOUNT_ALLOWED)(): Set[CtValidation] = {
    value match {
      case Some(v) if v < min => errorMessage("below.min", Seq(min))
      case _ => Set.empty
    }
  }

  protected def belowMinWithMax(value: Option[Int], max: Int, min: Int = MIN_MONEY_AMOUNT_ALLOWED)(): Set[CtValidation] = {

    value match {
      case Some(v) if v < min => errorMessage("below.min", Seq(min, max) )
      case _ => Set.empty
    }
  }

  protected def validateMoney(value: Option[Int], min: Int = -99999999, max: Int = 99999999)(): Set[CtValidation] = {
    val formattedMin = "£" + commaForThousands(min)
    val formattedMax = "£" + commaForThousands(max)

    value match {
      case Some(x) if x < min => Set(CtValidation(boxId = Some(boxId), s"error.$boxId.below.min", Some(Seq(formattedMin, formattedMax))))
      case Some(x) if x > max => Set(CtValidation(boxId = Some(boxId), s"error.$boxId.above.max", Some(Seq(formattedMin, formattedMax))))
      case _ => Set.empty
    }
  }

  protected def cannotExistErrorIf(predicate: => Boolean)(): Set[CtValidation] = {
    if (predicate)
      errorMessage("cannot.exist")
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

  protected def errorMessage[A](messageKey: String, errorArguments: Seq[A] = Seq.empty): Set[CtValidation] = {
    val argumentsAsStrings: Seq[String] = errorArguments.map(a => a.toString)

    if (errorArguments.isEmpty) {
      Set(CtValidation(Some(boxId), s"error.$boxId.$messageKey"))
    } else {
        Set(CtValidation(Some(boxId), s"error.$boxId.$messageKey", Some(argumentsAsStrings)))
    }
  }
}
