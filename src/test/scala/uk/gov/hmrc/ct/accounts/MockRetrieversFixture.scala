/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.frs102.retriever._
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xAccountsBoxRetriever, Frs10xDirectorsBoxRetriever, Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


sealed trait TestAccountsRetriever extends AccountsBoxRetriever with FilingAttributesBoxValueRetriever

sealed trait TestFrs10xAccountsRetriever extends Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xDirectorsBoxRetriever with Frs10xFilingQuestionsBoxRetriever

sealed trait TestFrs102AccountsRetriever extends Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xDirectorsBoxRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDormancyBoxRetriever

sealed trait TestFrs105AccountsRetriever extends Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever with Frs10xDormancyBoxRetriever

sealed trait TestAbridgedAccountsRetriever extends AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xDirectorsBoxRetriever with Frs10xFilingQuestionsBoxRetriever

sealed trait TestFullAccountsRetriever extends FullAccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xDirectorsBoxRetriever with Frs10xFilingQuestionsBoxRetriever

trait MockAccountsRetriever extends MockitoSugar {
  val boxRetriever = mock[TestAccountsRetriever]
}

trait MockFrs10xAccountsRetriever extends MockitoSugar {
  val boxRetriever = mock[TestFrs10xAccountsRetriever]
}

trait MockFrs102AccountsRetriever extends MockitoSugar {
  val boxRetriever = mock[TestFrs102AccountsRetriever](RETURNS_SMART_NULLS)
}

trait MockFrs105AccountsRetriever extends MockitoSugar {
  val boxRetriever = mock[TestFrs105AccountsRetriever](RETURNS_SMART_NULLS)
}

trait MockAbridgedAccountsRetriever extends MockitoSugar {
  val boxRetriever = mock[TestAbridgedAccountsRetriever]
}

trait MockFullAccountsRetriever extends MockitoSugar {
  val boxRetriever = mock[TestFullAccountsRetriever]
}
