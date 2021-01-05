/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class E15(value: Option[Boolean]) extends CtBoxIdentifier("The company was a charity/CASC and is claiming E15 exemption from all tax on all or part of its income and gains") with CtOptionalBoolean with Input
