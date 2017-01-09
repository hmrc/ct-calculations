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

package uk.gov.hmrc.ct.accounts.frs102

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever

trait BoxesFixture extends MockitoSugar {

  implicit val boxRetriever = mock[FullAccountsBoxRetriever]

  def ac42withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac42()).thenReturn(AC42(Some(99)))
  def ac42noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac42()).thenReturn(AC42(None))
  def ac43noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac43()).thenReturn(AC43(None))
  def ac43withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac43()).thenReturn(AC43(Some(99)))
  def acq5021noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5021()).thenReturn(ACQ5021(None))
  def acq5021false(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5021()).thenReturn(ACQ5021(Some(false)))
  def acq5021withValue(implicit boxRetriever: FullAccountsBoxRetriever) = acq5021false
  def acq5022noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5022()).thenReturn(ACQ5022(None))
  def acq5022false(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5022()).thenReturn(ACQ5022(Some(false)))
  def acq5022withValue(implicit boxRetriever: FullAccountsBoxRetriever) = acq5022false
  def ac44withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac44()).thenReturn(AC44(Some(99)))
  def ac44noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac44()).thenReturn(AC44(None))
  def ac45noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac45()).thenReturn(AC45(None))
  def ac45withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac45()).thenReturn(AC45(Some(99)))
  def acq5031noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5031()).thenReturn(ACQ5031(None))
  def acq5031withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5031()).thenReturn(ACQ5031(Some(false)))
  def acq5032noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5032()).thenReturn(ACQ5032(None))
  def acq5032withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5032()).thenReturn(ACQ5032(Some(false)))
  def acq5033noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5033()).thenReturn(ACQ5033(None))
  def acq5033withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5033()).thenReturn(ACQ5033(Some(false)))
  def acq5034noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5034()).thenReturn(ACQ5034(None))
  def acq5034withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5034()).thenReturn(ACQ5034(Some(false)))
  def acq5035noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5035()).thenReturn(ACQ5035(None))
  def acq5035withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5035()).thenReturn(ACQ5035(Some(false)))


}
