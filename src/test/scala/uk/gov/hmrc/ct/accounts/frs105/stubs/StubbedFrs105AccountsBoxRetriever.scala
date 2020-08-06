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

package uk.gov.hmrc.ct.accounts.frs105.stubs

import uk.gov.hmrc.ct.accounts.{AC1, AC12, AC2, AC205, AC206, AC3, AC4, AC401, AC402, AC403, AC404, CompanyAddress}
import uk.gov.hmrc.ct.{AbbreviatedAccountsFiling, AbridgedFiling, CATO24, CompaniesHouseFiling, CompaniesHouseSubmitted, CountryOfRegistration, FilingCompanyType, HMRCAmendment, HMRCFiling, HMRCSubmitted, MicroEntityFiling, StatutoryAccountsFiling, UTR}
import uk.gov.hmrc.ct.accounts.frs105.boxes.{AC13, AC34, AC35, AC405, AC406, AC410, AC411, AC415, AC416, AC420, AC421, AC425, AC426, AC450, AC451, AC455, AC456, AC460, AC461, AC465, AC466, AC470, AC471, AC490, AC491, AC58, AC59, AC64, AC65, AC66, AC67, AC7991, AC7992, AC7995, AC7997, AC7998, AC7999, AC7999a, AC8087}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC8081, AC8082, AC8083, AC8088}
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait StubbedFrs105AccountsBoxRetriever extends Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever {
  override def ac13(): AC13 = ???

  override def ac34(): AC34 = ???

  override def ac35(): AC35 = ???

  override def ac58(): AC58 = ???

  override def ac59(): AC59 = ???

  override def ac64(): AC64 = ???

  override def ac65(): AC65 = ???

  override def ac66(): AC66 = ???

  override def ac67(): AC67 = ???

  override def ac405(): AC405 = ???

  override def ac406(): AC406 = ???

  override def ac410(): AC410 = ???

  override def ac411(): AC411 = ???

  override def ac415(): AC415 = ???

  override def ac416(): AC416 = ???

  override def ac420(): AC420 = ???

  override def ac421(): AC421 = ???

  override def ac425(): AC425 = ???

  override def ac426(): AC426 = ???

  override def ac450(): AC450 = ???

  override def ac451(): AC451 = ???

  override def ac455(): AC455 = ???

  override def ac456(): AC456 = ???

  override def ac460(): AC460 = ???

  override def ac461(): AC461 = ???

  override def ac465(): AC465 = ???

  override def ac466(): AC466 = ???

  override def ac470(): AC470 = ???

  override def ac471(): AC471 = ???

  override def ac490(): AC490 = ???

  override def ac491(): AC491 = ???

  override def ac8087(): AC8087 = ???

  override def ac7991(): AC7991 = ???

  override def ac7992(): AC7992 = ???

  override def ac7995(): AC7995 = ???

  override def ac7997(): AC7997 = ???

  override def ac7998(): AC7998 = ???

  override def ac7999(): AC7999 = ???

  override def ac7999a(): AC7999a = ???

  override def companyType(): FilingCompanyType = ???

  override def abbreviatedAccountsFiling(): AbbreviatedAccountsFiling = ???

  override def abridgedFiling(): AbridgedFiling = ???

  override def companiesHouseFiling(): CompaniesHouseFiling = ???

  override def hmrcFiling(): HMRCFiling = ???

  override def companiesHouseSubmitted(): CompaniesHouseSubmitted = ???

  override def hmrcSubmitted(): HMRCSubmitted = ???

  override def hmrcAmendment(): HMRCAmendment = ???

  override def microEntityFiling(): MicroEntityFiling = ???

  override def statutoryAccountsFiling(): StatutoryAccountsFiling = ???

  override def utr(): UTR = ???

  override def countryOfRegistration(): CountryOfRegistration = ???

  override def ac8081(): AC8081 = ???

  override def ac8082(): AC8082 = ???

  override def ac8083(): AC8083 = ???

  override def ac8088(): AC8088 = ???

  override def companyAddress(): CompanyAddress = ???

  override def ac1(): AC1 = ???

  override def ac2(): AC2 = ???

  override def ac3(): AC3 = ???

  override def ac4(): AC4 = ???

  override def ac12(): AC12 = ???

  override def ac401(): AC401 = ???

  override def ac402(): AC402 = ???

  override def ac403(): AC403 = ???

  override def ac404(): AC404 = ???

  override def ac205(): AC205 = ???

  override def ac206(): AC206 = ???

  override def cato24(): CATO24 = ???

  override def generateValues: Map[String, CtValue[_]] = ???
}
