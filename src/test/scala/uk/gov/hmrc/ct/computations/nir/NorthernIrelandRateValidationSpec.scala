/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.nir

import org.joda.time.LocalDate
import org.scalatest.mock.MockitoSugar
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever

case class LossesBroughtForwardAgainstTradingProfit(cp283: CP283 = CP283(None), cp283a: CP283a = CP283a(None), cp283b: CP283b = CP283b(None), cp283c: CP283c = CP283c(None), cp283d: CP283d = CP283d(None))

object LossesBroughtForwardAgainstTradingProfit {
  def apply(cp283: Option[Int], cp283a: Option[Int], cp283b: Option[Int], cp283c: Option[Int], cp283d: Option[Int]): LossesBroughtForwardAgainstTradingProfit = {
    new LossesBroughtForwardAgainstTradingProfit(CP283(cp283), CP283a(cp283a), CP283b(cp283b), CP283c(cp283c), CP283d(cp283d))
  }
  val emptyLossesBroughtForwardAgainstTP = LossesBroughtForwardAgainstTradingProfit()
}

case class AllLossesBroughtForward(cp281: CP281 = CP281(None), cp281a: CP281a = CP281a(None), cp281b: CP281b = CP281b(None), cp281c: CP281c = CP281c(None), cp281d: CP281d = CP281d(None))

object AllLossesBroughtForward {
  def apply(cp281: Option[Int], cp281a: Option[Int], cp281b: Option[Int], cp281c: Option[Int], cp281d: Option[Int]): AllLossesBroughtForward = {
    new AllLossesBroughtForward(CP281(cp281), CP281a(cp281a), CP281b(cp281b), CP281c(cp281c), CP281d(cp281d))
  }
  val emptyAllLossesBroughtForward = AllLossesBroughtForward()
}

case class LossesBroughtForwardAgainstNonTradingProfit(cp997: CP997 = CP997(None), cp997b: CP997b = CP997b(None), cp997c: CP997c = CP997c(None), cp997d: CP997d = CP997d(None), cp997e: CP997e = CP997e(None))

object LossesBroughtForwardAgainstNonTradingProfit {
  def apply(cp997: Option[Int], cp997b: Option[Int], cp997c: Option[Int], cp997d: Option[Int], cp997e: Option[Int]) = {
    new LossesBroughtForwardAgainstNonTradingProfit(CP997(cp997), CP997b(cp997b), CP997c(cp997c), CP997d(cp997d), CP997e(cp997e))
  }

  val emptyLossesBroughtForwardAgainstNTP = LossesBroughtForwardAgainstNonTradingProfit()
}

class NorthernIrelandRateValidationSpec extends WordSpec with Matchers with MockitoSugar {

  "NorthernIrelandRateValidation" should {
    "if NIR is active for current period with NIR losses carried forward from previous period" when {

      val table = Table(
        ("message",                                                         "CP117",    "CATO01",      "cpq17",          "allLossesBroughtForward",                                                         "lossesBroughtForwardAgainstTradingProfit",                                                         "cp284",     "cp288",     "cp288a",     "cp288b",   "lossesBroughtForwardAgainstNonTradingProfit"),
//        ("No TP, TL or NTP: losses should be carried forward",                    0,           0,         None,       Some(1000),     Some(0),       Some(1000),     Some(1000),     Some(0), )
        ("TP and no NTP: NIR losses == TP and should be applied 1:1",          1000,           0,    Some(true),         AllLossesBroughtForward(Some(1000), Some(0), Some(1000), Some(1000), Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(1000), Some(0), Some(1000), Some(1000), Some(0)),     Some(0),     Some(0),      Some(0),      Some(0),   LossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP)
//        ("TP and no NTP: NIR losses < TP and should be applied 1:1",           2000,           0,    Some(true),       Some(1000),     Some(0),       Some(1000),     Some(1000),     Some(0),  Some(1000),   Some(0), Some(1000), Some(1000),    Some(0),  Some(1000),  Some(1000),      Some(0),   Some(1000),      None)
//        ("TP and no NTP: NIR losses should be applied 1:1",                    1000,           0,    Some(true),       Some(1000),     Some(0),       Some(1000),     Some(1000),     Some(0),  Some(0),    Some(0), Some(1000), Some(1000),    Some(0),   Some(0),    Some(0),    Some(0),    Some(0))

//        ("No TP, No TL with NTP",                                               0,          600,     Some(0),  Some(0),    Some(0),       600),
//
//        ("TP, No TL or NTP, no previous losses",                                1000,       0,      Some(0),   Some(0),    Some(0),       1000),
//        ("TP, No TL or NTP, previous losses less than TP",                      1000,       0,      Some(0),   Some(0),    Some(400),     600),
//        ("TP, No TL or NTP, previous losses equals TP",                         1000,       0,      Some(0),   Some(0),    Some(1000),    0),
//
//        ("TP, No TL with NTP, no previous losses",                              1000,       600,    Some(0),   Some(0),    Some(0),       1600),
//        ("TP, No TL with NTP, previous losses less than TP",                    1000,       600,    Some(0),   Some(0),    Some(400),     1200),
//        ("TP, No TL with NTP, previous losses equals TP",                       1000,       600,    Some(0),   Some(0),    Some(1000),    600),
//        ("TP, No TL with NTP, previous losses equals TP, previous against NTP", 1000,       600,    Some(0),   Some(200),  Some(1000),    400),
//
//        ("TL, No TP or NTP, no previous losses",                                0,          0,      Some(0),    Some(0),  Some(0),       0),
//        ("TL, No TP or NTP, previous losses less than TL",                      0,          0,      Some(400),  Some(0),  Some(0),       0),
//        ("TL, No TP or NTP, previous losses equals TL",                         0,          0,      Some(800),  Some(0),  Some(0),       0),
//        ("TL, No TP or NTP, previous losses greater than TL",                   0,          0,      Some(1200), Some(0),  Some(0),       0),
//
//        ("TL, No TP or NTP, no previous losses - None",                         0,          0,      None,       Some(0),  None,          0),
//        ("TL, No TP or NTP, previous losses less than TL - None",               0,          0,      Some(400),  Some(0),  None,          0),
//        ("TL, No TP or NTP, previous losses equals TL - None",                  0,          0,      Some(800),  Some(0),  None,          0),
//        ("TL, No TP or NTP, previous losses greater than TL - None",            0,          0,      Some(1200), Some(0),  None,          0),
//
//        ("TL, No TP with NTP, losses less than NTP",                            0,          600,    Some(200),  Some(0),  Some(0),       400),
//        ("TL, No TP with NTP, losses equals NTP",                               0,          600,    Some(600),  Some(0),  Some(0),       0),
//        ("Tl, No TP with NTP, losses greater than NTP",                         0,          600,    Some(600),  Some(0),  Some(0),       0),
//
//        ("TL, No TP with NTP, losses less than NTP and cp998 is zero",          0,          600,    Some(0),    Some(0),  Some(0),       600),
//        ("TL, No TP with NTP, losses equals NTP and cp998 is zero",             0,          600,    Some(0),    Some(0),  Some(0),       600),
//        ("Tl, No TP with NTP, losses greater than NTP and cp998 is zero",       0,          600,    Some(0),    Some(0),  Some(0),       600),
//
//        ("TL, No TP with NTP, losses less than NTP - None",                     0,          600,    Some(200),  Some(0),  None,          400),
//        ("TL, No TP with NTP, losses equals NTP - None",                        0,          600,    Some(600),  Some(0),  None,          0),
//        ("Tl, No TP with NTP, losses greater than NTP - None",                  0,          600,    Some(600),  Some(0),  None,          0),
//
//        ("TL, No TP with NTP, losses less than NTP and cp998 is None",          0,          600,    None,       Some(0),  None,          600),
//        ("TL, No TP with NTP, losses equals NTP and cp998 is None",             0,          600,    None,       Some(0),  None,          600),
//        ("Tl, No TP with NTP, losses greater than NTP and cp998 is None",       0,          600,    None,       Some(0),  None,          600),
//
//        ("TP 1000, NTP 500, previous loss 2000",                                1000,       500,    None,       Some(0),  Some(1000),    500),
//        ("TL 500, NTP 1000",                                                    0,          1000,   Some(500),  Some(0),  None,          500),
//
//        ("TP with no NTP, CP998 or CP281 empty",                                500099,     0,      None,       Some(0),  None,          500099),
//        ("TP with NTP, CP998 or CP281 empty",                                   500099,     10000,  None,       Some(0),  None,          510099)
      )

      forAll(table) {
        (message: String,
         cp117: Int,
         cato01: Int,
         cpq17: Option[Boolean],
         allLossesBroughtForward: AllLossesBroughtForward,
         lossesBroughtForwardAgainstTradingProfit: LossesBroughtForwardAgainstTradingProfit,
         cp284: Option[Int],
         cp288: Option[Int],
         cp288a: Option[Int],
         cp288b: Option[Int],
         lossesBroughtForwardAgainstNonTradingProfit: LossesBroughtForwardAgainstNonTradingProfit
        ) => {

          message in {
            val computationsBoxRetriever = CompsWithAboutReturn()(nirActive = true,
              CP117(cp117),
              CPQ17(cpq17),
              CATO01(cato01),
              allLossesBroughtForward.cp281,
              allLossesBroughtForward.cp281a,
              allLossesBroughtForward.cp281c,
              lossesBroughtForwardAgainstTradingProfit.cp283a,
              lossesBroughtForwardAgainstTradingProfit.cp283b,
              lossesBroughtForwardAgainstTradingProfit.cp283c,
              CP288(cp288),
              CP288a(cp288a),
              CP288b(cp288b),
              lossesBroughtForwardAgainstNonTradingProfit.cp997b,
              lossesBroughtForwardAgainstNonTradingProfit.cp997c,
              lossesBroughtForwardAgainstNonTradingProfit.cp997d
            )

            computationsBoxRetriever.cp281b() shouldBe allLossesBroughtForward.cp281b
            computationsBoxRetriever.cp281d() shouldBe allLossesBroughtForward.cp281d
            computationsBoxRetriever.cp283() shouldBe lossesBroughtForwardAgainstTradingProfit.cp283
            computationsBoxRetriever.cp283d() shouldBe lossesBroughtForwardAgainstTradingProfit.cp283d
            computationsBoxRetriever.cp284() shouldBe CP284(cp284)
            computationsBoxRetriever.cp997() shouldBe lossesBroughtForwardAgainstNonTradingProfit.cp997
            computationsBoxRetriever.cp997e() shouldBe lossesBroughtForwardAgainstNonTradingProfit.cp997e

            computationsBoxRetriever.cp281.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp281a.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp281c.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283a.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283b.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283c.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp288a.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp288b.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp997b.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp997c.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp997d.validate(computationsBoxRetriever) shouldBe empty
          }
        }
      }
    }
  }

}

case class CompsWithAboutReturn(override val cp1: CP1 = CP1(LocalDate.parse("2019-04-01")),
                                override val cp2: CP2 = CP2(LocalDate.parse("2020-03-31")))
                               (nirActive: Boolean,
                                override val cp117: CP117,
                                override val cpQ17: CPQ17,
                                override val cato01: CATO01,
                                override val cp281: CP281,
                                override val cp281a: CP281a,
                                override val cp281c: CP281c,
                                override val cp283a: CP283a,
                                override val cp283b: CP283b,
                                override val cp283c: CP283c,
                                override val cp288: CP288,
                                override val cp288a: CP288a,
                                override val cp288b: CP288b,
                                override val cp997b: CP997b,
                                override val cp997c: CP997c,
                                override val cp997d: CP997d) extends StubbedComputationsBoxRetriever with AboutThisReturnBoxRetriever {
  override def b5(): B5 = B5(Some(nirActive))

  override def b6(): B6 = ???

  override def b7(): B7 = ???

  override def b8(): B8 = ???

  override def b40(): B40 = ???

  override def b45Input(): B45Input = ???

  override def b55(): B55 = ???

  override def b65(): B65 = ???

  override def b80A(): B80A = ???

  override def b85A(): B85A = ???

  override def b90A(): B90A = ???
}
