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

trait CtOptionalInteger extends CtValue[Option[Int]] {

  self: CtBoxIdentifier =>

  def plus(other: CtOptionalInteger): Int = {
    value.getOrElse(0) + other.value.getOrElse(0)
  }

  def minus(other: CtOptionalInteger): Int = {
    value.getOrElse(0) - other.value.getOrElse(0)
  }

  def plus(other: CtInteger): Int = {
    value.getOrElse(0) + other.value
  }

  def minus(other: CtInteger): Int = {
    value.getOrElse(0) - other.value
  }

  def plus(other: Int): Int = {
    value.getOrElse(0) + other
  }

  def minus(other: Int): Int = {
    value.getOrElse(0) - other
  }

  def asInt: Option[Int] = {
    value
  }
}

trait CtOptionalBigDecimal extends CtValue[Option[BigDecimal]] {

  self: CtBoxIdentifier =>

  def plus(other: BigDecimal): BigDecimal = {
    value.getOrElse(BigDecimal(0.0)) + other
  }

  def minus(other: BigDecimal): BigDecimal = {
    value.getOrElse(BigDecimal(0.0)) - other
  }

  def asInt: Option[Int] = value.map(_.toInt)


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
}

trait CtBoolean extends CtValue[Boolean] {

  self: CtBoxIdentifier =>

  def asBoolean:Option[Boolean] = Some(value)
}

trait CtOptionalBoolean extends CtValue[Option[Boolean]] {

  self: CtBoxIdentifier =>

  def asBoolean:Option[Boolean]=value
}

trait CtString extends CtValue[String] {

  self: CtBoxIdentifier =>
}

trait CtOptionalString extends CtValue[Option[String]] {

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

}

trait CtDate extends CtValue[LocalDate] {

  self: CtBoxIdentifier =>

  def asLocalDate = Some(value)

}

trait CtOptionalDate extends CtValue[Option[LocalDate]] {

  self: CtBoxIdentifier =>

  def asLocalDate=value

}

trait MustBeZeroOrPositive {

  self: CtInteger =>

  require(value >= 0)
}


