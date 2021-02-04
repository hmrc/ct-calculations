/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsAny

import uk.gov.hmrc.ct.accounts.frsAny.boxes.AC14
import uk.gov.hmrc.ct.box.formats.OptionalIntegerFormat

package object formats {
  implicit val ac14Format: OptionalIntegerFormat[AC14] = new OptionalIntegerFormat[AC14](AC14.apply)

}
