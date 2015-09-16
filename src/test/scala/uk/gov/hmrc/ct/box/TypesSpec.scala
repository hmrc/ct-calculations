package uk.gov.hmrc.ct.box

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}

class TypesSpec extends WordSpec with Matchers {

  "CtOptionalInteger" should {

    "asInt will return the value " in {

      val expectedVal = Some(26)

      class TestBox extends CtBoxIdentifier("Test") with CtOptionalInteger {
        override def value: Option[Int] = expectedVal
      }

      new TestBox().asInt shouldBe expectedVal
    }

  }

  "CtOptionalBigDecimal" should {

    "asInt will return the value " in {

      val expectedVal = Some(26)

      class TestBox extends CtBoxIdentifier("Test") with CtOptionalBigDecimal{
        override def value: Option[BigDecimal] = Some(BigDecimal(26.8))
      }

      new TestBox().asInt shouldBe expectedVal
    }

  }

  "CtInteger" should {

    "asInt will return the value " in {

      val expectedVal = Some(26)

      class TestBox extends CtBoxIdentifier("Test") with CtInteger {
        override def value: Int = 26
      }

      new TestBox().asInt shouldBe expectedVal
    }

  }

  "CtBigDecimal" should {

    "asInt will return the value " in {

      val expectedVal = Some(26)

      class TestBox extends CtBoxIdentifier("Test") with CtBigDecimal{
        override def value: BigDecimal = BigDecimal(26.8)
      }

      new TestBox().asInt shouldBe expectedVal
    }

  }

  "CtBoolean" should {

    "asBoolean will return the value " in {

      val expectedVal = Some(true)

      class TestBox extends CtBoxIdentifier("Test") with CtBoolean{
        override def value: Boolean = true
      }

      new TestBox().asBoolean shouldBe expectedVal
    }

  }

  "CtOptionalBoolean" should {

    "asBoolean will return the value " in {

      val expectedVal = Some(true)

      class TestBox extends CtBoxIdentifier("Test") with CtOptionalBoolean{
        override def value: Option[Boolean] = Some(true)
      }

      new TestBox().asBoolean shouldBe expectedVal
    }

  }

  "CtDate" should {

    "asLocalDate will return the value " in {

      val expectedVal = Some(new LocalDate(2015,1,1))

      class TestBox extends CtBoxIdentifier("Test") with CtDate{


        override def value: LocalDate = new LocalDate(2015,1,1)
      }

      new TestBox().asLocalDate shouldBe expectedVal
    }

  }


  "CtOptionalDate" should {

    "asLocalDate will return the value " in {

      val expectedVal = Some(new LocalDate(2015,1,1))

      class TestBox extends CtBoxIdentifier("Test") with CtOptionalDate{


        override def value: Option[LocalDate] = Some(new LocalDate(2015,1,1))
      }

      new TestBox().asLocalDate shouldBe expectedVal
    }

  }

}
