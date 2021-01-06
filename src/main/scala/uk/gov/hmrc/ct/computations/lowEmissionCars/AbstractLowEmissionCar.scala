/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.lowEmissionCars
import org.joda.time.LocalDate

abstract class AbstractLowEmissionCar(val regNumber: Option[String],
                                      val isNew: Option[Boolean],
                                      val price: Option[Int],
                                      val emissions: Option[Int],
                                      val dateOfPurchase: Option[LocalDate]) {

}