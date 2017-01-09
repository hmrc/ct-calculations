/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

trait IntangibleAssetsCalculator {

  def calculateAC114(ac114A: AC114A, ac114B: AC114B): AC114 = {
    if (anyHaveValue(ac114A, ac114B))
      AC114(Some(ac114A.orZero + ac114B.orZero))
    else
      AC114(None)
  }

  def calculateAC115(ac115A: AC115A, ac115B: AC115B): AC115 = {
    if (anyHaveValue(ac115A, ac115B))
      AC115(Some(ac115A.orZero + ac115B.orZero))
    else
      AC115(None)
  }

  def calculateAC116(ac116A: AC116A, ac116B: AC116B): AC116 = {
    if (anyHaveValue(ac116A, ac116B))
      AC116(Some(ac116A.orZero + ac116B.orZero))
    else
      AC116(None)
  }
  
  def calculateAbridgedAC117(ac114: AC114, ac115: AC115, ac116: AC116, ac209: AC209, ac210: AC210): AC117 = {
    anyHaveValue(ac114, ac115, ac116, ac209, ac210) match {
      case true => AC117(Some(ac114.orZero + ac115.orZero - ac116.orZero + ac209.orZero + ac210.orZero))
      case _ => AC117(None)
    }
  }

  def calculateFullAC117(ac117A: AC117A, ac117B: AC117B): AC117 = {
    if (anyHaveValue(ac117A, ac117B))
      AC117(Some(ac117A.orZero + ac117B.orZero))
    else
      AC117(None)
  }


  def calculateAC117A(ac114A: AC114A, ac115A: AC115A, ac116A: AC116A, ac209A: AC209A, ac210A: AC210A): AC117A = {
    if (Set(ac114A.hasValue, ac115A.hasValue, ac116A.hasValue, ac209A.hasValue, ac210A.hasValue).contains(true))
      AC117A(Some(ac114A.orZero + ac115A.orZero - ac116A.orZero + ac209A.orZero + ac210A.orZero))
    else
      AC117A(None)
  }

  def calculateAC117B(ac114B: AC114B, ac115B: AC115B, ac116B: AC116B, ac209B: AC209B, ac210B: AC210B): AC117B = {
    if (Set(ac114B.hasValue, ac115B.hasValue, ac116B.hasValue, ac209B.hasValue, ac210B.hasValue).contains(true))
      AC117B(Some(ac114B.orZero + ac115B.orZero - ac116B.orZero + ac209B.orZero + ac210B.orZero))
    else
      AC117B(None)
  }

  def calculateAC118(ac118A: AC118A, ac118B: AC118B): AC118 = {
    if (anyHaveValue(ac118A, ac118B))
      AC118(Some(ac118A.orZero + ac118B.orZero))
    else
      AC118(None)
  }

  def calculateAC119(ac119A: AC119A, ac119B: AC119B): AC119 = {
    if (anyHaveValue(ac119A, ac119B))
      AC119(Some(ac119A.orZero + ac119B.orZero))
    else
      AC119(None)
  }

  def calculateAC120(ac120A: AC120A, ac120B: AC120B): AC120 = {
    if (anyHaveValue(ac120A, ac120B))
      AC120(Some(ac120A.orZero + ac120B.orZero))
    else
      AC120(None)
  }
  
  def calculateAbridgedAC121(ac118: AC118, ac119: AC119, ac120: AC120, ac211: AC211): AC121 = {
    anyHaveValue(ac118, ac119, ac120, ac211) match {
      case true => AC121(Some(ac118.orZero + ac119.orZero - ac120.orZero + ac211.orZero))
      case _ => AC121(None)
    }
  }

  def calculateFullAC121(ac121A: AC121A, ac121B: AC121B): AC121 = {
    if (anyHaveValue(ac121A, ac121B))
      AC121(Some(ac121A.orZero + ac121B.orZero))
    else
      AC121(None)
  }

  def calculateAC121A(ac118A: AC118A, ac119A: AC119A, ac120A: AC120A, ac211A: AC211A): AC121A = {
    anyHaveValue(ac118A, ac119A, ac120A, ac211A) match {
      case true => AC121A(Some(ac118A.orZero + ac119A.orZero - ac120A.orZero + ac211A.orZero))
      case _ => AC121A(None)
    }
  }

  def calculateAC121B(ac118B: AC118B, ac119B: AC119B, ac120B: AC120B, ac211B: AC211B): AC121B = {
    anyHaveValue(ac118B, ac119B, ac120B, ac211B) match {
      case true => AC121B(Some(ac118B.orZero + ac119B.orZero - ac120B.orZero + ac211B.orZero))
      case _ => AC121B(None)
    }
  }

  def calculateAbridgedAC122(ac117: AC117, ac121: AC121): AC122 = {
    Set(ac117.value, ac121.value).exists(_.nonEmpty) match {
      case true => AC122(Some(ac117.orZero - ac121.orZero))
      case _ => AC122(None)
    }
  }

  def calculateFullAC122(ac122A: AC122A, ac122B: AC122B): AC122 = {
    if (anyHaveValue(ac122A, ac122B))
      AC122(Some(ac122A.orZero + ac122B.orZero))
    else
      AC122(None)
  }

  def calculateAC122A(ac117A: AC117A, ac121A: AC121A): AC122A = {
    Set(ac117A.value, ac121A.value).exists(_.nonEmpty) match {
      case true => AC122A(Some(ac117A.orZero - ac121A.orZero))
      case _ => AC122A(None)
    }
  }

  def calculateAC122B(ac117B: AC117B, ac121B: AC121B): AC122B = {
    Set(ac117B.value, ac121B.value).exists(_.nonEmpty) match {
      case true => AC122B(Some(ac117B.orZero - ac121B.orZero))
      case _ => AC122B(None)
    }
  }

  def calculateAbridgedAC123(ac114: AC114, ac118: AC118): AC123 = {
    Set(ac114.value, ac118.value).exists(_.nonEmpty) match {
      case true => AC123(Some(ac114.orZero - ac118.orZero))
      case _ => AC123(None)
    }
  }

  def calculateFullAC123(ac123A: AC123A, ac123B: AC123B): AC123 = {
    if (anyHaveValue(ac123A, ac123B))
      AC123(Some(ac123A.orZero + ac123B.orZero))
    else
      AC123(None)
  }

  def calculateAC123A(ac114A: AC114A, ac118A: AC118A): AC123A = {
    Set(ac114A.value, ac118A.value).exists(_.nonEmpty) match {
      case true => AC123A(Some(ac114A.orZero - ac118A.orZero))
      case _ => AC123A(None)
    }
  }

  def calculateAC123B(ac114B: AC114B, ac118B: AC118B): AC123B = {
    Set(ac114B.value, ac118B.value).exists(_.nonEmpty) match {
      case true => AC123B(Some(ac114B.orZero - ac118B.orZero))
      case _ => AC123B(None)
    }
  }

  def calculateAC209(ac209A: AC209A, ac209B: AC209B): AC209 = {
    if (anyHaveValue(ac209A, ac209B))
      AC209(Some(ac209A.orZero + ac209B.orZero))
    else
      AC209(None)
  }

  def calculateAC210(ac210A: AC210A, ac210B: AC210B): AC210 = {
    if (anyHaveValue(ac210A, ac210B))
      AC210(Some(ac210A.orZero + ac210B.orZero))
    else
      AC210(None)
  }

  def calculateAC211(ac211A: AC211A, ac211B: AC211B): AC211 = {
    if (anyHaveValue(ac211A, ac211B))
      AC211(Some(ac211A.orZero + ac211B.orZero))
    else
      AC211(None)
  }
}
