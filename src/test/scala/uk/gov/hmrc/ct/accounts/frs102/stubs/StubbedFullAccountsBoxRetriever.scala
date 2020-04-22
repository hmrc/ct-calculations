/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.stubs

import uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors.LoansToDirectors
import uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions.RelatedPartyTransactions
import uk.gov.hmrc.ct.accounts.{AC1, AC12, AC2, AC205, AC206, AC3, AC4, AC401, AC402, AC403, AC404, CompanyAddress}
import uk.gov.hmrc.ct.{AbbreviatedAccountsFiling, AbridgedFiling, CompaniesHouseFiling, CompaniesHouseSubmitted, CountryOfRegistration, FilingCompanyType, HMRCAmendment, HMRCFiling, HMRCSubmitted, MicroEntityFiling, StatutoryAccountsFiling, UTR}
import uk.gov.hmrc.ct.accounts.frs102.boxes.{AC106, AC106A, AC107, AC114A, AC114B, AC115A, AC115B, AC116A, AC116B, AC118A, AC118B, AC119A, AC119B, AC120A, AC120B, AC124A, AC124B, AC124C, AC124D, AC124E, AC125A, AC125B, AC125C, AC125D, AC125E, AC126A, AC126B, AC126C, AC126D, AC126E, AC128A, AC128B, AC128C, AC128D, AC128E, AC129A, AC129B, AC129C, AC129D, AC129E, AC13, AC130A, AC130B, AC130C, AC130D, AC130E, AC134, AC135, AC136, AC137, AC138, AC138B, AC139, AC139B, AC14, AC142, AC143, AC144, AC145, AC146, AC147, AC148, AC149, AC15, AC150, AC150B, AC151, AC151B, AC152, AC153, AC156, AC157, AC158, AC159, AC160, AC161, AC18, AC187, AC189, AC19, AC20, AC200, AC200A, AC209A, AC209B, AC21, AC210A, AC210B, AC211A, AC211B, AC212A, AC212B, AC212C, AC212D, AC212E, AC213A, AC213B, AC213C, AC213D, AC213E, AC214A, AC214B, AC214C, AC214D, AC214E, AC219, AC22, AC23, AC28, AC29, AC30, AC31, AC320, AC320A, AC321, AC322, AC323, AC324, AC34, AC35, AC42, AC43, AC44, AC45, AC50, AC5032, AC5052A, AC5052B, AC5052C, AC5058A, AC5064A, AC5076C, AC51, AC5123, AC5133, AC52, AC53, AC54, AC55, AC58, AC59, AC64, AC65, AC66, AC67, AC70, AC71, AC7100, AC7110A, AC7200, AC7210, AC7210A, AC7210B, AC74, AC7400, AC7401, AC75, AC7500, AC76, AC7600, AC7601, AC77, AC7800, AC7900, AC7901, AC8084, AC8085, ACQ5021, ACQ5022, ACQ5031, ACQ5032, ACQ5033, ACQ5034, ACQ5035}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC8081, AC8082, AC8083, AC8088}
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait StubbedFullAccountsBoxRetriever extends FullAccountsBoxRetriever with FilingAttributesBoxValueRetriever{
  override def ac13(): AC13 = ???

  override def ac14(): AC14 = ???

  override def ac15(): AC15 = ???

  override def ac22(): AC22 = ???

  override def ac23(): AC23 = ???

  override def ac114A(): AC114A = ???

  override def ac114B(): AC114B = ???

  override def ac115A(): AC115A = ???

  override def ac115B(): AC115B = ???

  override def ac116A(): AC116A = ???

  override def ac116B(): AC116B = ???

  override def ac118A(): AC118A = ???

  override def ac118B(): AC118B = ???

  override def ac119A(): AC119A = ???

  override def ac119B(): AC119B = ???

  override def ac120A(): AC120A = ???

  override def ac120B(): AC120B = ???

  override def ac124A(): AC124A = ???

  override def ac124B(): AC124B = ???

  override def ac124C(): AC124C = ???

  override def ac124D(): AC124D = ???

  override def ac124E(): AC124E = ???

  override def ac125A(): AC125A = ???

  override def ac125B(): AC125B = ???

  override def ac125C(): AC125C = ???

  override def ac125D(): AC125D = ???

  override def ac125E(): AC125E = ???

  override def ac126A(): AC126A = ???

  override def ac126B(): AC126B = ???

  override def ac126C(): AC126C = ???

  override def ac126D(): AC126D = ???

  override def ac126E(): AC126E = ???

  override def ac128A(): AC128A = ???

  override def ac128B(): AC128B = ???

  override def ac128C(): AC128C = ???

  override def ac128D(): AC128D = ???

  override def ac128E(): AC128E = ???

  override def ac129A(): AC129A = ???

  override def ac129B(): AC129B = ???

  override def ac129C(): AC129C = ???

  override def ac129D(): AC129D = ???

  override def ac129E(): AC129E = ???

  override def ac130A(): AC130A = ???

  override def ac130B(): AC130B = ???

  override def ac130C(): AC130C = ???

  override def ac130D(): AC130D = ???

  override def ac130E(): AC130E = ???

  override def ac134(): AC134 = ???

  override def ac135(): AC135 = ???

  override def ac138(): AC138 = ???

  override def ac139(): AC139 = ???

  override def ac136(): AC136 = ???

  override def ac137(): AC137 = ???

  override def ac142(): AC142 = ???

  override def ac143(): AC143 = ???

  override def ac144(): AC144 = ???

  override def ac145(): AC145 = ???

  override def ac146(): AC146 = ???

  override def ac147(): AC147 = ???

  override def ac148(): AC148 = ???

  override def ac149(): AC149 = ???

  override def ac150(): AC150 = ???

  override def ac151(): AC151 = ???

  override def ac152(): AC152 = ???

  override def ac153(): AC153 = ???

  override def ac156(): AC156 = ???

  override def ac157(): AC157 = ???

  override def ac158(): AC158 = ???

  override def ac159(): AC159 = ???

  override def ac160(): AC160 = ???

  override def ac161(): AC161 = ???

  override def ac200(): AC200 = ???

  override def ac200a(): AC200A = ???

  override def ac209A(): AC209A = ???

  override def ac209B(): AC209B = ???

  override def ac210A(): AC210A = ???

  override def ac210B(): AC210B = ???

  override def ac211A(): AC211A = ???

  override def ac211B(): AC211B = ???

  override def ac212A(): AC212A = ???

  override def ac212B(): AC212B = ???

  override def ac212C(): AC212C = ???

  override def ac212D(): AC212D = ???

  override def ac212E(): AC212E = ???

  override def ac213A(): AC213A = ???

  override def ac213B(): AC213B = ???

  override def ac213C(): AC213C = ???

  override def ac213D(): AC213D = ???

  override def ac213E(): AC213E = ???

  override def ac214A(): AC214A = ???

  override def ac214B(): AC214B = ???

  override def ac214C(): AC214C = ???

  override def ac214D(): AC214D = ???

  override def ac214E(): AC214E = ???

  override def ac7210(): AC7210 = ???

  override def acq5021(): ACQ5021 = ???

  override def acq5022(): ACQ5022 = ???

  override def acq5031(): ACQ5031 = ???

  override def acq5032(): ACQ5032 = ???

  override def acq5033(): ACQ5033 = ???

  override def acq5034(): ACQ5034 = ???

  override def acq5035(): ACQ5035 = ???

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

  override def ac18(): AC18 = ???

  override def ac19(): AC19 = ???

  override def ac20(): AC20 = ???

  override def ac21(): AC21 = ???

  override def ac28(): AC28 = ???

  override def ac29(): AC29 = ???

  override def ac30(): AC30 = ???

  override def ac31(): AC31 = ???

  override def ac34(): AC34 = ???

  override def ac35(): AC35 = ???

  override def ac42(): AC42 = ???

  override def ac43(): AC43 = ???

  override def ac44(): AC44 = ???

  override def ac45(): AC45 = ???

  override def ac50(): AC50 = ???

  override def ac51(): AC51 = ???

  override def ac52(): AC52 = ???

  override def ac53(): AC53 = ???

  override def ac54(): AC54 = ???

  override def ac55(): AC55 = ???

  override def ac58(): AC58 = ???

  override def ac59(): AC59 = ???

  override def ac64(): AC64 = ???

  override def ac65(): AC65 = ???

  override def ac66(): AC66 = ???

  override def ac67(): AC67 = ???

  override def ac70(): AC70 = ???

  override def ac71(): AC71 = ???

  override def ac74(): AC74 = ???

  override def ac75(): AC75 = ???

  override def ac76(): AC76 = ???

  override def ac77(): AC77 = ???

  override def ac106(): AC106 = ???

  override def ac107(): AC107 = ???

  override def ac106A(): AC106A = ???

  override def ac219(): AC219 = ???

  override def ac320(): AC320 = ???

  override def ac320A(): AC320A = ???

  override def ac321(): AC321 = ???

  override def ac322(): AC322 = ???

  override def ac323(): AC323 = ???

  override def ac324(): AC324 = ???

  override def ac7110A(): AC7110A = ???

  override def ac138B(): AC138B = ???

  override def ac139B(): AC139B = ???

  override def ac150B(): AC150B = ???

  override def ac151B(): AC151B = ???

  override def ac5032(): AC5032 = ???

  override def ac5052A(): AC5052A = ???

  override def ac5052B(): AC5052B = ???

  override def ac5052C(): AC5052C = ???

  override def ac5058A(): AC5058A = ???

  override def ac5064A(): AC5064A = ???

  override def ac5133(): AC5133 = ???

  override def ac187(): AC187 = ???

  override def ac189(): AC189 = ???

  override def ac5076C(): AC5076C = ???

  override def ac5123(): AC5123 = ???

  override def ac7100(): AC7100 = ???

  override def ac7200(): AC7200 = ???

  override def ac7210A(): AC7210A = ???

  override def ac7210B(): AC7210B = ???

  override def ac7400(): AC7400 = ???

  override def ac7401(): AC7401 = ???

  override def ac7500(): AC7500 = ???

  override def ac7600(): AC7600 = ???

  override def ac7601(): AC7601 = ???

  override def ac7800(): AC7800 = ???

  override def loansToDirectors(): LoansToDirectors = ???

  override def ac7900(): AC7900 = ???

  override def ac8084(): AC8084 = ???

  override def ac8085(): AC8085 = ???

  override def ac7901(): AC7901 = ???

  override def relatedPartyTransactions(): RelatedPartyTransactions = ???

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

  override def generateValues: Map[String, CtValue[_]] = ???
}
