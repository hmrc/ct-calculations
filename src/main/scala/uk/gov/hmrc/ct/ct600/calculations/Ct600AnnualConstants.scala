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

package uk.gov.hmrc.ct.ct600.calculations

case class TaxYear(year: Int)


trait CtConstants {
  
  def lowerRelevantAmount: BigDecimal
  def upperRelevantAmount: BigDecimal
  def reliefFraction: BigDecimal
  def rateOfTax: BigDecimal
  def smallCompaniesRateOfTax: BigDecimal
}


case class AllCtConstants(lowerRelevantAmount: BigDecimal,
                          upperRelevantAmount: BigDecimal,
                          reliefFraction: BigDecimal,
                          rateOfTax: BigDecimal,
                          smallCompaniesRateOfTax: BigDecimal) extends CtConstants


case class UnifiedRateOfTax(private val unifiedTaxRate: String) extends CtConstants {

  val lowerRelevantAmount: BigDecimal = BigDecimal("0")
  val upperRelevantAmount: BigDecimal = BigDecimal("0")
  val reliefFraction: BigDecimal = BigDecimal("0")
  val rateOfTax: BigDecimal = BigDecimal(unifiedTaxRate)
  val smallCompaniesRateOfTax: BigDecimal = BigDecimal(unifiedTaxRate)

}


object Ct600AnnualConstants extends Ct600AnnualConstants {

  val data: Map[TaxYear, CtConstants] = Map(TaxYear(2006) -> AllCtConstants(lowerRelevantAmount = BigDecimal("300000"),
                                              upperRelevantAmount = BigDecimal("1500000"),
                                              reliefFraction = BigDecimal("0.0275"),
                                              rateOfTax = BigDecimal("0.30"),
                                              smallCompaniesRateOfTax = BigDecimal("0.19")),

                  TaxYear(2007) -> AllCtConstants(lowerRelevantAmount = BigDecimal("300000"),
                                              upperRelevantAmount = BigDecimal("1500000"),
                                              reliefFraction = BigDecimal("0.025"),
                                              rateOfTax = BigDecimal("0.30"),
                                              smallCompaniesRateOfTax = BigDecimal("0.20")),

                  TaxYear(2008) -> AllCtConstants(lowerRelevantAmount = BigDecimal("300000"),
                                              upperRelevantAmount = BigDecimal("1500000"),
                                              reliefFraction = BigDecimal("0.0175"),
                                              rateOfTax = BigDecimal("0.28"),
                                              smallCompaniesRateOfTax = BigDecimal("0.21")),

                  TaxYear(2009) -> AllCtConstants(lowerRelevantAmount = BigDecimal("300000"),
                                              upperRelevantAmount = BigDecimal("1500000"),
                                              reliefFraction = BigDecimal("0.0175"),
                                              rateOfTax = BigDecimal("0.28"),
                                              smallCompaniesRateOfTax = BigDecimal("0.21")),

                  TaxYear(2010) -> AllCtConstants(lowerRelevantAmount = BigDecimal("300000"),
                                              upperRelevantAmount = BigDecimal("1500000"),
                                              reliefFraction = BigDecimal("0.0175"),
                                              rateOfTax = BigDecimal("0.28"),
                                              smallCompaniesRateOfTax = BigDecimal("0.21")),

                  TaxYear(2011) -> AllCtConstants(lowerRelevantAmount = BigDecimal("300000"),
                                              upperRelevantAmount = BigDecimal("1500000"),
                                              reliefFraction = BigDecimal("0.015"),
                                              rateOfTax = BigDecimal("0.26"),
                                              smallCompaniesRateOfTax = BigDecimal("0.20")),

                  TaxYear(2012) -> AllCtConstants(lowerRelevantAmount = BigDecimal("300000"),
                                              upperRelevantAmount = BigDecimal("1500000"),
                                              reliefFraction = BigDecimal("0.01"),
                                              rateOfTax = BigDecimal("0.24"),
                                              smallCompaniesRateOfTax = BigDecimal("0.20")),

                  TaxYear(2013) -> AllCtConstants(lowerRelevantAmount = BigDecimal("300000"),
                                              upperRelevantAmount = BigDecimal("1500000"),
                                              reliefFraction = BigDecimal("0.0075"),
                                              rateOfTax = BigDecimal("0.23"),
                                              smallCompaniesRateOfTax = BigDecimal("0.20")),

                  TaxYear(2014) -> AllCtConstants(lowerRelevantAmount = BigDecimal("300000"),
                                              upperRelevantAmount = BigDecimal("1500000"),
                                              reliefFraction = BigDecimal("0.0025"),
                                              rateOfTax = BigDecimal("0.21"),
                                              smallCompaniesRateOfTax = BigDecimal("0.20")),

                  TaxYear(2015) -> UnifiedRateOfTax("0.20"),

                  TaxYear(2016) -> UnifiedRateOfTax("0.20"),

                  TaxYear(2017) -> UnifiedRateOfTax("0.19"),

                  TaxYear(2018) -> UnifiedRateOfTax("0.19"),

                  TaxYear(2019) -> UnifiedRateOfTax("0.19"),

                  TaxYear(2020) -> UnifiedRateOfTax("0.17")


  )

  val minYear = data.keys.reduceLeft((y1: TaxYear, y2: TaxYear) => if (y1.year < y2.year) y1 else y2)
  val maxYear = data.keys.reduceLeft((y1: TaxYear, y2: TaxYear) => if (y1.year > y2.year) y1 else y2)

  def lowProfitsThreshold(numberOfCompanies :Option[Int]) = 300000 / (numberOfCompanies.getOrElse(0) + 1)
}


trait Ct600AnnualConstants {

  val data: Map[TaxYear, CtConstants]
  val minYear: TaxYear
  val maxYear: TaxYear

  def constantsForTaxYear(taxYear: TaxYear): CtConstants = {
    assert(taxYear.year >= minYear.year, s"Cannot operate on years before ${minYear.year}")
    data.get(taxYear) match {
      case Some(x) => x
      case _ => data.get(Ct600AnnualConstants.maxYear).get
    }
  }
}
