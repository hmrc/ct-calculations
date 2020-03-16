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
  private val testBroughtForward = 90
  private val testCarriedForward = 89
  private val someText = "bingBong12"
  private val overHundredCharacters = someText * 15
  private val dateUnderLowerBound = dateLowerBound.minusDays(1)
  private val dateInclusiveErrorMsg = Some(List("29 October 2018", "28 October 2019"))

  private val happyFullBuilding = Building(Some(someText), Some(someText), Some("BN3 8BB"), Some(dateLowerBound),
    Some(dateLowerBound), Some(true), Some(arbitraryPrice), Some(arbitraryClaim), Some(testBroughtForward), Some(testCarriedForward))
  private val greaterThanMaxClaimError = Set(CtValidation(Some(s"building0.$claimId"), s"error.$claimId.greaterThanMax" ,None))

  private val mockBoxRetriever = mock[ComputationsBoxRetriever]

  "A building" should {
    "validate with an error" when {
        when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)
        when(mockBoxRetriever.cp1()) thenReturn CP1(dateLowerBound)

      "description is missing" in {
        val building = happyFullBuilding.copy(description = None)
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))
        building.validate(mockBoxRetriever) shouldBe fieldRequiredError(descriptionId)
      }

      "firstLineOfAddress is missing" in {
        val building = happyFullBuilding.copy(firstLineOfAddress = None)
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))
        building.validate(mockBoxRetriever) shouldBe fieldRequiredError(firstLineOfAddressId)
      }

      "earliestWrittenContract is missing" in {
        val building = happyFullBuilding.copy(earliestWrittenContract = None)
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))
        building.validate(mockBoxRetriever) shouldBe fieldRequiredError(earliestWrittenContractId)
      }

      "nonResidentialActivityStart is missing" in {
        val building = happyFullBuilding.copy(nonResidentialActivityStart = None)
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))
        building.validate(mockBoxRetriever) shouldBe fieldRequiredError(nonResActivityId) ++ greaterThanMaxClaimError
      }

      "cost is missing" in {
        val building = happyFullBuilding.copy(cost = None)
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))
        building.validate(mockBoxRetriever) shouldBe fieldRequiredError(costId) ++ Set(CtValidation(Some(s"building0.$claimId"), s"error.$claimId.greaterThanMax" ,None),
          CtValidation(Some(s"building0.$broughtForwardId"), s"error.$broughtForwardId.greaterThanMax" ,None))
      }

      "claim is missing" in {
        val building = happyFullBuilding.copy(claim = None)
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))
        building.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some(s"building0.$claimId"), s"error.$claimId.required", None))
      }

      "broughtForward is missing" in {
        val building = happyFullBuilding.copy(broughtForward = None)
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))
        building.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some(s"building0.$broughtForwardId"), s"error.$broughtForwardId.required", None))
      }

      "carriedForward is missing" in {
        val building = happyFullBuilding.copy(carriedForward = None)
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))
        building.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some(s"building0.$carriedForwardId"), s"error.$carriedForwardId.required", None))
      }
    }
  }

  "building first line of address" should {
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
    when(mockBoxRetriever.cp1()) thenReturn CP1(dateLowerBound)

    "validate with an error" when {
      "date is before 2018-10-29" in {
        happyFullBuilding.copy(earliestWrittenContract = Some(dateUnderLowerBound)).validate(mockBoxRetriever) shouldBe
          Set(CtValidation(Some(earliestWrittenContractId), s"error.$earliestWrittenContractId.not.betweenInclusive",
            dateInclusiveErrorMsg))
      }

      "date is after the end of accounting period" in {
        happyFullBuilding.copy(earliestWrittenContract = Some(exampleUpperBoundDate.plusDays(1))).validate(mockBoxRetriever) shouldBe
          Set(CtValidation(Some(earliestWrittenContractId), s"error.$earliestWrittenContractId.not.betweenInclusive",
            dateInclusiveErrorMsg), CtValidation(Some("SBA01E"), "error.SBA01E.not.betweenInclusive" ,Some(List("29 October 2019", "28 October 2019"))))
      }

      "nonResidentialActivityStart is before earliestWrittenContract" in {
        happyFullBuilding.copy(earliestWrittenContract = Some(dateLowerBound.plusDays(2)), nonResidentialActivityStart = Some(dateLowerBound.plusDays(1))).validate(mockBoxRetriever) shouldBe
          Set(CtValidation(Some("SBA01E"), "error.SBA01E.not.betweenInclusive" ,Some(List("31 October 2018", "28 October 2019"))))
      }
    }

    "validate with a success" when {
      "date is between 2018-10-29 and the end of the accounting period" in {
        happyFullBuilding.copy(earliestWrittenContract = Some(dateLowerBound.plusDays(1)),
          nonResidentialActivityStart = Some(dateLowerBound.plusDays(1))).validate(mockBoxRetriever) shouldBe
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
            dateInclusiveErrorMsg)) ++ greaterThanMaxClaimError
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

        building.validate(mockBoxRetriever) shouldBe Set(CtValidation(
          Some(s"building0.$claimId"), s"error.$claimId.lessThanOne", None),
          CtValidation(Some(s"building0.$carriedForwardId"), s"error.$carriedForwardId.greaterThanMax", None))
      }

      "claim more than apportioned 2%" in {
        when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)
        when(mockBoxRetriever.cp1()) thenReturn CP1(dateLowerBound)

        val building = happyFullBuilding.copy(claim = Some(arbitraryPrice))
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))

        building.validate(mockBoxRetriever) shouldBe greaterThanMaxClaimError ++ Set(CtValidation(Some(s"building0.$carriedForwardId"), s"error.$carriedForwardId.greaterThanMax", None))
      }
    }
  }

  "broughtForward" should {
    "validate with an error" when {
      "claim less than zero" in {
        when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)
        when(mockBoxRetriever.cp1()) thenReturn CP1(dateLowerBound)

        val building = happyFullBuilding.copy(broughtForward = Some(-1))
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))

        building.validate(mockBoxRetriever) shouldBe Set(CtValidation(
          Some(s"building0.$broughtForwardId"), s"error.$broughtForwardId.lessThanZero", None),
          CtValidation(Some(s"building0.$carriedForwardId"), s"error.$carriedForwardId.greaterThanMax" ,None))
      }

      "claim more than totalCost" in {
        when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)
        when(mockBoxRetriever.cp1()) thenReturn CP1(dateLowerBound)

        val building = happyFullBuilding.copy(broughtForward = Some(arbitraryPrice+1))
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))

        building.validate(mockBoxRetriever) shouldBe Set(CtValidation(
          Some(s"building0.$broughtForwardId"), s"error.$broughtForwardId.greaterThanMax", None),
          CtValidation(Some(s"building0.$carriedForwardId"), s"error.$carriedForwardId.greaterThanMax" ,None))
      }
    }
  }

  "carriedForward" should {
    "validate with an error" when {
      "claim less than zero" in {
        when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)
        when(mockBoxRetriever.cp1()) thenReturn CP1(dateLowerBound)

        val building = happyFullBuilding.copy(carriedForward = Some(-1))
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))

        building.validate(mockBoxRetriever) shouldBe Set(CtValidation(
          Some(s"building0.$carriedForwardId"), s"error.$carriedForwardId.lessThanZero", None))
      }

      "claim more than totalCost" in {
        when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)
        when(mockBoxRetriever.cp1()) thenReturn CP1(dateLowerBound)

        val building = happyFullBuilding.copy(carriedForward = Some(arbitraryPrice+1))
        when(mockBoxRetriever.sba01()) thenReturn SBA01(List(building))

        building.validate(mockBoxRetriever) shouldBe Set(CtValidation(
          Some(s"building0.$carriedForwardId"), s"error.$carriedForwardId.greaterThanMax", None))
      }
    }
  }
}
