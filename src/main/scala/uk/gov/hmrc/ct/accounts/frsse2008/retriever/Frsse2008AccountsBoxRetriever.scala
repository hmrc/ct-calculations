/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008.retriever

import uk.gov.hmrc.ct.accounts.AC12
import uk.gov.hmrc.ct.accounts.frs105.boxes.AC415
import uk.gov.hmrc.ct.accounts.frs10x.boxes.AC13
import uk.gov.hmrc.ct.accounts.frsse2008._
import uk.gov.hmrc.ct.accounts.frsse2008.micro._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frsse2008AccountsBoxRetriever extends AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def ac12(): AC12

  def ac13(): AC13

  def ac14(): AC14

  def ac15(): AC15

  def ac16(): AC16 = AC16.calculate(this)

  def ac17(): AC17 = AC17.calculate(this)

  def ac18(): AC18

  def ac19(): AC19

  def ac20(): AC20

  def ac21(): AC21

  def ac22(): AC22

  def ac23(): AC23

  def ac24(): AC24

  def ac26(): AC26 = AC26.calculate(this)

  def ac27(): AC27 = AC27.calculate(this)

  def ac28(): AC28

  def ac29(): AC29

  def ac30(): AC30

  def ac31(): AC31

  def ac32(): AC32 = AC32.calculate(this)

  def ac33(): AC33 = AC33.calculate(this)

  def ac34(): AC34

  def ac35(): AC35

  def ac36(): AC36 = AC36.calculate(this)

  def ac37(): AC37 = AC37.calculate(this)

  def ac38(): AC38

  def ac39(): AC39

  def ac40(): AC40 = AC40.calculate(this)

  def ac41(): AC41 = AC41.calculate(this)

  def ac405(): AC405

  def ac406(): AC406

  def ac410(): AC410

  def ac411(): AC411

  def ac415(): AC415

  def ac416(): AC416

  def ac420(): AC420

  def ac421(): AC421

  def ac425(): AC425

  def ac426(): AC426

  def ac435(): AC435 = AC435.calculate(this)

  def ac436(): AC436 = AC436.calculate(this)
}
