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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC5032(value: Option[String]) extends CtBoxIdentifier(name = "Profit/(loss) before tax note")
                                      with CtOptionalString
                                      with Input
                                      with ValidatableBox[Frs10xAccountsBoxRetriever] {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
    (boxRetriever.retrieveAC32(), value) match {
      case (AC32(None), Some(s)) => Set(CtValidation(Some("AC5032"), "error.AC5032.cannot.exist"))
      case (AC32(Some(_)), Some(s)) if s.length > 20000 => Set(CtValidation(Some("AC5032"), "error.AC5032.max.length", Some(Seq("20,000"))))
      case _ => Set.empty
    }
  }
}
