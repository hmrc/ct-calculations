/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.structuresAndBuildingsAllowance

import org.mockito.Mockito._
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.{Building, CP1, CP2, SBA01, SBAHelper}
import uk.gov.hmrc.ct.utils.UnitSpec

class StructuresAndBuildingsAllowanceSpec extends UnitSpec with SBAHelper {

  private val arbitraryPrice = 100
  private val arbitraryClaim = 1
  private val someText = "bingBong12"
  private val overHundredCharacters = someText * 15
  private val dateUnderLowerBound = dateLowerBound.minusDays(1)
  private val dateInclusiveErrorMsg = Some(List("28 October 2018", "28 October 2019"))

  private val happyFullBuilding = Building(Some(someText), Some("BN3 8BB"), Some(dateLowerBound),
    Some(dateLowerBound), Some(arbitraryPrice), Some(arbitraryClaim))

  private val mockBoxRetriever = mock[ComputationsBoxRetriever]

  "A building" should {
    "validate with an error" when {
      "any of the fields in a building are empty" in {
        when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)
        when(mockBoxRetriever.cp1()) thenReturn CP1(dateLowerBound)

        val b1 = happyFullBuilding.copy(firstLineOfAddress = None)
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(b1))
        b1.validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01A")

        val b2 = happyFullBuilding.copy(postcode = None)
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(b2))
        b2.validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01B")

        val b3 = happyFullBuilding.copy(earliestWrittenContract = None)
          when(mockBoxRetriever.sba01()) thenReturn SBA01(List(b3))
        b3.validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01C")

        val b4 = happyFullBuilding.copy(nonResidentialActivityStart = None)
          when(mockBoxRetriever.sba01()) thenReturn SBA01(List(b4))
        b4.validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01D") ++ Set(CtValidation(Some("SBA01F.building0"), "error.SBA01F.greaterThanMax" ,None))

        val b5 = happyFullBuilding.copy(cost = None)
          when(mockBoxRetriever.sba01()) thenReturn SBA01(List(b5))
        b5.validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01E") ++ Set(CtValidation(Some("SBA01F.building0"), "error.SBA01F.greaterThanMax" ,None))

        val b6 = happyFullBuilding.copy(claim = None)
          when(mockBoxRetriever.sba01()) thenReturn SBA01(List(b6))
        b6.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("SBA01F.building0"), "error.SBA01F.required", None))
      }
    }
  }

      "building name" should {
        "validate with an error" when {
          "characters exceeds 100 limit" in {
            happyFullBuilding.copy(firstLineOfAddress = Some(overHundredCharacters)).validate(mockBoxRetriever) shouldBe
              Set(CtValidation(Some(firstLineOfAddressId), s"error.$firstLineOfAddressId.max.length", Some(Seq(commaForThousands(100)))))
          }
        }
          "validate with a success" when {
            "building name is less than 100 character limit" in {
              happyFullBuilding.copy(firstLineOfAddress = Some(someText)).validate(mockBoxRetriever) shouldBe validationSuccess
            }
          }
      }

      "building postcode" should {
        "validate with an error" when {
          "the postcode is over 8 characters" in {
              happyFullBuilding.copy(postcode = Some(someText)).validate(mockBoxRetriever) shouldBe
                postcodeError(postcodeId)
            }
            "the postcode is under 6 characters" in {
              happyFullBuilding.copy(postcode = Some("ab")).validate(mockBoxRetriever) shouldBe
                postcodeError(postcodeId)
            }
            "the postcode fails just the regex" in {
              happyFullBuilding.copy(postcode = Some("BN3 3!!")).validate(mockBoxRetriever) shouldBe
                postcodeError(postcodeId)
            }
          }
        "validate with a success" when {
          "the postcode is between 6 and 8 characters and satisfies the regex" in {
            happyFullBuilding.validate(mockBoxRetriever) shouldBe validationSuccess
          }
        }
      }

      "earliestWrittenContract" should {
        when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)

        "validate with an error" when {
          "date is before 2018-10-29" in {
            happyFullBuilding.copy(earliestWrittenContract = Some(dateUnderLowerBound)).validate(mockBoxRetriever) shouldBe
              Set(CtValidation(Some(earliestWrittenContractId), s"error.$earliestWrittenContractId.not.betweenInclusive",
                dateInclusiveErrorMsg))
          }

          "date is after the end of accounting period" in {
            happyFullBuilding.copy(earliestWrittenContract = Some(exampleUpperBoundDate.plusDays(1))).validate(mockBoxRetriever) shouldBe
              Set(CtValidation(Some(earliestWrittenContractId), s"error.$earliestWrittenContractId.not.betweenInclusive",
                dateInclusiveErrorMsg))
          }
        }

        "validate with a success" when {
          "date is between 2018-10-29 and the end of the accounting period" in {
            happyFullBuilding.copy(earliestWrittenContract = Some(exampleUpperBoundDate.minusDays(1))).validate(mockBoxRetriever) shouldBe
              validationSuccess
          }
        }
      }
        "nonResidentialActivity" should {
          when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)

          "validate with an error" when {
            "date is before 2018-10-29" in {
              happyFullBuilding.copy(nonResidentialActivityStart = Some(dateUnderLowerBound)).validate(mockBoxRetriever) shouldBe
                Set(CtValidation(Some(nonResActivityId), s"error.$nonResActivityId.not.betweenInclusive",
                  dateInclusiveErrorMsg))
            }

            "date is after the end of accounting period" in {
              val building = happyFullBuilding.copy(nonResidentialActivityStart = Some(exampleUpperBoundDate.plusDays(1)))
              when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))

              building.validate(mockBoxRetriever) shouldBe
                Set(CtValidation(Some(nonResActivityId), s"error.$nonResActivityId.not.betweenInclusive",
                  dateInclusiveErrorMsg)) ++ Set(CtValidation(Some("SBA01F.building0"), "error.SBA01F.greaterThanMax", None))
            }
          }
          "validate with a success" when {
            "date is between 2018-10-29 and the end of the accounting period" in {
              happyFullBuilding.copy(nonResidentialActivityStart = Some(dateLowerBound.plusDays(1))).validate(mockBoxRetriever) shouldBe
                validationSuccess
            }
          }
        }

        "claim" should {
          "validate with an error" when {
            "claim less than one" in {
              when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)
              when(mockBoxRetriever.cp1()) thenReturn CP1(dateLowerBound)

              val building = happyFullBuilding.copy(claim = Some(0))
              when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))

              building.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("SBA01F.building0"), "error.SBA01F.lessThanOne", None))
            }

            "claim more than apportioned 2%" in {
              when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)
              when(mockBoxRetriever.cp1()) thenReturn CP1(dateLowerBound)

              val building = happyFullBuilding.copy(claim = Some(arbitraryPrice))
              when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))

              building.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("SBA01F.building0"), "error.SBA01F.greaterThanMax", None))
            }
          }
        }
}
