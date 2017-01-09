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

import org.joda.time.LocalDate

trait CtTypeConverters {
  implicit def convert(catoBoolean: CtBoolean) : Boolean = catoBoolean.value
  implicit def convert(catoInt: CtInteger) : Int = catoInt.value
  implicit def convert(catoInt: CtOptionalInteger) : Int = catoInt.value.getOrElse(0)
  implicit def convert(catoOptionalBigDecimal: CtOptionalBigDecimal) : Option[BigDecimal] = catoOptionalBigDecimal.value

}

trait CtValue[T] {
  def value: T

  def asBoxString = {
      value match {
        case Some(x) => Some(x.toString)
        case x: None.type => None
        case x => Some(x.toString)
      }
  }
}

trait OptionalCtValue[T] extends CtValue[Option[T]] {

  def hasValue = value.nonEmpty

  def noValue = value.isEmpty
}

trait CtOptionalInteger extends OptionalCtValue[Int] {

  self: CtBoxIdentifier =>

  def plus(other: CtOptionalInteger): Int = {
    orZero + other.orZero
  }

  def minus(other: CtOptionalInteger): Int = {
    orZero - other.orZero
  }

  def plus(other: CtInteger): Int = {
    orZero + other.value
  }

  def minus(other: CtInteger): Int = {
    orZero - other.value
  }

  def plus(other: Int): Int = {
    orZero + other
  }

  def minus(other: Int): Int = {
    orZero - other
  }

  def asInt: Option[Int] = {
    value
  }

  def orZero: Int = {
    value.getOrElse(0)
  }

  def isPositive: Boolean = orZero > 0
}

trait CtOptionalBigDecimal extends OptionalCtValue[BigDecimal] {

  self: CtBoxIdentifier =>

  def plus(other: BigDecimal): BigDecimal = {
    this.orZero + other
  }

  def minus(other: BigDecimal): BigDecimal = {
    this.orZero - other
  }

  def plus(other: CtBigDecimal): BigDecimal = {
    this.orZero + other.value
  }

  def minus(other: CtBigDecimal): BigDecimal = {
    this.orZero - other.value
  }

  def plus(other: CtOptionalBigDecimal): BigDecimal = {
    this.orZero + other.orZero
  }

  def minus(other: CtOptionalBigDecimal): BigDecimal = {
    this.orZero - other.orZero
  }

  def asInt: Option[Int] = value.map(_.toInt)

  def orZero: BigDecimal = {
    value.getOrElse(0)
  }

  def isPositive: Boolean = orZero > 0
}

trait CtInteger extends CtValue[Int] {

  self: CtBoxIdentifier =>

  def plus(other: CtInteger): Int = {
    value + other.value
  }

  def minus(other: CtInteger): Int = {
    value - other.value
  }

  def plus(other: CtOptionalInteger): Int = {
    value + other.value.getOrElse(0)
  }

  def minus(other: CtOptionalInteger): Int = {
    value - other.value.getOrElse(0)
  }

  def plus(other: Int): Int = {
    value + other
  }

  def minus(other: Int): Int = {
    value - other
  }

  def asInt:Option[Int]=Some(value)

  def isPositive: Boolean = value > 0
}

trait CtBoolean extends CtValue[Boolean] {

  self: CtBoxIdentifier =>

  def asBoolean:Option[Boolean] = Some(value)
}

trait CtOptionalBoolean extends OptionalCtValue[Boolean] {

  self: CtBoxIdentifier =>

  def asBoolean: Option[Boolean] = value

  def orFalse: Boolean = value.getOrElse(false)

  def inverse : Option[Boolean] = value.map { !_ }

  def isTrue = value.getOrElse(false) == true

  def isFalse = value.getOrElse(false) == false
}

trait CtString extends CtValue[String] {

  self: CtBoxIdentifier =>
}

trait CtOptionalString extends OptionalCtValue[String] {

  self: CtBoxIdentifier =>
}

trait CtBigDecimal extends CtValue[BigDecimal] {

  self: CtBoxIdentifier =>

  def plus(other: CtBigDecimal): BigDecimal = {
    value + other.value
  }

  def minus(other: CtBigDecimal): BigDecimal = {
    value - other.value
  }

  def multiply(other: CtBigDecimal): BigDecimal = {
    value * other.value
  }

  def multiply(other: CtInteger): BigDecimal = {
    value * other.value
  }

  def plus(other: BigDecimal): BigDecimal = {
    value + other
  }

  def minus(other: BigDecimal): BigDecimal = {
    value - other
  }

  def plus(other: CtOptionalBigDecimal): BigDecimal = {
    value + other.value.getOrElse(0.0)
  }

  def minus(other: CtOptionalBigDecimal): BigDecimal = {
    value - other.value.getOrElse(0.0)
  }

  def asInt:Option[Int] = Some(value.toInt)

  def isPositive: Boolean = value > BigDecimal(0)
}

trait CtDate extends CtValue[LocalDate] {

  self: CtBoxIdentifier =>

  def asLocalDate = Some(value)

}

trait CtOptionalDate extends OptionalCtValue[LocalDate] {

  self: CtBoxIdentifier =>

  def asLocalDate=value

}

trait MustBeZeroOrPositive {

  self: CtInteger =>

  require(value >= 0, "This box must cannot have a negative value.")
}

trait MustBeNoneOrZeroOrPositive {

  self: CtOptionalInteger =>

  require(value match {
    case Some(v) if v >= 0 => true
    case None => true
    case _ => false
  })
}

trait MustBeNoneOrZeroOrPositiveDecimal {

  self: CtOptionalBigDecimal =>

  require(value match {
    case Some(v) if v >= 0 => true
    case None => true
    case _ => false
  })
}
