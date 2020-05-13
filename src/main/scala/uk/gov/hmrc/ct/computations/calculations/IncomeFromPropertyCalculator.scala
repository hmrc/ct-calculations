/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait IncomeFromPropertyCalculator extends CtTypeConverters {

  def netIncomeFromProperty(cp507: CP507, cp508: CP508): CP509 = {
    CP509(cp507 - cp508)
  }

  def totalIncomeFromProperty(cp509: CP509, cp510: CP510): CP511 = {
    CP511(cp509 + cp510)
  }
}
