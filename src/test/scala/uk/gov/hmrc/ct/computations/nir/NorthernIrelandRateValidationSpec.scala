/*
 * Copyright 2023 HM Revenue & Customs
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
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtValidation
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

case class NILossesBroughtForwardAgainstNonTradingProfit(cp997NI: CP997NI = CP997NI(None), cp997c: CP997c = CP997c(None), cp997d: CP997d = CP997d(None), cp997e: CP997e = CP997e(None))

object NILossesBroughtForwardAgainstNonTradingProfit {
  def apply(cp997NI: Option[Int], cp997c: Option[Int], cp997d: Option[Int], cp997e: Option[Int]) = {
    new NILossesBroughtForwardAgainstNonTradingProfit(CP997NI(cp997NI), CP997c(cp997c), CP997d(cp997d), CP997e(cp997e))
  }

  val emptyLossesBroughtForwardAgainstNTP = NILossesBroughtForwardAgainstNonTradingProfit()
}

case class LossesBroughtForwardAgainstNonTradingProfit(cp997: CP997 = CP997(None))

object LossesBroughtForwardAgainstNonTradingProfit {
  def apply(cp997: Option[Int]) = {
    new LossesBroughtForwardAgainstNonTradingProfit(CP997(cp997))
  }

  val emptyLossesBroughtForwardAgainstNTP = LossesBroughtForwardAgainstNonTradingProfit(Some(0))
}


class NorthernIrelandRateValidationSpec extends AnyWordSpec with Matchers with MockitoSugar {

  "NorthernIrelandRateValidation" should {
    "if NIR is active for current period with NIR losses carried forward from previous period" when {
      //                                                                                                                                                                       CP281      CP281a      CP281b       CP281c      CP281d                                                    CP283     CP283a     CP283b      CP283c      CP283d                                                                                                        CP997NI     CP997c      CP997d      CP997e
      val table = Table(
        ("message",                                                                                    "CP117",    "CATO01",       "cpq17",          "allLossesBroughtForward: Total      pre(MS)     post         NI_Loss     Main_Loss",    "lossesBroughtForwardAgainstTradingProfit: Total     pre        post        NI_Loss     Main_Loss",  "cp284",    "cp288",     "cp288a",    "cp288b",    "lossesBroughtForwardAgainstNonTradingProfit: Total       NI_Loss     Main_Loss   NI_Loss_Revalued"),
        ("TP and no NTP: NIR losses == TP and should be applied 1:1",                                     1000,           0,      Some(true),        AllLossesBroughtForward(Some(1000), Some(0),    Some(1000),  Some(1000),   Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(1000), Some(0),   Some(1000), Some(1000), Some(0)),    Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP),
        ("TP and no NTP: NIR losses < TP and should be applied 1:1",                                      2000,           0,      Some(true),        AllLossesBroughtForward(Some(1000), Some(0),    Some(1000),  Some(1000),   Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(1000), Some(0),   Some(1000), Some(1000), Some(0)),    Some(1000), Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP),
        ("TP and NTP: NIR losses > TP and no main stream losses",                                         2000,        1000,      Some(true),        AllLossesBroughtForward(Some(3000), Some(0),    Some(3000),  Some(3000),   Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(2000), Some(0),   Some(2000), Some(2000), Some(0)),    Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(657),  Some(1000), Some(0),    Some(657))),
        ("TP and NTP: NIR losses = TP and no main stream losses",                                         2000,        1000,      Some(true),        AllLossesBroughtForward(Some(2000), Some(0),    Some(2000),  Some(2000),   Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(2000), Some(0),   Some(2000), Some(2000), Some(0)),    Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(0),    Some(0),    Some(0),    Some(0))),
        ("TP and NTP: NIR losses > TP and main stream losses",                                            2000,        1000,      Some(true),        AllLossesBroughtForward(Some(2000), Some(0),    Some(2000),  Some(2000),   Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(2000), Some(0),   Some(2000), Some(2000), Some(0)),    Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(0),    Some(0),    Some(0),    Some(0))),
        ("TP and NTP with MSL losses, NIR losses < TP and MSL",                                          10000,        7000,      Some(true),        AllLossesBroughtForward(Some(10000),Some(0),    Some(10000), Some(1000),   Some(9000)),  LossesBroughtForwardAgainstTradingProfit(Some(10000),Some(0),   Some(10000),Some(1000), Some(9000)), Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(0),    Some(0),    Some(0),    Some(0))),
        ("TP and NTP with pre and post losses trying to set off between TP and NTP",                      2000,        3000,      Some(true),        AllLossesBroughtForward(Some(3500), Some(500),  Some(3000),  Some(1000),   Some(2000)),  LossesBroughtForwardAgainstTradingProfit(Some(1000), Some(0),   Some(1000), Some(1000), Some(0)),    Some(1000), Some(2500),  Some(500),   Some(2000),  NILossesBroughtForwardAgainstNonTradingProfit(Some(2000), Some(0),    Some(2000), Some(0))),
        ("TP & NTP with pre and post (NIR & MSL) losses and set off between TP and NTP then cfwd",        3000,        7000,      Some(true),        AllLossesBroughtForward(Some(15000),Some(2000), Some(13000), Some(10000),  Some(3000)),  LossesBroughtForwardAgainstTradingProfit(Some(3000), Some(0),   Some(3000), Some(3000), Some(0)),    Some(0),    Some(12000), Some(2000),  Some(10000), NILossesBroughtForwardAgainstNonTradingProfit(Some(5631), Some(4000), Some(3000), Some(2631))),
        ("TP & NTP with pre and post (NIR & MSL) losses and set off between TP and NTP then carryfwd",    5000,       15000,      Some(true),        AllLossesBroughtForward(Some(15000),Some(0),    Some(15000), Some(5000),   Some(10000)), LossesBroughtForwardAgainstTradingProfit(Some(5000), Some(0),   Some(5000), Some(5000), Some(0)),    Some(0),    Some(15000), Some(0),     Some(10500), NILossesBroughtForwardAgainstNonTradingProfit(Some(7789), Some(5000), Some(4500), Some(3289))),
        ("TP with no NTP with pre (NIR) losses and set off against the trading profit",                   5000,        3000,      Some(true),        AllLossesBroughtForward(Some(10000),Some(3000), Some(7000),  Some(3000),   Some(4000)),  LossesBroughtForwardAgainstTradingProfit(Some(5000), Some(1000),Some(4000), Some(3000), Some(1000)), Some(0),    Some(5000),  Some(2000),  Some(3000),  NILossesBroughtForwardAgainstNonTradingProfit(Some(2000), Some(0),    Some(2000), Some(0))),
        ("NIL & TP dont set against each other",                                                          4000,        2000,      Some(true),        AllLossesBroughtForward(Some(8000), Some(2000), Some(6000),  Some(6000),   Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(4000), Some(0),   Some(4000), Some(4000), Some(0)),    Some(0),    Some(8000),  Some(2000),  Some(6000),  NILossesBroughtForwardAgainstNonTradingProfit(Some(0),    Some(0),    Some(0),    Some(0))),
        ("NIL & TP set TP against post and pre Main Loss",                                                6000,           0,      Some(true),        AllLossesBroughtForward(Some(6000), Some(5500), Some(500),   Some(0),      Some(500)),   LossesBroughtForwardAgainstTradingProfit(Some(6000), Some(5500),Some(500),  Some(0),    Some(500)),  Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP),
        ("TP & no NTP with pre 01/04/17 MSTL and should be applied 1:1",                                  8000,           0,      Some(true),        AllLossesBroughtForward(Some(8000), Some(8000), Some(0),     Some(0),      Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(8000), Some(8000),Some(0),    Some(0),    Some(0)),    Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP),
        ("TP & NTP with pre 01/04/17 MSTL and should be applied 1:1 against NITP",                        2000,        1000,      Some(true),        AllLossesBroughtForward(Some(2000), Some(2000), Some(0),     Some(0),      Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(2000), Some(2000),Some(0),    Some(0),    Some(0)),    Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(0),    Some(0),    Some(0),    Some(0))),
        ("(NO) NTP with pre 01/04/17 MSTL and should carry forward when you cant set off",                   0,        1500,      Some(true),        AllLossesBroughtForward(Some(1500), Some(1500), Some(0),     Some(0),      Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0),   Some(0),    Some(0),    Some(0)),    Some(0),    Some(1500),  Some(1500),  Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(0),    Some(0),    Some(0),    Some(0))),
        ("NTL & NO NTP with pre 01/04/17 MSTL and should carry forward when you cant set off",               0,           0,      Some(true),        AllLossesBroughtForward(Some(2000), Some(2000), Some(0),     Some(0),      Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0),   Some(0),    Some(0),    Some(0)),    Some(0),    Some(2000),  Some(2000),  Some(0),     NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP),
        ("NTL & NO NTP with pre 01/04/17 MSTL and should carry forward when you cant set off & NTP tax",     0,        2500,      Some(true),        AllLossesBroughtForward(Some(2500), Some(2500), Some(0),     Some(0),      Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0),   Some(0),    Some(0),    Some(0)),    Some(0),    Some(2500),  Some(2500),  Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(0),    Some(0),    Some(0),    Some(0))),
        ("TP with post 01/04/17 MSTL only unused from earlier Aps to set off 1:1",                        3500,           0,      Some(true),        AllLossesBroughtForward(Some(3500), Some(0),    Some(3500),  Some(0),      Some(3500)),  LossesBroughtForwardAgainstTradingProfit(Some(3500), Some(0),   Some(3500), Some(0),    Some(3500)), Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP),
        ("TP & NTP with post 01/04/17 MSTL should be applied against TP or NTP",                          2000,        4000,      Some(true),        AllLossesBroughtForward(Some(4000), Some(0),    Some(4000),  Some(0),      Some(4000)),  LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0),   Some(0),    Some(0),    Some(0)),    Some(2000), Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(4000), Some(0),    Some(4000), Some(0))),
        ("NTP (No NTP) with post 01/04/17 MSTL should be applied against NTP",                               0,        1000,      Some(true),        AllLossesBroughtForward(Some(1000), Some(0),    Some(1000),  Some(0),      Some(1000)),  LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0),   Some(0),    Some(0),    Some(0)),    Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(1000), Some(0),    Some(1000), Some(0))),
        ("NITL (NO NITL) with post 01/04/17 MSTL and should carry forward when you cant set off",            0,           0,      Some(true),        AllLossesBroughtForward(Some(1500), Some(0),    Some(1500),  Some(0),      Some(1500)),  LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0),   Some(0),    Some(0),    Some(0)),    Some(0),    Some(1500),  Some(0),     Some(1500),  NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP),
        ("TP & NTP with pre & post 01/04/17 MSTL can only set post 01/04/17 against TP",                  4000,        2000,      Some(true),        AllLossesBroughtForward(Some(4000), Some(2000), Some(2000),  Some(0),      Some(2000)),  LossesBroughtForwardAgainstTradingProfit(Some(4000), Some(2000),Some(2000), Some(0),    Some(2000)), Some(0),    Some(0),     Some(0),     Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(0),    Some(0),    Some(0),    Some(0))),
        ("TP & NTP with pre & post 01/04/17 MSTL can set pre MSL against TP and/or post MSL against NTP", 3000,        4000,      Some(true),        AllLossesBroughtForward(Some(6000), Some(3000), Some(3000),  Some(0),      Some(3000)),  LossesBroughtForwardAgainstTradingProfit(Some(3000), Some(1500),Some(1500), Some(0),    Some(1500)), Some(0),    Some(3000),  Some(1500),  Some(1500),  NILossesBroughtForwardAgainstNonTradingProfit(Some(1500), Some(0),    Some(1500), Some(0))),
        ("NTP with post & pre TL brought forward and use post against NTP and carry forward pre",            0,        3000,      Some(true),        AllLossesBroughtForward(Some(5000), Some(2000), Some(3000),  Some(1500),   Some(1500)),  LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0),   Some(0),    Some(0),    Some(0)),    Some(0),    Some(2000),  Some(2000),  Some(3000),  NILossesBroughtForwardAgainstNonTradingProfit(Some(2486), Some(1500), Some(1500), Some(986))),
        ("BIG FISH TEST: Victoria",                                                                       3000,        7000,      Some(true),        AllLossesBroughtForward(Some(15000),Some(2000), Some(13000), Some(10000),  Some(3000)),  LossesBroughtForwardAgainstTradingProfit(Some(3000), Some(0),   Some(3000), Some(3000), Some(0)),    Some(0),    Some(2000),  Some(2000),  Some(0),     NILossesBroughtForwardAgainstNonTradingProfit(Some(5631), Some(4000), Some(3000), Some(2631)))
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
         northernIrelandlossesBroughtForwardAgainstNonTradingProfit: NILossesBroughtForwardAgainstNonTradingProfit
        ) => {

          message in {
            val computationsBoxRetriever = CompsWithAboutReturn()(
              nirActive = true,
              areLossesFromNIRActivity = true,
              CP117(cp117),
              CPQ17(cpq17),
              CATO01(cato01),
              allLossesBroughtForward.cp281,
              allLossesBroughtForward.cp281a,
              allLossesBroughtForward.cp281c,
              lossesBroughtForwardAgainstTradingProfit.cp283a,
              lossesBroughtForwardAgainstTradingProfit.cp283b,
              lossesBroughtForwardAgainstTradingProfit.cp283c,
              lossesBroughtForwardAgainstTradingProfit.cp283d,
              CP288(cp288),
              CP288a(cp288a),
              CP288b(cp288b),
              northernIrelandlossesBroughtForwardAgainstNonTradingProfit.cp997c,
              northernIrelandlossesBroughtForwardAgainstNonTradingProfit.cp997d,
              CP44(0)
            )

            computationsBoxRetriever.cp281b() shouldBe allLossesBroughtForward.cp281b
            computationsBoxRetriever.cp281d() shouldBe allLossesBroughtForward.cp281d
            computationsBoxRetriever.cp283() shouldBe lossesBroughtForwardAgainstTradingProfit.cp283
            computationsBoxRetriever.cp284() shouldBe CP284(cp284)
            computationsBoxRetriever.cp997NI() shouldBe northernIrelandlossesBroughtForwardAgainstNonTradingProfit.cp997NI
            computationsBoxRetriever.cp997e() shouldBe northernIrelandlossesBroughtForwardAgainstNonTradingProfit.cp997e

            computationsBoxRetriever.cp281.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp281a.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp281c.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283a.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283b.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283c.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283d.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp288a.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp288b.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp997c.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp997d.validate(computationsBoxRetriever) shouldBe empty
          }
        }
      }
    }

    "Testing possible failure tests when NIR is active for current period with NIR losses carried forward from previous period" when {

      //                                                                                                                                                                                      CP281      CP281a     CP281b       CP281c      CP281d                                                  CP283     CP283a    CP283b      CP283c      CP283d                                                                                                              CP997NI     CP997c      CP997d      CP997e
      val table = Table(
        ("message",                                                                                                     "CP117",    "CATO01",       "cpq17",        "allLossesBroughtForward: Total      pre(MS)    post        NI_Loss     Main_Loss",   "lossesBroughtForwardAgainstTradingProfit: Total      pre      post        NI_Loss     Main_Loss",   "cp284",     "cpnewbox",     "cpnewboxa","cpnewboxb", "NIlossesBroughtForwardAgainstNonTradingProfit: Total       NI_Loss    Main_Loss   NI_Loss_Revalued", "Exceptions"),
        ("It should throw error msg after using too many losses against TP than what you have",                          500,           0,          Some(true),     AllLossesBroughtForward(Some(1000), Some(0),    Some(1000), Some(1000), Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(2000), Some(0), Some(2000), Some(2000), Some(0)),       Some(-1500),  Some(0),        Some(0),    Some(0),     NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP, Set(CtValidation(Some("CP283a"), "error.CP283.exceeds.totalProfit", None), CtValidation(Some("CP283b"), "error.CP283.exceeds.totalProfit", None), CtValidation(None,"error.CP283b.exceeds.tradingProfit.error",None), CtValidation(None,"error.lossesBroughtForwardError.error1",None))),
        ("It should throw error msg after using too many losses against NTP than what you have",                           0,         200,          Some(true),     AllLossesBroughtForward(Some(1000), Some(0),    Some(1000), Some(1000), Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0), Some(0),    Some(0),    Some(0)),       Some(0),     Some(0),        Some(0),    Some(0),      NILossesBroughtForwardAgainstNonTradingProfit(Some(657),  Some(1000), Some(0),    Some(657)), Set(CtValidation(Some("CP997c"), "error.CP997.exceeds.nonTradingProfit",None),CtValidation(Some("CP997d"), "error.CP997.exceeds.nonTradingProfit",None))),
        ("It should throw error msg when trying to carry forward more pre 01/04/17 MSTL where NTL & NO NTP ",            200,           0,          Some(true),     AllLossesBroughtForward(Some(2000), Some(2000), Some(0),    Some(0),    Some(0)),     LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0), Some(0),    Some(0),    Some(0)),       Some(200),   Some(2000),     Some(3000), Some(0),      NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP,  Set(CtValidation(None, "error.CP281a.breakdown.sum.incorrect",None))),
        ("It should throw error msg when losses brought forward totals is not correct",                                 1000,           0,          Some(true),     AllLossesBroughtForward(Some(2000), Some(1000), Some(1000), Some(500),  Some(500)),   LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0), Some(0),    Some(0),    Some(0)),       Some(1000),  Some(2000),     Some(0),    Some(2000),   NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP,  Set(CtValidation(None, "error.CP281a.breakdown.sum.incorrect",None), CtValidation(None,"error.lossesBroughtForwardError.error1"))),
        ("It should throw error msg when trying to carry forward more post 01/04/17 MSTL where NTL & NO NTP ",           200,           0,          Some(true),     AllLossesBroughtForward(Some(2000), Some(0),    Some(2000), Some(1000), Some(1000)),  LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0), Some(0),    Some(0),    Some(0)),       Some(200),   Some(2000),     Some(3000),    Some(0),   NILossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP,  Set(CtValidation(None, "error.CP281a.breakdown.sum.incorrect",None), CtValidation(None,"error.lossesBroughtForwardError.error1")))


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
         northernIrelandLossesBroughtForwardAgainstNonTradingProfit: NILossesBroughtForwardAgainstNonTradingProfit,
         exceptions:Set[CtValidation]
        ) => {

          message in {
            val computationsBoxRetriever = CompsWithAboutReturn()(
              nirActive = true,
              areLossesFromNIRActivity = true,
              CP117(cp117),
              CPQ17(cpq17),
              CATO01(cato01),
              allLossesBroughtForward.cp281,
              allLossesBroughtForward.cp281a,
              allLossesBroughtForward.cp281c,
              lossesBroughtForwardAgainstTradingProfit.cp283a,
              lossesBroughtForwardAgainstTradingProfit.cp283b,
              lossesBroughtForwardAgainstTradingProfit.cp283c,
              lossesBroughtForwardAgainstTradingProfit.cp283d,
              CP288(cp288),
              CP288a(cp288a),
              CP288b(cp288b),
              northernIrelandLossesBroughtForwardAgainstNonTradingProfit.cp997c,
              northernIrelandLossesBroughtForwardAgainstNonTradingProfit.cp997d,
              CP44(0)
            )

            computationsBoxRetriever.cp281 shouldBe allLossesBroughtForward.cp281
            computationsBoxRetriever.cp281b() shouldBe allLossesBroughtForward.cp281b
            computationsBoxRetriever.cp281c shouldBe allLossesBroughtForward.cp281c
            computationsBoxRetriever.cp281d() shouldBe allLossesBroughtForward.cp281d
            computationsBoxRetriever.cp283() shouldBe  lossesBroughtForwardAgainstTradingProfit.cp283
            computationsBoxRetriever.cp284() shouldBe CP284(cp284)
            computationsBoxRetriever.cp997NI() shouldBe northernIrelandLossesBroughtForwardAgainstNonTradingProfit.cp997NI
            computationsBoxRetriever.cp997e() shouldBe northernIrelandLossesBroughtForwardAgainstNonTradingProfit.cp997e

              computationsBoxRetriever.cp281.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp281a.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp281c.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp283a.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp283b.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp283c.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp283d.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp288a.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp288b.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp997c.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp997c.validate(computationsBoxRetriever) ++
              computationsBoxRetriever.cp997d.validate(computationsBoxRetriever) shouldBe exceptions

          }
        }
      }
    }
  }

  "Losses brought forward from previous period without Northern Ireland rate involved" when {

    val table = Table(

      //                                                                                                                               CP281       CP281a     CP281b       CP281c      CP281d                                                   CP283     CP283a      CP283b      CP283c   CP283d
      ("message",                                                "CP117",    "CATO01",      "cpq17",         "allLossesBroughtForward: Total       pre         post        NI_Loss  Main_Loss",      "lossesBroughtForwardAgainstTradingProfit: Total     pre         post        NI_Loss  Main_Loss",    "cp284",    "cp288",     "cp288a",   "cp288b",  "lossesBroughtForwardAgainstNonTradingProfit",                                    "cp44"),
      ("Losses before 1/4/2017 & Losses after 1/4/2017, No NTP",  2000,            0,        Some(true),      AllLossesBroughtForward(Some(3000),  Some(1500), Some(1500),    None,  Some(1500)),    LossesBroughtForwardAgainstTradingProfit(Some(2000), Some(500),  Some(1500), Some(0), Some(1500)),   Some(0),    Some(1000),  Some(1000), Some(0),    LossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP, 5000),

      ("TP & NTP with pre and post (Non NIR) losses trying set "+
        "off against TP and carrying forward",                    5000,         3000,        Some(true),      AllLossesBroughtForward(Some(10000), Some(3000), Some(7000),    None,  Some(7000)),    LossesBroughtForwardAgainstTradingProfit(Some(5000), Some(3000), Some(2000), None,    Some(2000)),   Some(0),    Some(5000),  Some(0),    Some(5000), LossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP, 8000),

      ("NTP with pre and post (Non NIR) losses trying to carry" +
        "forward without setting off against TP and NTP",            0,         3000,        Some(true),      AllLossesBroughtForward(Some(5000),  Some(1500), Some(3500),    None,  Some(3500)),    LossesBroughtForwardAgainstTradingProfit(Some(0),    Some(0),    Some(0),    None,    Some(0)),      Some(0),    Some(5000),  Some(1500), Some(3500), LossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP, 3000),
      (
        "TP with pre and post (Non NIR) losses trying to set off "+
        "against the TP and carrying forward the remainder",       2000,           0,        Some(true),      AllLossesBroughtForward(Some(1000),  Some(500),  Some(500),     None,  Some(500)),     LossesBroughtForwardAgainstTradingProfit(Some(500),  Some(250),  Some(250),  None,    Some(250)),    Some(1500), Some(750),   Some(250),  Some(500),  LossesBroughtForwardAgainstNonTradingProfit.emptyLossesBroughtForwardAgainstNTP, 2000)


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
       lossesBroughtForwardAgainstNonTradingProfit: LossesBroughtForwardAgainstNonTradingProfit,
       cp44: Int
      ) => {

        message in {
          val computationsBoxRetriever = CompsWithAboutReturn()(
            nirActive = false,
            areLossesFromNIRActivity=false,
            CP117(cp117),
            CPQ17(cpq17),
            CATO01(cato01),
            allLossesBroughtForward.cp281,
            allLossesBroughtForward.cp281a,
            allLossesBroughtForward.cp281c,
            lossesBroughtForwardAgainstTradingProfit.cp283a,
            lossesBroughtForwardAgainstTradingProfit.cp283b,
            lossesBroughtForwardAgainstTradingProfit.cp283c,
            lossesBroughtForwardAgainstTradingProfit.cp283d,
            CP288(cp288),
            CP288a(cp288a),
            CP288b(cp288b),
            CP997c(None),
            CP997d(None),
            CP44(cp44)

          )

          computationsBoxRetriever.cp281b() shouldBe allLossesBroughtForward.cp281b
          computationsBoxRetriever.cp281d() shouldBe allLossesBroughtForward.cp281d
          computationsBoxRetriever.cp283() shouldBe lossesBroughtForwardAgainstTradingProfit.cp283
          computationsBoxRetriever.cp284() shouldBe CP284(cp284)


            computationsBoxRetriever.cp281.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp281a.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp281c.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283a.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283b.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283c.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp283d.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp288a.validate(computationsBoxRetriever) ++
            lossesBroughtForwardAgainstNonTradingProfit.cp997.validate(computationsBoxRetriever) ++
            computationsBoxRetriever.cp288b.validate(computationsBoxRetriever) shouldBe empty
        }
      }
    }
  }
}



case class CompsWithAboutReturn(override val cp1: CP1 = CP1(LocalDate.parse("2019-04-01")),
                                override val cp2: CP2 = CP2(LocalDate.parse("2020-03-31")))
                               (nirActive: Boolean,
                                areLossesFromNIRActivity:Boolean,
                                override val cp117: CP117,
                                override val cpQ17: CPQ17,
                                override val cato01: CATO01,
                                override val cp281: CP281,
                                override val cp281a: CP281a,
                                override val cp281c: CP281c,
                                override val cp283a: CP283a,
                                override val cp283b: CP283b,
                                override val cp283c: CP283c,
                                override val cp283d: CP283d,
                                override val cp288: CP288,
                                override val cp288a: CP288a,
                                override val cp288b: CP288b,
                                override val cp997c: CP997c,
                                override val cp997d: CP997d,
                                override val cp44: CP44) extends StubbedComputationsBoxRetriever with AboutThisReturnBoxRetriever {
  override def b5(): B5 = ???

  override def b6(): B6 = ???

  override def b7(): B7 = B7(Some(nirActive))

  override def b8(): B8 = ???

  override def b40(): B40 = ???

  override def b45Input(): B45Input = ???

  override def b55(): B55 = ???

  override def b65(): B65 = ???

  override def b80A(): B80A = ???

  override def b85A(): B85A = ???

  override def b90A(): B90A = ???

  override def cpQ117(): CPQ117 = CPQ117(Some(areLossesFromNIRActivity))

  override def cpQ19(): CPQ19 = CPQ19(Some(false))

}