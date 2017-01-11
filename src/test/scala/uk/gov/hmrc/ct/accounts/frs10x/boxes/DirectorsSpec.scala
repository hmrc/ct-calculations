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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.{DirectorsMockSetup, MockableFrs10xBoxretrieverWithFilingAttributes}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling, MicroEntityFiling, StatutoryAccountsFiling}

class DirectorsSpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

  val mockBoxRetriever = mock[MockableFrs10xBoxretrieverWithFilingAttributes]

  override def beforeEach = {
    DirectorsMockSetup.setupDefaults(mockBoxRetriever)
  }

  "Directors" should {

    val director = Director("444", "luke")
    val populatedDirectors = Directors(List(director))

    "validate CoHo Only on AC8021 and AC8023" when {
      "cannot exist for statutory accounts answered no to AC8021" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(None))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set(CtValidation(None, "error.Directors.AC8021.cannot.exist"))
      }

      "validate successfully for statutory accounts answered yes to AC8021" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(None))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set()
      }

      "cannot exist for micro entity accounts answered no to AC8021" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(None))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set(CtValidation(None, "error.Directors.AC8021.cannot.exist"))
      }

      "validate successfully for micro entity accounts answered yes to AC8021" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(None))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set()
      }
    }

    "validate Joint on AC8021 and AC8023" when {
      "validate successfully for statutory accounts answered no to AC8021" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(None))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set()
      }

      "validate successfully for statutory accounts answered yes to AC8021" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(None))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set()
      }

      "cannot exist for micro entity accounts answered no to AC8021 amd AC8023" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(Some(false)))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set(CtValidation(None, "error.Directors.AC8023.cannot.exist"))
      }

      "validate successfully for micro entity accounts answered yes to AC8023 and no to AC8021" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(Some(true)))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set()
      }
    }

    "validate based HMRC Only on AC8021 and AC8023" when {
      "validate successfully for statutory accounts no answer for AC8023" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(None))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(None))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set()
      }

      "cannot exist for micro entity accounts answered no to AC8023" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(None))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(Some(false)))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set(CtValidation(None, "error.Directors.AC8023.cannot.exist"))
      }

      "validate successfully for micro entity accounts answered yes to AC8023" in {
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(None))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(Some(true)))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        populatedDirectors.validate(mockBoxRetriever) shouldBe Set()
      }
    }

    "validate successfully when no validation errors are present" in {

      val director = Director("444", "luke")
      val directors = Directors(List(director))

      directors.validate(mockBoxRetriever) shouldBe empty
    }

    "validate director name length" in {

      val director = Director("444", "")
      val directors = Directors(List(director))

      val expectedError = Set(CtValidation(Some("ac8001"), "error.Directors.ac8001.text.sizeRange", Some(List("1", "40"))))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate director name length more than 40 chars" in {

      val director = Director("444", "a" * 41)
      val directors = Directors(List(director))

      val expectedError = Set(CtValidation(Some("ac8001"), "error.Directors.ac8001.text.sizeRange", Some(List("1", "40"))))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate director name characters" in {

      val director = Director("444", "^^")
      val directors = Directors(List(director))

      val expectedError = Set(CtValidation(Some("ac8001"), "error.Directors.ac8001.regexFailure", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate at least one director name present is enabled when AC8023 is true" in {

      val directors = Directors(List())

      val expectedError = Set(CtValidation(Some("ac8001"), "error.Directors.ac8001.global.atLeast1", None))
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
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(companiesHouseFiling))
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(hmrcFiling))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(microEntityFiling))
        when(mockBoxRetriever.ac8021()).thenReturn(AC8021(ac8021))
        when(mockBoxRetriever.ac8023()).thenReturn(AC8023(ac8023))

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

      val expectedError = Set(CtValidation(Some("ac8001"), "error.Directors.ac8001.atMost12", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate duplicate director names" in {
      val directors = Directors(List(Director("444", "Jack"), Director("555", "Jack")))

      val expectedError = Set(CtValidation(Some("ac8001"), "error.Directors.ac8001.unique", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate at least one director appointed if are-there-appointments question is yes" in {
      when(mockBoxRetriever.acQ8003()).thenReturn(ACQ8003(Some(true)))
      val directors = Directors(List(Director("444", "Jack"), Director("555", "Jill")))

      val expectedError = Set(CtValidation(Some("ac8005"), "error.Directors.ac8005.global.atLeast1", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "no error if at least one director appointed if are-there-appointments question is yes" in {
      when(mockBoxRetriever.acQ8003()).thenReturn(ACQ8003(Some(true)))
      val directors = Directors(List(Director("444", "Jack", ac8005 = Some(true), ac8007 = Some(new LocalDate(2016, 4, 5))), Director("555", "Jill")))

      directors.validate(mockBoxRetriever) shouldBe empty
    }

    "do not validate at least one director appointed if are-there-appointments question is no" in {
      val directors = Directors(List(Director("444", "Jack"), Director("555", "Jill")))

      directors.validate(mockBoxRetriever) shouldBe empty
    }

    "validate at least one director resigned if are-there-resignations question is yes" in {
      when(mockBoxRetriever.acQ8009()).thenReturn(ACQ8009(Some(true)))
      val directors = Directors(List(Director("444", "Jack"), Director("555", "Jill")))

      val expectedError = Set(CtValidation(Some("ac8011"), "error.Directors.ac8011.global.atLeast1", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate resignation date" in {
      when(mockBoxRetriever.acQ8009()).thenReturn(ACQ8009(Some(true)))
      val directors = Directors(List(Director("444", "Jack", ac8011 = Some(true)), Director("555", "Jill")))

      directors.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("ac8013.444"), "error.ac8013.required"))
    }

    "no error if at least one director resigned if are-there-resignations question is yes" in {
      when(mockBoxRetriever.acQ8009()).thenReturn(ACQ8009(Some(true)))
      val directors = Directors(List(Director("444", "Jack", ac8011 = Some(true), ac8013 =  Some(new LocalDate(2016, 4, 5))), Director("555", "Jill")))

      directors.validate(mockBoxRetriever) shouldBe empty
    }

    "validate date present if appointed" in {
      when(mockBoxRetriever.acQ8003()).thenReturn(ACQ8003(Some(true)))
      val directors = Directors(List(Director("444", "Jack", ac8005 = Some(true)), Director("555", "Jill")))

      val expectedError = Set(CtValidation(Some("ac8007.444"), "error.ac8007.required", None))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate date within POA if appointed" in {
      when(mockBoxRetriever.acQ8003()).thenReturn(ACQ8003(Some(true)))
      val expectedError = Set(CtValidation(Some("ac8007.444"), "error.ac8007.not.betweenInclusive", Some(List("6 April 2015", "5 April 2016"))))

      val directors = Directors(List(Director("444", "Jack", ac8005 = Some(true), ac8007 = Some(new LocalDate(2015, 4, 5))), Director("555", "Jill")))
      directors.validate(mockBoxRetriever) shouldBe expectedError
    }

    "validate AC8005 and AC8007 cannot exist if ACQ8003" in {
      when(mockBoxRetriever.acQ8003()).thenReturn(ACQ8003(Some(false)))
      when(mockBoxRetriever.acQ8009()).thenReturn(ACQ8009(Some(true)))
      val directors = Directors(List(
        Director("1", "Test Name One", ac8005 = Some(true), ac8007 = Some(new LocalDate(2016, 1, 1)), ac8011 = Some(true), ac8013 = Some(new LocalDate(2016, 2, 1))),
        Director("2", "Test Name Two", ac8005 = None, ac8007 = None, ac8011 = Some(true), ac8013 = Some(new LocalDate(2016, 2, 1)))
      ))

      directors.validate(mockBoxRetriever) shouldBe Set(
        CtValidation(Some("AC8005"), "error.director.1.box.AC8005.cannot.exist"),
        CtValidation(Some("AC8007"), "error.director.1.box.AC8007.cannot.exist")
      )
    }

    "validate AC8011 and AC8013 cannot exist if ACQ8009" in {
      when(mockBoxRetriever.acQ8003()).thenReturn(ACQ8003(Some(true)))
      when(mockBoxRetriever.acQ8009()).thenReturn(ACQ8009(Some(false)))
      val directors = Directors(List(
        Director("1", "Test Name One", ac8005 = Some(true), ac8007 = Some(new LocalDate(2016, 1, 1)), ac8011 = Some(true), ac8013 = Some(new LocalDate(2016, 2, 1))),
        Director("2", "Test Name Two", ac8005 = Some(true), ac8007 = Some(new LocalDate(2016, 1, 1)), ac8011 = None, ac8013 = None)
      ))

      directors.validate(mockBoxRetriever) shouldBe Set(
        CtValidation(Some("AC8011"), "error.director.1.box.AC8011.cannot.exist"),
        CtValidation(Some("AC8013"), "error.director.1.box.AC8013.cannot.exist")
      )
    }
  }
}
