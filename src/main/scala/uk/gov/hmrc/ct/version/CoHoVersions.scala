/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.version

object CoHoVersions {

  case object FRSSE2008 extends Version {
    override def name: String = "FRSSE-2008"
  }

  case object FRS102 extends Version {
    override def name: String = "FRS-102"
  }

  case object FRS105 extends Version {
    override def name: String = "FRS-105"
  }
}
