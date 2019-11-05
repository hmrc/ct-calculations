/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.validation.MandatoryNotesIntegerValidationFixture
import uk.gov.hmrc.ct.accounts.{MockMandatoryNotesRetriever, TestMandatoryNotesAccountsRetriever}
import uk.gov.hmrc.ct.computations.CP2
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class AC7998Spec extends WordSpec with Matchers with MockitoSugar with MandatoryNotesIntegerValidationFixture[ComputationsBoxRetriever] with MockMandatoryNotesRetriever {

  private val boxID = "AC7998"
  private val minNumberOfEmployees = Some(1)
  private val maxNumberOfEmployees = Some(99999)
  private val isMandatory = Some(true)

  "validate appropriately when accounting period is before 01/01/2017 and employee information box is not empty" when {
    val mandatoryNotesStartDate = LocalDate.parse("2017-01-01")

    when(boxRetriever.cp2()) thenReturn CP2(mandatoryNotesStartDate)
    testIntegerFieldValidation(boxID, AC7998, minNumberOfEmployees, maxNumberOfEmployees, isMandatory)
  }

  "pass validation when accounting period is before 01/01/2017 and employee information box is empty" in {
    val mandatoryNotesNotEnabledDate = LocalDate.parse("2016-12-31")
    when(boxRetriever.cp2()) thenReturn CP2(mandatoryNotesNotEnabledDate)

    AC7998(None).validate(boxRetriever) shouldBe Set.empty
}
}


