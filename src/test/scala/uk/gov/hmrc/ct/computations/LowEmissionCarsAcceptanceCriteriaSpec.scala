/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.{CATO11, CATO12}

class LowEmissionCarsAcceptanceCriteriaSpec extends WordSpec with Matchers {

  /*    CPQ8        Ceased Trading
        CP78        Written down value brought forward
        CP666       Written down value of Special Rate Pool brought forward
        CP81_Input  Expenditure qualifying for first year allowance (FYA)
        CP81        ""
        CP82        Additions Qualifying for writing down allowance
        CP83        Expenditure qualifying for Annual Investment Allowance (AIA)
        CP84        Disposal proceeds
        CP673       Market value of unsold assets
        CP667       Proceeds from disposals from special rate pool
        CP672       Proceeds from disposals from main pool
        CPaux1      FYA Rate Pool
        CPaux2      Main Rate Pool
        CPaux3      Special Rate Pool
        CP87_Input  FYA Claimed
        CP87        ""
        CP88        AIA Claimed
        CP89        Written down allowance calimed
        CP668       Written down allowance claimed from special rate pool
        CP90        Balance Allowance
        CP186       Total Allowances claimed
        CP91        Main pool balancing charge
        CP671       Balancing charge
        CP670       Special Rate pool balancing charge
        CP92        Main pool written down value carried forward
        CP669       Special Rate pool written down value carried forward
     */


  "Low Emmission Cars calculations" should {

    "calculate values for Companies Still Trading" in {
      val companiesStillTrading =
        Table(
          ("Scenario",
            "LEC01",
            "CPQ8", "CP78", "CP666", "CP81_Input", "CP82", "CP83", "CP667", "CP672", "CP87_Input","CP668", "CP670", "CP88", "CP89", "CP186", "CP91", "CP671", "CP92", "CP669"),

          ("Scenario 1 - Company still trading, some AIA can be claimed from the main pool, no disposals were made, user claims some but not all they're entitled to.",
            List(mainRatePoolCar(100)),
            Some(false), Some(50), None, Some(0), Some(15), Some(40), None, Some(0), Some(0),Some(0), Some(0), Some(0), Some(30), Some(30), None, None, Some(175), Some(0)),

          ("Scenario 2 - Company still trading, some AIA, main pool allowance can be claimed from the main pool, there have been disposals on the main pool, but lower than the value of the pool. " +
            "User claims some of the allowance but not all they're entitled to.",
            List(mainRatePoolCar(100)),
            Some(false), Some(50), None, Some(0), Some(15), Some(40), None, Some(48), Some(0), None, Some(0), Some(0), Some(21), Some(21), None, None, Some(136), Some(0)),

          ("Scenario 3 - Company still trading, some AIA, there have been disposals on the main pool, higher than the value of the pool " +
            "(there will be balancing charges). User can't claim anything from the main pool.",
            List(mainRatePoolCar(270)),
            Some(false), Some(50), None, Some(0), Some(47), Some(69), None, Some(3000), Some(0),None, Some(0), Some(0), None, Some(0), Some(2564), Some(2564), Some(0), Some(0)),

          ("Scenario 4 - Company still trading, some AIA and FYA, there have been disposals on the main pool and secondary pool, " +
            "higher than the value of both pools (there will be balancing charges). User can't claim anything from the main pool.",
            List(mainRatePoolCar(270), specialRatePoolCar(594)),
            Some(false), Some(11), Some(98), Some(31), Some(43), Some(77), Some(2111), Some(3500), Some(0), None, Some(1419), None, None, Some(0), Some(3068), Some(3068), Some(0), Some(0)),

          ("Scenario 5 - Company still trading, some AIA and FYA (also FYA cars), there have been disposals on the main pool and secondary pool, " +
            "the main disposals higher than the value of the main pool but the special rate disposals still leave some remaining special rate allowance " +
            "to be claimed (there will be only balancing charges on the main pool). user can't claim anything from the main pool but can claim from the secondary pool.",
            List(fyaRatePoolCar(25), mainRatePoolCar(50), specialRatePoolCar(600)),
            Some(false), Some(11), Some(98), Some(31), Some(43), Some(77), Some(4), Some(3500), Some(21), Some(20), Some(0), Some(64), None, Some(85), Some(3348), Some(3348), Some(0), Some(674))
        )

      forAll(companiesStillTrading) {
        (scenario: String,
         lec01: List[Car],
         cpq8: Option[Boolean],
         cp78: Option[Int],
         cp666: Option[Int],
         cp81_Input: Option[Int],
         cp82: Option[Int],
         cp83: Option[Int],
         cp667: Option[Int],
         cp672: Option[Int],
         cp87_Input: Option[Int],
         cp668: Option[Int],
         cp670: Option[Int],
         cp88: Option[Int],
         cp89: Option[Int],
         cp186: Option[Int],
         cp91: Option[Int],
         cp671: Option[Int],
         cp92: Option[Int],
         cp669: Option[Int]) => {

          val retriever = new TestComputationsRetriever(
            lec01 = lec01,
            cpq8 = cpq8,
            cp78 = cp78,
            cp81Input = cp81_Input,
            cp82 = cp82,
            cp83 = cp83,
            cp87Input = cp87_Input,
            cp88 = cp88,
            cp89 = cp89,
            cp666 = cp666,
            cp667 = cp667,
            cp668 = cp668,
            cp672 = cp672
          ) with AccountsBoxRetriever

          assert(retriever.retrieveCP81().value equals cp81_Input.getOrElse(fail("Missing value for CP81 Input")), scenario)
          assert(retriever.retrieveCP87().value equals cp87_Input.getOrElse(fail("Missing value for CP87 Input")), scenario)

          assert(retriever.retrieveCP91().value equals cp91, clue("CP91", retriever.retrieveCP91().value, cp91))
          assert(retriever.retrieveCP92().value equals cp92, clue("CP92", retriever.retrieveCP92().value, cp92))
          assert(retriever.retrieveCP186().value equals cp186, clue("CP186", retriever.retrieveCP186().value, cp186))
          assert(retriever.retrieveCP669().value equals cp669, clue("CP669", retriever.retrieveCP669().value, cp669))
          assert(retriever.retrieveCP670().value equals cp670, clue("CP670", retriever.retrieveCP670().value, cp670))
          assert(retriever.retrieveCP671().value equals cp671, clue("CP671", retriever.retrieveCP671().value, cp671))
        }
      }
    }

    val companiesNoLongerTrading =
      Table(
        ("Scenario",
          "LEC01",
          "CPQ8", "CP78", "CP666", "CP674", "CP81_Input", "CP82", "CP83", "CP84", "CP673",
          "CP90", "CP186", "CP91", "CP671", "CP92", "CP669"),

        ("Scenario 6 - Company not trading, higher disposals than allowances: balancing charges.",
          List(fyaRatePoolCar(20), mainRatePoolCar(30), specialRatePoolCar(40)),
          Some(true), Some(20), Some(30), Some(30),Some(0), None, None, Some(1000), Some(300),
          Some(0), Some(0), Some(1130), Some(1130), Some(0), Some(0)),

        ("Scenario 7 - Company not trading, lower disposals than allowances: balance allowances.",
          List(fyaRatePoolCar(20), mainRatePoolCar(30), specialRatePoolCar(40)),
          Some(true), Some(500), Some(600), Some(1000),Some(0), None, None, Some(10), Some(5),
          Some(2175), Some(2175), Some(0), Some(0), Some(0), Some(0))
      )

    "calculate values for Companies No Longer Trading" in {
      forAll(companiesNoLongerTrading) {
        (scenario: String,
         lec01: List[Car],
         cpq8: Option[Boolean],
         cp78: Option[Int],
         cp666: Option[Int],
         cp674: Option[Int],
         cp81_Input: Option[Int],
         cp82: Option[Int],
         cp83: Option[Int],
         cp84: Option[Int],
         cp673: Option[Int],
         cp90: Option[Int],
         cp186: Option[Int],
         cp91: Option[Int],
         cp671: Option[Int],
         cp92: Option[Int],
         cp669: Option[Int]) => {

          val retriever = new TestComputationsRetriever(
            lec01 = lec01,
            cpq8 = cpq8,
            cp78 = cp78,
            cp81Input = cp81_Input,
            cp82 = cp82,
            cp83 = cp83,
            cp84 = cp84,
            cp666 = cp666,
            cp673 = cp673,
            cp674 = cp674
          ) with AccountsBoxRetriever

          assert(retriever.retrieveCP81().value equals cp81_Input.getOrElse(fail("Missing value for CP81 Input")), scenario)

          assert(retriever.retrieveCP90().value equals cp90, clue("CP90", retriever.retrieveCP90().value, cp90))
          assert(retriever.retrieveCP91().value equals cp91, clue("CP91", retriever.retrieveCP91().value, cp91))
          assert(retriever.retrieveCP92().value equals cp92, clue("CP92", retriever.retrieveCP92().value, cp92))
          assert(retriever.retrieveCP186().value equals cp186, clue("CP186", retriever.retrieveCP186().value, cp186))
          assert(retriever.retrieveCP669().value equals cp669, clue("CP669", retriever.retrieveCP669().value, cp669))
          assert(retriever.retrieveCP671().value equals cp671, clue("CP671", retriever.retrieveCP671().value, cp671))

        }
      }

    }

  }

  def testing(scenario: String, lec01: List[Car], cpq8: Option[Boolean], cp78: Option[Int], cp666: Option[Int], cp81_Input: Option[Int], cp82: Option[Int], cp83: Option[Int], cp667: Option[Int], cp672: Option[Int], cp87_Input: Option[Int], cp668: Option[Int], cp670: Option[Int], cp88: Option[Int], cp89: Option[Int], cp186: Option[Int], cp91: Option[Int], cp671: Option[Int], cp92: Option[Int], cp669: Option[Int]): Unit = {

  }

  private def clue(boxId: String, calcValue: Option[Int], expectedValue: Option[Int]) = s"Calculated value $boxId of $calcValue was not equal to expected $expectedValue"

  private def fyaRatePoolCar(value: Int) = Car(regNumber = "ABC123Z", isNew = true, price = value, emissions = 110, dateOfPurchase = new LocalDate("2013-03-31"))

  private def mainRatePoolCar(value: Int) = Car(regNumber = "XYZ123A", isNew = true, price = value, emissions = 160, dateOfPurchase = new LocalDate("2009-04-01"))

  private def specialRatePoolCar(value: Int) = Car(regNumber = "XYZ789C", isNew = true, price = value, emissions = 161, dateOfPurchase = new LocalDate("2013-03-31"))

  class TestComputationsRetriever(lec01: List[Car],
                                  cpq8: Option[Boolean],
                                  cp78: Option[Int] = None,
                                  cp81Input: Option[Int] = None,
                                  cp82: Option[Int] = None,
                                  cp83: Option[Int] = None,
                                  cp84: Option[Int] = None,
                                  cp87Input: Option[Int] = None,
                                  cp88: Option[Int] = None,
                                  cp89: Option[Int] = None,
                                  cp666: Option[Int] = None,
                                  cp667: Option[Int] = None,
                                  cp668: Option[Int] = None,
                                  cp672: Option[Int] = None,
                                  cp673: Option[Int] = None,
                                  cp674: Option[Int] = None) extends ComputationsBoxRetriever {

    self: AccountsBoxRetriever =>

    def retrieveLEC01: LEC01 = LEC01(lec01)

    def retrieveCPQ8: CPQ8 = CPQ8(cpq8)

    def retrieveCP78: CP78 = CP78(cp78)

    def retrieveCP666: CP666 = CP666(cp666)

    def retrieveCP81Input: CP81Input = CP81Input(cp81Input)

    def retrieveCP82: CP82 = CP82(cp82)

    def retrieveCP83: CP83 = CP83(cp83)

    def retrieveCP84: CP84 = CP84(cp84)

    def retrieveCP667: CP667 = CP667(cp667)

    def retrieveCP672: CP672 = CP672(cp672)

    def retrieveCP673: CP673 = CP673(cp673)

    def retrieveCP674: CP674 = CP674(cp674)

    def retrieveCP87Input: CP87Input = CP87Input(cp87Input)

    def retrieveCP88: CP88 = CP88(cp88)

    def retrieveCP89: CP89 = CP89(cp89)

    def retrieveCP668: CP668 = CP668(cp668)

    def retrieveAC1(): AC1 = ???

    def retrieveAC3(): AC3 = ???

    def retrieveAC4(): AC4 = ???

    def retrieveAC5(): AC5 = ???

    def retrieveAC12(): AC12 = ???

    def retrieveAC205(): AC205 = ???

    def retrieveAC206(): AC206 = ???

    def retrieveCP36(): CP36 = ???

    def retrieveCP303(): CP303 = ???

    def retrieveCP501(): CP501 = ???

    def retrieveCATO11(): CATO11 = ???

    def retrieveCP287(): CP287 = ???

    def retrieveCP21(): CP21 = ???

    def retrieveCP15(): CP15 = ???

    def retrieveCPQ20(): CPQ20 = ???

    def retrieveCP24(): CP24 = ???

    def retrieveCP18(): CP18 = ???

    def retrieveCP281(): CP281 = ???

    def retrieveCP86(): CP86 = ???

    def retrieveAP2(): AP2 = ???

    def retrieveCPQ17(): CPQ17 = ???

    def retrieveCP80(): CP80 = ???

    def retrieveCP53(): CP53 = ???

    def retrieveCP302(): CP302 = ???

    def retrieveCP35(): CP35 = ???

    def retrieveCPQ1000(): CPQ1000 = ???

    def retrieveCP503(): CP503 = ???

    def retrieveCP23(): CP23 = ???

    def retrieveCP91Input(): CP91Input = ???

    def retrieveCP17(): CP17 = ???

    def retrieveCPQ19(): CPQ19 = ???

    def retrieveCP47(): CP47 = ???

    def retrieveCP26(): CP26 = ???

    def retrieveCP32(): CP32 = ???

    def retrieveCP20(): CP20 = ???

    def retrieveCP286(): CP286 = ???

    def retrieveCP29(): CP29 = ???

    def retrieveCP8(): CP8 = ???

    def retrieveCP85(): CP85 = ???

    def retrieveAP1(): AP1 = ???

    def retrieveCP79(): CP79 = ???

    def retrieveCP46(): CP46 = ???

    def retrieveCP2(): CP2 = ???

    def retrieveCPQ10(): CPQ10 = ???

    def retrieveCP52(): CP52 = ???

    def retrieveCP34(): CP34 = ???

    def retrieveCP49(): CP49 = ???

    def retrieveCP55(): CP55 = ???

    def retrieveCPQ7(): CPQ7 = ???

    def retrieveCP301(): CP301 = ???

    def retrieveCP28(): CP28 = ???

    def retrieveCP22(): CP22 = ???

    def retrieveCP502(): CP502 = ???

    def retrieveCPQ21(): CPQ21 = ???

    def retrieveCP16(): CP16 = ???

    def retrieveCP43(): CP43 = ???

    def retrieveCP37(): CP37 = ???

    def retrieveCP31(): CP31 = ???

    def retrieveCPQ18(): CPQ18 = ???

    def retrieveCP19(): CP19 = ???

    def retrieveCP1(): CP1 = ???

    def retrieveCP25(): CP25 = ???

    def retrieveCP285(): CP285 = ???

    def retrieveCATO12(): CATO12 = ???

    def retrieveCP7(): CP7 = ???

    def retrieveCP57(): CP57 = ???

    def retrieveCP30(): CP30 = ???

    def retrieveAP3(): AP3 = ???

    def retrieveCP51(): CP51 = ???

    def retrieveCP510(): CP510 = ???

    def retrieveCP33(): CP33 = ???

    def retrieveCP48(): CP48 = ???

    def retrieveCP27(): CP27 = ???

    def generateValues: Map[String, CtValue[_]] = ???

  }
}
