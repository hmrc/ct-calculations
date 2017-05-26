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

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.computations._


class TradingLossesCP286MaximumCalculatorSpec extends WordSpec with Matchers {

  val table = Table(
    ("message",                                                         "CP117",    "CATO01",   "cp998",  "cp997",    "cp283",   "result"),
    ("No TP, TL or NTP",                                                    0,          0,       Some(0),  Some(0),    Some(0),       0),

    ("No TP, No TL with NTP",                                               0,          600,     Some(0),  Some(0),    Some(0),       600),

    ("TP, No TL or NTP, no previous losses",                                1000,       0,      Some(0),   Some(0),    Some(0),       1000),
    ("TP, No TL or NTP, previous losses less than TP",                      1000,       0,      Some(0),   Some(0),    Some(400),     600),
    ("TP, No TL or NTP, previous losses equals TP",                         1000,       0,      Some(0),   Some(0),    Some(1000),    0),

    ("TP, No TL with NTP, no previous losses",                              1000,       600,    Some(0),   Some(0),    Some(0),       1600),
    ("TP, No TL with NTP, previous losses less than TP",                    1000,       600,    Some(0),   Some(0),    Some(400),     1200),
    ("TP, No TL with NTP, previous losses equals TP",                       1000,       600,    Some(0),   Some(0),    Some(1000),    600),
    ("TP, No TL with NTP, previous losses equals TP, previous against NTP", 1000,       600,    Some(0),   Some(200),  Some(1000),    400),

    ("TL, No TP or NTP, no previous losses",                                0,          0,      Some(0),    Some(0),  Some(0),       0),
    ("TL, No TP or NTP, previous losses less than TL",                      0,          0,      Some(400),  Some(0),  Some(0),       0),
    ("TL, No TP or NTP, previous losses equals TL",                         0,          0,      Some(800),  Some(0),  Some(0),       0),
    ("TL, No TP or NTP, previous losses greater than TL",                   0,          0,      Some(1200), Some(0),  Some(0),       0),

    ("TL, No TP or NTP, no previous losses - None",                         0,          0,      None,       Some(0),  None,          0),
    ("TL, No TP or NTP, previous losses less than TL - None",               0,          0,      Some(400),  Some(0),  None,          0),
    ("TL, No TP or NTP, previous losses equals TL - None",                  0,          0,      Some(800),  Some(0),  None,          0),
    ("TL, No TP or NTP, previous losses greater than TL - None",            0,          0,      Some(1200), Some(0),  None,          0),

    ("TL, No TP with NTP, losses less than NTP",                            0,          600,    Some(200),  Some(0),  Some(0),       400),
    ("TL, No TP with NTP, losses equals NTP",                               0,          600,    Some(600),  Some(0),  Some(0),       0),
    ("Tl, No TP with NTP, losses greater than NTP",                         0,          600,    Some(600),  Some(0),  Some(0),       0),

    ("TL, No TP with NTP, losses less than NTP and cp998 is zero",          0,          600,    Some(0),    Some(0),  Some(0),       600),
    ("TL, No TP with NTP, losses equals NTP and cp998 is zero",             0,          600,    Some(0),    Some(0),  Some(0),       600),
    ("Tl, No TP with NTP, losses greater than NTP and cp998 is zero",       0,          600,    Some(0),    Some(0),  Some(0),       600),

    ("TL, No TP with NTP, losses less than NTP - None",                     0,          600,    Some(200),  Some(0),  None,          400),
    ("TL, No TP with NTP, losses equals NTP - None",                        0,          600,    Some(600),  Some(0),  None,          0),
    ("Tl, No TP with NTP, losses greater than NTP - None",                  0,          600,    Some(600),  Some(0),  None,          0),

    ("TL, No TP with NTP, losses less than NTP and cp998 is None",          0,          600,    None,       Some(0),  None,          600),
    ("TL, No TP with NTP, losses equals NTP and cp998 is None",             0,          600,    None,       Some(0),  None,          600),
    ("Tl, No TP with NTP, losses greater than NTP and cp998 is None",       0,          600,    None,       Some(0),  None,          600),

    ("TP 1000, NTP 500, previous loss 2000",                                1000,       500,    None,       Some(0),  Some(1000),    500),
    ("TL 500, NTP 1000",                                                    0,          1000,   Some(500),  Some(0),  None,          500),

    ("TP with no NTP, CP998 or CP281 empty",                                500099,     0,      None,       Some(0),  None,          500099),
    ("TP with NTP, CP998 or CP281 empty",                                   500099,     10000,  None,       Some(0),  None,          510099)
  )

  "TradingLossesCP286Maximum" should {
    "calculate the maximum CP286 allow" in new TradingLossesCP286MaximumCalculator {
      forAll(table) {
        (message: String,
         cp117: Int,
         cato01: Int,
         cp998: Option[Int],
         cp997: Option[Int],
         cp283: Option[Int],
         result: Int) => {
          calculateMaximumCP286(CP117(cp117), CATO01(cato01), CP283(cp283), CP997(cp997), CP998(cp998)) shouldBe result
        }
      }
    }
  }
}
