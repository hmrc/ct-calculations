package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}


//  NOTE - - NOT SURE THIS IS NEEDED FOR V3 !!!!!  MIGHT BE ABLE TO DELETE THIS --  TBD!!!!

case class LP04(value: Option[Int]) extends CtBoxIdentifier(name = "Amount of other loans.") with CtOptionalInteger with Input
