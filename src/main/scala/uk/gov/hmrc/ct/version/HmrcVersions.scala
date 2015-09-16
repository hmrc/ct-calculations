package uk.gov.hmrc.ct.version

object HmrcVersions {

  case object CT600Version3 extends Version {
    override def name: String = "v3"
  }

  case object CT600Version2 extends Version {
    override def name: String = "v2"
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

}
