/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.retriever

import uk.gov.hmrc.ct.accounts.frs10x.boxes._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs10xDormancyBoxRetriever extends AccountsBoxRetriever {
  self: FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever =>

  def acq8999(): ACQ8999

  def acq8991(): ACQ8991

  def acq8989(): ACQ8989

  def acq8990(): ACQ8990

  def ac8089(): AC8089

  def notTradedStatementRequired(): NotTradedStatementRequired = NotTradedStatementRequired.calculate(this)

  def profitAndLossStatementRequired(): ProfitAndLossStatementRequired = ProfitAndLossStatementRequired.calculate(this)
}
