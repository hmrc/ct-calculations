package uk.gov.hmrc.ct.computations.structuresAndBuildingsAllowance
package uk.gov.hmrc.ct.computations.capitalAllowances.structuresAndBuildingsAllowance

import org.joda.time.LocalDate
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}

class StructuresAndBuildingsAllowanceSpec extends  WordSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

//  private val mockBoxRetriever = mock[MockableFrs10xBoxretrieverWithFilingAttributes]
  private val today = LocalDate.now
  private val arbitraryPrice = 100
  private val sba01BoxId = "SBA01"
  private val someText = "bingBong12"
  private val hundredCharacters = someText * 10
  private val overHundredCharacters = someText * 15
//  private val legalBuildingName = SBA01A(Some("bingBong"))

//  override def beforeEach = BuildingsMockSetup.setupDefaults(mockBoxRetriever)

  "A building" should {
    val emptyBuilding = Building(SBA01A(None), None, None, None, None, None)
    val fullBuilding = Building(legalBuildingName, Some("postcode"), Some(today), Some(today),
      Some(arbitraryPrice), Some(arbitraryPrice))

    "validate with an error" when {
      "any of the fields are empty" in {
        SBA01(List(emptyBuilding)).validate(mockBoxRetriever) shouldBe fieldRequiredError(sba01BoxId)
      }

      "name is over 100 characters long" in {
        val illegalName = legalBuildingName.copy(value = Some(overHundredCharacters))
        illegalName.validate(mockBoxRetriever) shouldBe sizeRangeError(sba01BoxId, 100)
      }

      "postcode is over 8 characters long" in {
        val invalidPostcode = SBA01B(Some(someText))
        postCodeError(invalidPostcode)
      }

      "postcode invalidates with the regex" in {
        val invalidPostcode = SBA01B(Some("ss7 %%%"))
        postCodeError(invalidPostcode)
      }

      "postcode does not fit the correct format" in {
      }

    }
  }

  private val postCodeError: SBA01B => Unit = {
    invalidPostcode => invalidPostcode.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some(sba01BoxId),"error.SBA01.invalidPostcode", None))
  }

  private val fieldRequiredError: String => Set[CtValidation] =
    boxId => Set(CtValidation(Some(boxId), s"error.$boxId.required", None))

  private def sizeRangeError(boxId: String, max: Int): Set[CtValidation] =
    Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(1.toString, max.toString))))

}
