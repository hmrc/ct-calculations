/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.{MicroEntityFiling, HMRCFiling, CompaniesHouseFiling}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class DirectorsSpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

  val mockBoxRetriever = mock[MockableFrs10xBoxretrieverWithFilingAttributes]

  override def beforeEach = {
    DirectorsMockSetup.setupDefaults(mockBoxRetriever)
  }

  "Directors" should {

    "validate successfully when no validation errors are present" in {

      val director = Director("444", "luke")
      val directors = Directors(List(director))

      directors.validate(mockBoxRetriever) shouldBe empty
    }

    "validate director name length" in {

      val director = Director("444", "")
      val directors = Directors(List(director))

      val expectedError = Set(CtValidation(Some("AC8001"), "error.Directors.AC8001.text.sizeRange", Some(List("1", "40"))))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate director name length more than 40 chars" in {

      val director = Director("444", "a" * 41)
      val directors = Directors(List(director))

      val expectedError = Set(CtValidation(Some("AC8001"), "error.Directors.AC8001.text.sizeRange", Some(List("1", "40"))))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate director name characters" in {

      val director = Director("444", "??")
      val directors = Directors(List(director))

      val expectedError = Set(CtValidation(Some("AC8001"), "error.Directors.AC8001.regexFailure", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate at least one director name present is enabled when AC8023 is true" in {

      val directors = Directors(List())

      val expectedError = Set(CtValidation(Some("AC8001"), "error.Directors.AC8001.global.atLeast1", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate under the correct conditions only" in {

      val directors = Directors(List())

      val testTable = Table(
        ("companiesHouseFiling",   "hmrcFiling",     "microEntityFiling",  "ac8021",         "ac8023",            "expectedResult"),
        (true,                      true,             false,                None,             None,                 true),
        (true,                      true,             true,                 None,             Some(true),           true),
        (true,                      true,             true,                 None,             Some(false),          false),
        (true,                      false,            false,                Some(true),       None,                 true),
        (true,                      false,            false,                Some(false),      None,                 false),
        (true,                      false,            true,                 Some(true),       None,                 true),
        (true,                      false,            true,                 Some(false),      None,                 false),
        (false,                     true,             false,                None,             None,                 true),
        (false,                     true,             true,                 None,             Some(true),           true),
        (false,                     true,             true,                 None,             Some(false),          false)
      )

      forAll(testTable) { (companiesHouseFiling: Boolean, hmrcFiling: Boolean, microEntityFiling: Boolean, ac8021: Option[Boolean], ac8023: Option[Boolean], expectedResult: Boolean) =>
        when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(companiesHouseFiling))
        when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(hmrcFiling))
        when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(microEntityFiling))
        when(mockBoxRetriever.retrieveAC8021()).thenReturn(AC8021(ac8021))
        when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(ac8023))

        directors.validate(mockBoxRetriever).size > 0 shouldBe expectedResult
        directors.validate(mockBoxRetriever).exists(_.errorMessageKey.contains("atLeast1")) shouldBe expectedResult
      }
    }

    "validate at most 12 director names" in {

      val directorsList = for {
        i <- ('A' to 'Z').toList
        d = Director("444", s"director$i")
      } yield d

      val directors = Directors(directorsList)

      val expectedError = Set(CtValidation(Some("AC8001"), "error.Directors.AC8001.atMost12", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate duplicate director names" in {
      val directors = Directors(List(Director("444", "Jack"), Director("555", "Jack")))

      val expectedError = Set(CtValidation(Some("AC8001"), "error.Directors.AC8001.unique", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate at least one director appointed if are-there-appointments question is yes" in {
      when(mockBoxRetriever.retrieveACQ8003()).thenReturn(ACQ8003(Some(true)))
      val directors = Directors(List(Director("444", "Jack"), Director("555", "Jill")))

      val expectedError = Set(CtValidation(Some("AC8005"), "error.Directors.AC8005.global.atLeast1", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "do not validate at least one director appointed if are-there-appointments question is no" in {

      val directors = Directors(List(Director("444", "Jack"), Director("555", "Jill")))

      directors.validate(mockBoxRetriever) shouldBe empty
    }

    "no error if at least one director appointed if are-there-appointments question is yes" in {
      when(mockBoxRetriever.retrieveACQ8003()).thenReturn(ACQ8003(Some(true)))
      val directors = Directors(List(Director("444", "Jack", AC8005 = Some(true)), Director("555", "Jill")))

      directors.validate(mockBoxRetriever) shouldBe empty
    }
  }
}
