/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600j.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

case class J20A(value: Option[LocalDate]) extends SchemeDateBox{

  override def validate(boxRetriever: CT600JBoxRetriever): Set[CtValidation] =
    validateSchemeDate(boxRetriever.j15(), boxRetriever.j15A(), boxRetriever.j20())

}
