/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP997c, _}
import org.scalatest.prop.Tables.Table

class LossesCarriedForwardsCalculatorSpec extends WordSpec with Matchers {

  val table = Table(
    ("CP281", "CP118", "CP283", "CP998", "CP997", "CP287", "result"),
    (      0,       0,       0,       0,       0,       0,        0),
    (    100,       0,      80,       0,       0,       0,       20),
    (    100,       0,      80,       0,      10,       0,       10),
    (      0,     100,       0,       0,       0,      80,       20),
    (      0,     100,       0,      30,       0,       0,       70),
    (      0,     100,       0,      30,       0,      10,       60),
    (     50,     100,       0,     100,      20,      10,       20)
  )

  "Losses carried forward" in new LossesCarriedForwardsCalculator {
    forAll(table) {
      (cp281: Int,
       cp118: Int,
       cp283: Int,
       cp998: Int,
       cp997: Int,
       cp287: Int,
       result: Int) => {
        lossesCarriedForwardsCalculation(
          CP281(cp281), CP118(cp118), CP283(Some(cp283)),
          CP998(Some(cp998)), CP287(cp287), CP997(cp997),
          CP997d(None), CP997c(None)
        ) shouldBe CP288(Some(result))
      }
    }
  }
}
