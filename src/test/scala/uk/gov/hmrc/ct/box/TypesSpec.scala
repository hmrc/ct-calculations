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
