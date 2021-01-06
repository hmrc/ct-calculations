/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.version

object HmrcVersions {

  case object CT600Version3 extends Version {
    override def name: String = "v3"
  }

  case object CT600Version2 extends Version {
    override def name: String = "v2"
  }

  case object ComputationsCT20180406 extends Version {
    override def name: String = "ct-2018-04-06"
  }

  case object ComputationsCT20171001 extends Version {
    override def name: String = "ct-2017-10-01"
  }

  case object ComputationsCT20161001 extends Version {
    override def name: String = "ct-2016-10-01"
  }

  case object ComputationsCT20150201 extends Version {
    override def name: String = "ct-2015-02-01"
  }

  case object ComputationsCT20141001 extends Version {
    override def name: String = "ct-2014-10-01"
  }

  case object ComputationsCT20130721 extends Version {
    override def name: String = "ct-2013-07-21"
  }

  case object UploadedAccounts extends Version {
    override def name: String = "uploaded-1.0"
  }
}
