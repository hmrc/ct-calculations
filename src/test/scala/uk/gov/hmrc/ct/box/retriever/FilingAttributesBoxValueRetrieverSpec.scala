package uk.gov.hmrc.ct.box.retriever

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct._

class FilingAttributesBoxValueRetrieverSpec extends WordSpec with Matchers {

  "FilingAttributesBoxValueRetriever" should {
    "have 7 functions" in {
      FilingAttributesBoxValueRetriever.retrieveBoxIdFunctions(classOf[FilingAttributesBoxValueRetriever]).size shouldBe 7
    }

    "get ct values" in {

      val retriever = new FilingAttributesBoxValueRetrieverForTest
      val result = FilingAttributesBoxValueRetriever.generateValues(retriever)
      result("ProductName") shouldBe retriever.retrieveProductName()

      result("AbbreviatedAccountsFiling") shouldBe retriever.retrieveAbbreviatedAccountsFiling()
      result("StatutoryAccountsFiling") shouldBe retriever.retrieveStatutoryAccountsFiling()
      result("MicroEntityFiling") shouldBe retriever.retrieveMicroEntityFiling()
      result("AbridgedFiling") shouldBe retriever.retrieveAbridgedFiling()
      result("CompaniesHouseFiling") shouldBe retriever.retrieveCompaniesHouseFiling()
      result("HMRCFiling") shouldBe retriever.retrieveHMRCFiling()

    }
  }
}

class FilingAttributesBoxValueRetrieverForTest extends FilingAttributesBoxValueRetriever {

  override def generateValues: Map[String, CtValue[_]] = ???

  override def retrieveProductName(): ProductName = ProductName("productType")

  override def retrieveAbbreviatedAccountsFiling(): AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)

  override def retrieveStatutoryAccountsFiling(): StatutoryAccountsFiling = StatutoryAccountsFiling(true)

  override def retrieveMicroEntityFiling(): MicroEntityFiling = MicroEntityFiling(false)

  override def retrieveAbridgedFiling(): AbridgedFiling = AbridgedFiling(false)

  override def retrieveCompaniesHouseFiling(): CompaniesHouseFiling = CompaniesHouseFiling(true)

  override def retrieveHMRCFiling(): HMRCFiling = HMRCFiling(true)
}
