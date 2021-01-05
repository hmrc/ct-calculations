/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger}

abstract class CP997Abstract (value: Option[Int])
  extends CtBoxIdentifier("Losses from previous AP after 01/04/2017 set against non-trading profits this AP")
    with CtOptionalInteger