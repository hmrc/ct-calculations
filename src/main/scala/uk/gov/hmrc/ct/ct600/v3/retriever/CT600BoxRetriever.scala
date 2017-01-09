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

package uk.gov.hmrc.ct.ct600.v3.retriever

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

trait CT600BoxRetriever extends ComputationsBoxRetriever with CT600DeclarationBoxRetriever with AboutThisReturnBoxRetriever {

  self: AccountsBoxRetriever with FilingAttributesBoxValueRetriever =>

  def b1(): B1

  def b2(): B2 = B2(ac1())

  def b3(): B3 = B3(utr())

  def b4(): B4 = B4(companyType())

  def b145(): B145 = B145(cp7())

  def b150(): B150

  def b155(): B155 = B155(cp256())

  def b160(): B160 = B160(cp257())

  def b165(): B165 = B165(cp258())

  def b170(): B170 = B170(cp259())

  def b190(): B190 = B190(cp511())

  def b205(): B205 = B205(cp502())

  def b235(): B235 = B235(cp265())

  def b275(): B275 = B275(cp264())

  def b280(): B280 = B280.calculate(this)

  def b295(): B295 = B295(cp264())

  def b300(): B300 = B300.calculate(this)

  def b305(): B305 = B305(cp305())

  def b315(): B315 = B315(cp295())

  def b330(): B330 = B330.calculate(this)

  def b335(): B335 = B335.calculate(this)

  def b340() : B340 = B340.calculate(this)

  def b345() : B345 = B345.calculate(this)

  def b380() : B380 = B380.calculate(this)

  def b385() : B385 = B385.calculate(this)

  def b390() : B390 = B390.calculate(this)

  def b395() : B395 = B395.calculate(this)

  def b430() : B430 = B430.calculate(this)

  def b440(): B440 = B440(b430())

  def b475(): B475 = B475(b440())

  def b480(): B480 = {
    this match {
      case r: CT600ABoxRetriever => B480(r.a80())
      case _ => B480(None)
    }

  }

  def b485(): B485 = {
    this match {
      case r: CT600ABoxRetriever => B485.calculate(r)
      case _ => B485(false)
    }
  }

  def b510(): B510 = B510.calculate(this)

  def b515(): B515

  def b520(): B520 = B520.calculate(this)

  def b525(): B525 = B525.calculate(this)

  def b595(): B595

  def b600(): B600 = B600.calculate(this)

  def b605(): B605 = B605.calculate(this)

  def b620(): B620

  def b690(): B690 = B690(cp88())

  def b695(): B695 = B695(cp668())

  def b700(): B700 = B700(cp670())

  def b705(): B705 = B705(cp248())

  def b710(): B710 = B710(cp247())

  def b735(): B735

  def b750(): B750

  def b755(): B755

  def b760(): B760 = B760(cp251())

  def b765(): B765 = B765(cp252())

  def b775(): B775 = B775(cp253())

  def b780(): B780 = B780(cp118())

  def bFQ1(): BFQ1
}
