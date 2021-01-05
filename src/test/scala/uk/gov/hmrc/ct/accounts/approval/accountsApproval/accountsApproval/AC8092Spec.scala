/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.approval.accountsApproval.accountsApproval

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.approval.boxes.AC8092
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockAccountsRetriever, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC8092Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter
  with MockAccountsRetriever with AccountsFreeTextValidationFixture[AccountsBoxRetriever] {

  testTextFieldValidation("AC8092", AC8092, testUpperLimit = Some(StandardCohoNameFieldLimit))
  testTextFieldIllegalCharactersValidation("AC8092", AC8092)

}
