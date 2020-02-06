package uk.gov.hmrc.ct.computations.structuresAndBuildingsAllowance

import org.joda.time.LocalDate
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.{Building, CP2, SBAHelper}
import org.mockito.Mockito._
import uk.gov.hmrc.ct.computations

class StructuresAndBuildingsAllowanceSpec extends  WordSpec with MockitoSugar with Matchers with SBAHelper {

  private val today = LocalDate.now
  private val arbitraryPrice = 100
  private val sba01BoxId = "SBA01"
  private val someText = "bingBong12"
  private val hundredCharacters = someText * 10
  private val overHundredCharacters = someText * 15
  private val dateUnderLowerBound = dateLowerBound.minusDays(1)

  private val emptyBuilding = Building(None, None, None, None, None, None)
  private val happyFullBuilding = Building(Some(someText), Some("postcode"), Some(exampleUpperBoundDate),
    Some(exampleUpperBoundDate), Some(arbitraryPrice), Some(arbitraryPrice))

  private val mockBoxRetriever = mock[ComputationsBoxRetriever]

  "A building" should {
    "validate with an error" when {
      "any of the fields in a building are empty" in {
        happyFullBuilding.copy(name = None).validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01A")
        happyFullBuilding.copy(postcode = None).validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01B")
        happyFullBuilding.copy(earliestWrittenContract = None).validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01C")
        happyFullBuilding.copy(nonResidentialActivityStart = None).validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01D")
        happyFullBuilding.copy(cost = None).validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01E")
        happyFullBuilding.copy(claim = None).validate(mockBoxRetriever) shouldBe fieldRequiredError("SBA01F")
      }
    }
  }

      "earliestWrittenContract" should {
        "validate with an error" when {
          "date is before 2018-10-29" in {
            when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)

            happyFullBuilding.copy(earliestWrittenContract = Some(dateUnderLowerBound)).validate(mockBoxRetriever) shouldBe
            Set(CtValidation(Some(earliestWrittenContractId), s"error.$earliestWrittenContractId.not.betweenInclusive",
              Some(List("28 October 2018", "28 October 2019"))))
          }

          "date is after the end of accounting period" in {
            when(mockBoxRetriever.cp2()) thenReturn CP2(exampleUpperBoundDate)

            happyFullBuilding.copy(earliestWrittenContract = Some(exampleUpperBoundDate.plusDays(1))).validate(mockBoxRetriever) shouldBe
            Set(CtValidation(Some(earliestWrittenContractId), s"error.$earliestWrittenContractId.not.betweenInclusive",
              Some(List("28 October 2018", "28 October 2019"))))
          }

        }

        //      "name is over 100 characters long" in {
        //        val illegalName = legalBuildingName.copy(value = Some(overHundredCharacters))
        //        illegalName.validate(mockBoxRetriever) shouldBe sizeRangeError(sba01BoxId, 100)
        //      }
        //
        //      "postcode is over 8 characters long" in {
        //        val invalidPostcode = SBA01B(Some(someText))
        //        postCodeError(invalidPostcode)
        //      }
        //
        //      "postcode invalidates with the regex" in {
        //        val invalidPostcode = SBA01B(Some("ss7 %%%"))
        //        postCodeError(invalidPostcode)
        //      }

        "postcode does not fit the correct format" in {
        }
      }

    private def validateParameter(building: Building, validationResult: Set[CtValidation]) =
      building.validate(mockBoxRetriever) shouldBe validationResult

}

//  private val postCodeError: Option[String] => Unit = {
//    invalidPostcode => invalidPostcode.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some(sba01BoxId),"error.SBA01.invalidPostcode", None))
//  }
//

//  private def sizeRangeError(boxId: String, max: Int): Set[CtValidation] =
//    Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(1.toString, max.toString))))


