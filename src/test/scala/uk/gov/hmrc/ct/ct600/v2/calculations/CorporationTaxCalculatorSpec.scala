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

package uk.gov.hmrc.ct.ct600.v2.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP1, CP2, CP295, HmrcAccountingPeriod}
import uk.gov.hmrc.ct.ct600._
import uk.gov.hmrc.ct.ct600.calculations.CorporationTaxCalculatorParameters
import uk.gov.hmrc.ct.ct600.v2._

class CorporationTaxCalculatorSpec extends WordSpec with Matchers {

  "CorporationTaxCalculator - rateOfTaxFy1 & rateOfTaxFy2" should {


    "return the correct constants for 2006 & 2007 when B37 is == 0 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2006, 8, 1)), CP2(new LocalDate(2007, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.30")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2006, 8, 1)), CP2(new LocalDate(2007, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.30")
    }

    "return the correct constants for 2007 & 2008 when B37 is == 0 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2007, 8, 1)), CP2(new LocalDate(2008, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.30")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2007, 8, 1)), CP2(new LocalDate(2008, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.28")
    }

    "return the correct constants for 2008 & 2009 when B37 is == 0 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2008, 8, 1)), CP2(new LocalDate(2009, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.28")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2008, 8, 1)), CP2(new LocalDate(2009, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.28")
    }

    "return the correct constants for 2009 & 2010 when B37 is == 0 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2009, 8, 1)), CP2(new LocalDate(2010, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.28")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2009, 8, 1)), CP2(new LocalDate(2010, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.28")
    }

    "return the correct constants for 2010 & 2011 when B37 is == 0 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2010, 8, 1)), CP2(new LocalDate(2011, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.28")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2010, 8, 1)), CP2(new LocalDate(2011, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.26")
    }

    "return the correct constants for 2011 & 2012 when B37 is == 0 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2011, 8, 1)), CP2(new LocalDate(2012, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.26")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2011, 8, 1)), CP2(new LocalDate(2012, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.24")
    }

    "return the correct constants for 2012 & 2013 when B37 is == 0 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2012, 8, 1)), CP2(new LocalDate(2013, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.24")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2012, 8, 1)), CP2(new LocalDate(2013, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.23")
    }

    "return the correct constants for 2006 & 2007 when B37 is == 1 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2006, 8, 1)), CP2(new LocalDate(2007, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.19")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2006, 8, 1)), CP2(new LocalDate(2007, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
    }

    "return the correct constants for 2007 & 2008 when B37 is == 1 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2007, 8, 1)), CP2(new LocalDate(2008, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2007, 8, 1)), CP2(new LocalDate(2008, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2008 & 2009 when B37 is == 1 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2008, 8, 1)), CP2(new LocalDate(2009, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2008, 8, 1)), CP2(new LocalDate(2009, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2009 & 2010 when B37 is == 1 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2009, 8, 1)), CP2(new LocalDate(2010, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2009, 8, 1)), CP2(new LocalDate(2010, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2010 & 2011 when B37 is == 1 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2010, 8, 1)), CP2(new LocalDate(2011, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2010, 8, 1)), CP2(new LocalDate(2011, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
    }

    "return the correct constants for 2011 & 2012 when B37 is == 1 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2011, 8, 1)), CP2(new LocalDate(2012, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2011, 8, 1)), CP2(new LocalDate(2012, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
    }

    "return the correct constants for 2012 & 2013 when B37 is == 1 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2012, 8, 1)), CP2(new LocalDate(2013, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2012, 8, 1)), CP2(new LocalDate(2013, 7, 31))), b37 = B37(1), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
    }

    "return the correct constants for 2013 & 2014 when B37 is >= 300001 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(300001),  b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.23")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(300001), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2013 & 2014 when B37 is >= 300001 and B42 is No" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(300001),  b42 = B42(false), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.23")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(300001), b42 = B42(false), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2013 & 2014 when B37 is < 300001 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(300000), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(300000), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
    }

    "return the correct constants for 2013 & 2014 when B37 is < 300001 and B42 is No" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(300000), b42 = B42(false), b39 = B39(None), b38 = B38(None)) shouldNot be (BigDecimal("0.20"))
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(300000), b42 = B42(false), b39 = B39(None), b38 = B38(None)) shouldNot be (BigDecimal("0.20"))
    }

    "return the correct constants for 2013 & 2014 when B37 is < 0 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(-1000), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.23")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(-1000), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2013 & 2014 when B37 is < 0 and B42 is No" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(-1000), b42 = B42(false), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.23")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(-1000), b42 = B42(false), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2013 & 2014 when B37 is == 0 and B42 is Yes" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.23")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(0), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2013 & 2014 when B37 is == 0 and B42 is No" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(0), b42 = B42(false), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.23")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31))), b37 = B37(0), b42 = B42(false), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2013 & 2014 when B37 is == 150,001 and B42 is Yes and B39 is 1" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(150001), b42 = B42(true), b39 = B39(Some(1)), b38 = B38(None)) shouldBe BigDecimal("0.23")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(150001), b42 = B42(true), b39 = B39(Some(1)), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2013 & 2014 when B37 is == 150,000 and B42 is Yes and B39 is 1" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(150000), b42 = B42(true), b39 = B39(Some(1)), b38 = B38(None)) shouldBe BigDecimal("0.20")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(150000), b42 = B42(true), b39 = B39(Some(1)), b38 = B38(None)) shouldBe BigDecimal("0.20")
    }

    "return the correct constants for 2013 & 2014 when B37 is == 100,001 and B42 is Yes and B39 is 2" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(100001), b42 = B42(true), b39 = B39(Some(2)), b38 = B38(None)) shouldBe BigDecimal("0.23")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(100001), b42 = B42(true), b39 = B39(Some(2)), b38 = B38(None)) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2013 & 2014 when B37 is == 100,000 and B42 is Yes and B39 is 2" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(100000), b42 = B42(true), b39 = B39(Some(2)), b38 = B38(None)) shouldBe BigDecimal("0.20")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(100000), b42 = B42(true), b39 = B39(Some(2)), b38 = B38(None)) shouldBe BigDecimal("0.20")
    }

    "return the correct constants for 2013 & 2014 when B37 is == 299,999 and B42 is Yes and B38 is empty and b39 is empty" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(299999), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(299999), b42 = B42(true), b39 = B39(None), b38 = B38(None)) shouldBe BigDecimal("0.20")
    }

    "return the correct constants for 2013 & 2014 when B37 is == 299,000 and B42 is Yes and B38 is 1 and b39 is empty" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(299999), b42 = B42(true), b39 = B39(None), b38 = B38(Some(1))) shouldBe BigDecimal("0.20")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(299999), b42 = B42(true), b39 = B39(None), b38 = B38(Some(1))) shouldBe BigDecimal("0.20")
    }

    "return the correct constants for 2013 & 2014 when B37 is == 299,000 and B42 is Yes and B38 is 2 and b39 is empty" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(299999), b42 = B42(true), b39 = B39(None), b38 = B38(Some(2))) shouldBe BigDecimal("0.23")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31))), b37 = B37(299999), b42 = B42(true), b39 = B39(None), b38 = B38(Some(2))) shouldBe BigDecimal("0.21")
    }

    "return the correct constants for 2014 & 2015 when B37 is == 299,000 and B42 is Yes and B38 is 2 and b39 is empty" in new Calc {
      rateOfTaxFy1(HmrcAccountingPeriod(CP1(new LocalDate(2015, 1, 1)), CP2(new LocalDate(2015, 12, 31))), b37 = B37(299999), b42 = B42(true), b39 = B39(None), b38 = B38(Some(2))) shouldBe BigDecimal("0.21")
      rateOfTaxFy2(HmrcAccountingPeriod(CP1(new LocalDate(2015, 1, 1)), CP2(new LocalDate(2015, 12, 31))), b37 = B37(299999), b42 = B42(true), b39 = B39(None), b38 = B38(Some(2))) shouldBe BigDecimal("0.20")
    }

  }

  "CorporationTaxCalculator - corporationTaxFy1 & corporationTaxFy2" should {

    "should return the correct value for corporationTaxFy1" in new Calc {
      corporationTaxFy1(B44(250), B45(BigDecimal("0.5"))) shouldBe B46(BigDecimal("125.0"))
    }

    "should return the correct value for corporationTaxFy2" in new Calc {
      corporationTaxFy2(B54(250), B55(BigDecimal("0.5"))) shouldBe B56(BigDecimal("125.0"))
    }

    "should return the correct value for corporationTaxFy1 with profit = 574 and rate = 20%" in new Calc {
      corporationTaxFy1(B44(574), B45(BigDecimal("0.20"))) shouldBe B46(BigDecimal("114.80"))
    }

    "should return the correct value for corporationTaxFy2 with profit = 578 and rate = 20%" in new Calc {
      corporationTaxFy2(B54(578), B55(BigDecimal("0.20"))) shouldBe B56(BigDecimal("115.60"))
    }
  }

  "CorporationTaxCalculator - corporationTaxFy1RoundedDown & corporationTaxFy2RoundedUp" should {

    "should return the correct value for corporationTaxFy1RoundedHalfDown" in new Calc {
      corporationTaxFy1RoundedHalfDown(B46(BigDecimal("1.50"))) shouldBe B46R(1)
    }

    "should return the correct value for corporationTaxFy1RoundedHalfUp" in new Calc {
      corporationTaxFy2RoundedHalfUp(B56(BigDecimal("1.50"))) shouldBe B56R(2)
    }
  }

  "CorporationTaxCalculator - corporationTaxNetOfMrr" should {

    "should return the correct value for corporationTaxFy1" in new Calc {
      corporationTaxNetOfMrr(B46(BigDecimal("250")), B56(BigDecimal("50")), B64(BigDecimal("25"))) shouldBe B65(BigDecimal("275.0"))
    }
  }

  "CorporationTaxCalculator - calculateApportionedProfitsChargeableFy1 & calculateApportionedProfitsChargeableFy2 for period spanning two financial years" should {

    val period = HmrcAccountingPeriod(CP1(new LocalDate(2013, 8, 1)), CP2(new LocalDate(2014, 7, 31)))

    "should return zero profit for FY1 when the period profit is zero" in new Calc {
      calculateApportionedProfitsChargeableFy1(CorporationTaxCalculatorParameters(CP295(0), period)).value shouldBe BigDecimal("0")
    }

    "should return zero profit for FY2 when the period profit is zero" in new Calc {
      calculateApportionedProfitsChargeableFy2(CorporationTaxCalculatorParameters(CP295(0), period)).value shouldBe BigDecimal("0")
    }

    "should return correct profit for FY1" in new Calc {
      calculateApportionedProfitsChargeableFy1(CorporationTaxCalculatorParameters(CP295(365), period)).value shouldBe BigDecimal("243")
    }

    "should return correct profit for FY2" in new Calc {
      calculateApportionedProfitsChargeableFy2(CorporationTaxCalculatorParameters(CP295(365), period)).value shouldBe BigDecimal("122")
    }

    "should return the correct value for apportioned profit FY1 rounded half up #1" in new Calc {
      val twoDayPeriod = HmrcAccountingPeriod(CP1(new LocalDate(2016, 3, 31)), CP2(new LocalDate(2016, 4, 1)))

      calculateApportionedProfitsChargeableFy1(CorporationTaxCalculatorParameters(CP295(11), twoDayPeriod)).value shouldBe BigDecimal("6")
    }

    "should return the correct values for FY1 and FY2 (values from live failure)" in new Calc {
      val splitPeriod = HmrcAccountingPeriod(CP1(new LocalDate(2015, 6, 1)), CP2(new LocalDate(2016, 5, 31)))

      calculateApportionedProfitsChargeableFy1(CorporationTaxCalculatorParameters(CP295(152181), splitPeriod)).value shouldBe BigDecimal("126818")
      calculateApportionedProfitsChargeableFy2(CorporationTaxCalculatorParameters(CP295(152181), splitPeriod)).value shouldBe BigDecimal("25363")
    }

    "should return FY1 and FY2 portions which add up to the total" in new Calc {
      val splitPeriod = HmrcAccountingPeriod(CP1(new LocalDate(2015, 6, 1)), CP2(new LocalDate(2016, 5, 31)))

      for(total <- 1 to 10000 by 11) {
        calculateApportionedProfitsChargeableFy2(CorporationTaxCalculatorParameters(CP295(total), splitPeriod)).value +
          calculateApportionedProfitsChargeableFy1(CorporationTaxCalculatorParameters(CP295(total), splitPeriod)).value shouldBe BigDecimal(total.toString)
      }
    }
  }

  "CorporationTaxCalculator - calculateApportionedProfitsChargeableFy1 & calculateApportionedProfitsChargeableFy2 for period matching a single financial year" should {

    val period = HmrcAccountingPeriod(CP1(new LocalDate(2013, 4, 1)), CP2(new LocalDate(2014, 3, 31)))

    "should return correct profit for FY1 when period does not span two financial years" in new Calc {
      calculateApportionedProfitsChargeableFy1(CorporationTaxCalculatorParameters(CP295(365), period)).value shouldBe BigDecimal("365")
    }

    "should return zero profit for FY2 when period does not span two financial years" in new Calc {
      calculateApportionedProfitsChargeableFy2(CorporationTaxCalculatorParameters(CP295(365), period)).value shouldBe BigDecimal("0")
    }
  }

  "CorporationTaxCalculator - financialYear1, financialYear2" should {

    "should return the same years when inside a single calendar year" in new Calc {
      val period = HmrcAccountingPeriod(CP1(new LocalDate(2013, 4, 1)), CP2(new LocalDate(2013, 8, 31)))
      financialYear1(period) shouldBe 2013
      financialYear2(period) shouldBe None
    }

    "should return the same years when spanning a full financial year" in new Calc {
      val period = HmrcAccountingPeriod(CP1(new LocalDate(2013, 4, 1)), CP2(new LocalDate(2014, 3, 31)))
      financialYear1(period) shouldBe 2013
      financialYear2(period) shouldBe None
    }

    "should return different years when spanning two financial years" in new Calc {
      val period = HmrcAccountingPeriod(CP1(new LocalDate(2013, 9, 1)), CP2(new LocalDate(2014, 8, 31)))
      financialYear1(period) shouldBe 2013
      financialYear2(period) shouldBe Some(2014)
    }
  }

  "CorporationTaxCalculator - totalCorporationTaxChargeable" should {

    "should return correct total" in new Calc {
      totalCorporationTaxChargeable(B46(100), B56(50)).value shouldBe BigDecimal("150")
    }
  }

  "CorporationTaxCalculator - finalCorporationTaxChargeable" should {

    "should return total net of mrr if mrr is being claimed" in new Calc {
      finalCorporationTaxChargeable(B42(true), B46(100), B56(150), B64(25)).value shouldBe BigDecimal("225")
    }

    "should return total without Mrr deducted if mrr is not being claimed" in new Calc {
      finalCorporationTaxChargeable(B42(false), B46(100), B56(150), B64(25)).value shouldBe BigDecimal("250")
    }
  }

  class Calc extends CorporationTaxCalculator
}
