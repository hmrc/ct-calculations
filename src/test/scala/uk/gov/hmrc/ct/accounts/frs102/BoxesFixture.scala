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

package uk.gov.hmrc.ct.accounts.frs102

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.accounts.frs102.boxes.{AC42, AC43, ACQ5022}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever

trait BoxesFixture extends MockitoSugar {

  implicit val boxRetriever = mock[FullAccountsBoxRetriever]

  def ac42withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac42()).thenReturn(AC42(Some(99)))
  def ac42noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac42()).thenReturn(AC42(None))
  def ac43noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac43()).thenReturn(AC43(None))
  def ac43withValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.ac43()).thenReturn(AC43(Some(99)))
  def acq5022noValue(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5022()).thenReturn(ACQ5022(None))
  def acq5022false(implicit boxRetriever: FullAccountsBoxRetriever) = when(boxRetriever.acq5022()).thenReturn(ACQ5022(Some(false)))
  def acq5022withValue(implicit boxRetriever: FullAccountsBoxRetriever) = acq5022false


}
