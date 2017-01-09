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

package uk.gov.hmrc.ct.ct600.v2.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.ct600.v2.{B86, B91, B92, B93}

trait CorporationTaxAlreadyPaidCalculator extends CtTypeConverters {

  def corporationTaxOutstanding(taxPayable: B86,
                                corporationTaxAlreadyPaid: B91): B92 = {
    B92((taxPayable minus corporationTaxAlreadyPaid.value) max 0)
  }

  def corporationTaxOverpaid(taxPayable: B86,
                             corporationTaxAlreadyPaid: B91): B93 = {
    B93(((taxPayable minus corporationTaxAlreadyPaid.value) min 0).abs )
  }
}

case class CorporationTaxAlreadyPaidParameters(taxPayable: B86,
                                               corporationTaxAlreadyPaid: B91)
