/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.approval.accountsApproval.accountsApproval

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.accounts.approval.boxes.AC198A
import uk.gov.hmrc.ct.accounts.{AC4, AccountsDatesValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever

class AC198ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with BeforeAndAfter
  with AccountsDatesValidationFixture[AccountsBoxRetriever] {

  val NOW = DateHelper.now()
  val APEnd = NOW.minusMonths(1)

  before{
    when(boxRetriever.ac4()).thenReturn(AC4(APEnd))
  }

  testDateIsMandatory("AC198A", AC198A)

  testDateBetweenIntervalValidation("AC198A", startDate = APEnd.plusDays(1), endDate = NOW, AC198A)

}
