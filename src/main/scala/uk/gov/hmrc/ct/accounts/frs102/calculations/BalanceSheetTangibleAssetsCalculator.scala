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
import uk.gov.hmrc.ct.box.CtTypeConverters

trait BalanceSheetTangibleAssetsCalculator extends CtTypeConverters {

  def calculateTangibleAssetsAtTheEndOFThePeriod(ac124: AC124, ac125: AC125, ac126: AC126, ac212: AC212, ac213: AC213): AC217 = {
    (ac124.value, ac125.value, ac126.value, ac212.value, ac213.value) match {
      case (None, None, None, None, None) => AC217(None)
      case (_) => AC217(Some(ac124.orZero + ac125.orZero - ac126.orZero + ac212.orZero + ac213.orZero))
    }
  }

  def calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(ac128: AC128, ac219: AC219, ac130: AC130, ac214: AC214) = {
    (ac128.value, ac219.value, ac130.value, ac214.value) match {
      case (None, None, None, None) => AC131(None)
      case (_) => AC131 (Some(ac128.orZero + ac219.orZero - ac130.orZero + ac214.orZero))
    }
  }

  def calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(ac124: AC124, ac128: AC128) = {
    (ac124.value, ac128.value) match {
      case (None, None) =>  AC133(None)
      case _ => AC133(Some(ac124.orZero - ac128.orZero))
    }
  }

  def calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(ac217: AC217, ac131: AC131) = {
    (ac217.value, ac131.value) match {
      case (None, None) => AC132(None)
      case _ => AC132(Some(ac217.orZero - ac131.orZero))
    }
  }

  def calculateAC124(ac124A: AC124A, ac124B: AC124B, ac124C: AC124C, ac124D: AC124D, ac124E: AC124E): AC124 = {
    (ac124A.value, ac124B.value, ac124C.value, ac124D.value, ac124E.value) match {
      case (None, None, None, None, None) => AC124(None)
      case _ => AC124(Some(ac124A.orZero + ac124B.orZero + ac124C.orZero + ac124D.orZero + ac124E.orZero))
    }
  }

  def calculateAC125(ac125A: AC125A, ac125B: AC125B, ac125C: AC125C, ac125D: AC125D, ac125E: AC125E): AC125 = {
    (ac125A.value, ac125B.value, ac125C.value, ac125D.value, ac125E.value) match {
      case (None, None, None, None, None) => AC125(None)
      case _ => AC125(Some(ac125A.orZero + ac125B.orZero + ac125C.orZero + ac125D.orZero + ac125E.orZero))
    }
  }
  
  def calculateAC126(ac126A: AC126A, ac126B: AC126B, ac126C: AC126C, ac126D: AC126D, ac126E: AC126E): AC126 = {
    (ac126A.value, ac126B.value, ac126C.value, ac126D.value, ac126E.value) match {
      case (None, None, None, None, None) => AC126(None)
      case _ => AC126(Some(ac126A.orZero + ac126B.orZero + ac126C.orZero + ac126D.orZero + ac126E.orZero))
    }
  }

  def calculateAC127(ac127A: AC127A, ac127B: AC127B, ac127C: AC127C, ac127D: AC127D, ac127E: AC127E): AC127 = {
    (ac127A.value, ac127B.value, ac127C.value, ac127D.value, ac127E.value) match {
      case (None, None, None, None, None) => AC127(None)
      case _ => AC127(Some(ac127A.orZero + ac127B.orZero + ac127C.orZero + ac127D.orZero + ac127E.orZero))
    }
  }

  def calculateAC127A(ac124A: AC124A, ac125A: AC125A, ac126A: AC126A, ac212A: AC212A, ac213A: AC213A): AC127A = {
    (ac124A.value, ac125A.value, ac126A.value, ac212A.value, ac213A.value) match {
      case (None, None, None, None, None) => AC127A(None)
      case _ => AC127A(Some(ac124A.orZero + ac125A.orZero - ac126A.orZero + ac212A.orZero + ac213A.orZero))
    }
  }

  def calculateAC127B(ac124B: AC124B, ac125B: AC125B, ac126B: AC126B, ac212B: AC212B, ac213B: AC213B): AC127B = {
    (ac124B.value, ac125B.value, ac126B.value, ac212B.value, ac213B.value) match {
      case (None, None, None, None, None) => AC127B(None)
      case _ => AC127B(Some(ac124B.orZero + ac125B.orZero - ac126B.orZero + ac212B.orZero + ac213B.orZero))
    }
  }

  def calculateAC127C(ac124C: AC124C, ac125C: AC125C, ac126C: AC126C, ac212C: AC212C, ac213C: AC213C): AC127C = {
    (ac124C.value, ac125C.value, ac126C.value, ac212C.value, ac213C.value) match {
      case (None, None, None, None, None) => AC127C(None)
      case _ => AC127C(Some(ac124C.orZero + ac125C.orZero - ac126C.orZero + ac212C.orZero + ac213C.orZero))
    }
  }

  def calculateAC127D(ac124D: AC124D, ac125D: AC125D, ac126D: AC126D, ac212D: AC212D, ac213D: AC213D): AC127D = {
    (ac124D.value, ac125D.value, ac126D.value, ac212D.value, ac213D.value) match {
      case (None, None, None, None, None) => AC127D(None)
      case _ => AC127D(Some(ac124D.orZero + ac125D.orZero - ac126D.orZero + ac212D.orZero + ac213D.orZero))
    }
  }

  def calculateAC127E(ac124E: AC124E, ac125E: AC125E, ac126E: AC126E, ac212E: AC212E, ac213E: AC213E): AC127E = {
    (ac124E.value, ac125E.value, ac126E.value, ac212E.value, ac213E.value) match {
      case (None, None, None, None, None) => AC127E(None)
      case _ => AC127E(Some(ac124E.orZero + ac125E.orZero - ac126E.orZero + ac212E.orZero + ac213E.orZero))
    }
  }

  def calculateAC128(ac128A: AC128A, ac128B: AC128B, ac128C: AC128C, ac128D: AC128D, ac128E: AC128E): AC128 = {
    (ac128A.value, ac128B.value, ac128C.value, ac128D.value, ac128E.value) match {
      case (None, None, None, None, None) => AC128(None)
      case _ => AC128(Some(ac128A.orZero + ac128B.orZero + ac128C.orZero + ac128D.orZero + ac128E.orZero))
    }
  }

  def calculateAC129(ac129A: AC129A, ac129B: AC129B, ac129C: AC129C, ac129D: AC129D, ac129E: AC129E): AC129 = {
    (ac129A.value, ac129B.value, ac129C.value, ac129D.value, ac129E.value) match {
      case (None, None, None, None, None) => AC129(None)
      case _ => AC129(Some(ac129A.orZero + ac129B.orZero + ac129C.orZero + ac129D.orZero + ac129E.orZero))
    }
  }

  def calculateAC130(ac130A: AC130A, ac130B: AC130B, ac130C: AC130C, ac130D: AC130D, ac130E: AC130E): AC130 = {
    (ac130A.value, ac130B.value, ac130C.value, ac130D.value, ac130E.value) match {
      case (None, None, None, None, None) => AC130(None)
      case _ => AC130(Some(ac130A.orZero + ac130B.orZero + ac130C.orZero + ac130D.orZero + ac130E.orZero))
    }
  }

  def calculateAC131(ac131A: AC131A, ac131B: AC131B, ac131C: AC131C, ac131D: AC131D, ac131E: AC131E): AC131 = {
    (ac131A.value, ac131B.value, ac131C.value, ac131D.value, ac131E.value) match {
      case (None, None, None, None, None) => AC131(None)
      case _ => AC131(Some(ac131A.orZero + ac131B.orZero + ac131C.orZero + ac131D.orZero + ac131E.orZero))
    }
  }

  def calculateAC131A(ac128A: AC128A, ac129A: AC129A, ac130A: AC130A, ac214A: AC214A): AC131A = {
    (ac128A.value, ac129A.value, ac130A.value, ac214A.value) match {
      case (None, None, None, None) => AC131A(None)
      case _ => AC131A(Some(ac128A.orZero + ac129A.orZero - ac130A.orZero + ac214A.orZero))
    }
  }

  def calculateAC131B(ac128B: AC128B, ac129B: AC129B, ac130B: AC130B, ac214B: AC214B): AC131B = {
    (ac128B.value, ac129B.value, ac130B.value, ac214B.value) match {
      case (None, None, None, None) => AC131B(None)
      case _ => AC131B(Some(ac128B.orZero + ac129B.orZero - ac130B.orZero + ac214B.orZero))
    }
  }

  def calculateAC131C(ac128C: AC128C, ac129C: AC129C, ac130C: AC130C, ac214C: AC214C): AC131C = {
    (ac128C.value, ac129C.value, ac130C.value, ac214C.value) match {
      case (None, None, None, None) => AC131C(None)
      case _ => AC131C(Some(ac128C.orZero + ac129C.orZero - ac130C.orZero + ac214C.orZero))
    }
  }

  def calculateAC131D(ac128D: AC128D, ac129D: AC129D, ac130D: AC130D, ac214D: AC214D): AC131D = {
    (ac128D.value, ac129D.value, ac130D.value, ac214D.value) match {
      case (None, None, None, None) => AC131D(None)
      case _ => AC131D(Some(ac128D.orZero + ac129D.orZero - ac130D.orZero + ac214D.orZero))
    }
  }

  def calculateAC131E(ac128E: AC128E, ac129E: AC129E, ac130E: AC130E, ac214E: AC214E): AC131E = {
    (ac128E.value, ac129E.value, ac130E.value, ac214E.value) match {
      case (None, None, None, None) => AC131E(None)
      case _ => AC131E(Some(ac128E.orZero + ac129E.orZero - ac130E.orZero + ac214E.orZero))
    }
  }

  def calculateAC132(ac132A: AC132A, ac132B: AC132B, ac132C: AC132C, ac132D: AC132D, ac132E: AC132E): AC132 = {
    (ac132A.value, ac132B.value, ac132C.value, ac132D.value, ac132E.value) match {
      case (None, None, None, None, None) => AC132(None)
      case _ => AC132(Some(ac132A.orZero + ac132B.orZero + ac132C.orZero + ac132D.orZero + ac132E.orZero))
    }
  }

  def calculateAC132A(ac127A: AC127A, ac131A: AC131A): AC132A = {
    (ac127A.value, ac131A.value) match {
      case (None, None) => AC132A(None)
      case _ => AC132A(Some(ac127A.orZero - ac131A.orZero))
    }
  }

  def calculateAC132B(ac127B: AC127B, ac131B: AC131B): AC132B = {
    (ac127B.value, ac131B.value) match {
      case (None, None) => AC132B(None)
      case _ => AC132B(Some(ac127B.orZero - ac131B.orZero))
    }
  }

  def calculateAC132C(ac127C: AC127C, ac131C: AC131C): AC132C = {
    (ac127C.value, ac131C.value) match {
      case (None, None) => AC132C(None)
      case _ => AC132C(Some(ac127C.orZero - ac131C.orZero))
    }
  }

  def calculateAC132D(ac127D: AC127D, ac131D: AC131D): AC132D = {
    (ac127D.value, ac131D.value) match {
      case (None, None) => AC132D(None)
      case _ => AC132D(Some(ac127D.orZero - ac131D.orZero))
    }
  }

  def calculateAC132E(ac127E: AC127E, ac131E: AC131E): AC132E = {
    (ac127E.value, ac131E.value) match {
      case (None, None) => AC132E(None)
      case _ => AC132E(Some(ac127E.orZero - ac131E.orZero))
    }
  }

  def calculateAC133(ac133A: AC133A, ac133B: AC133B, ac133C: AC133C, ac133D: AC133D, ac133E: AC133E): AC133 = {
    (ac133A.value, ac133B.value, ac133C.value, ac133D.value, ac133E.value) match {
      case (None, None, None, None, None) => AC133(None)
      case _ => AC133(Some(ac133A.orZero + ac133B.orZero + ac133C.orZero + ac133D.orZero + ac133E.orZero))
    }
  }

  def calculateAC133A(ac124A: AC124A, ac128A: AC128A): AC133A = {
    (ac124A.value, ac128A.value) match {
      case (None, None) => AC133A(None)
      case _ => AC133A(Some(ac124A.orZero - ac128A.orZero))
    }
  }

  def calculateAC133B(ac124B: AC124B, ac128B: AC128B): AC133B = {
    (ac124B.value, ac128B.value) match {
      case (None, None) => AC133B(None)
      case _ => AC133B(Some(ac124B.orZero - ac128B.orZero))
    }
  }

  def calculateAC133C(ac124C: AC124C, ac128C: AC128C): AC133C = {
    (ac124C.value, ac128C.value) match {
      case (None, None) => AC133C(None)
      case _ => AC133C(Some(ac124C.orZero - ac128C.orZero))
    }
  }

  def calculateAC133D(ac124D: AC124D, ac128D: AC128D): AC133D = {
    (ac124D.value, ac128D.value) match {
      case (None, None) => AC133D(None)
      case _ => AC133D(Some(ac124D.orZero - ac128D.orZero))
    }
  }

  def calculateAC133E(ac124E: AC124E, ac128E: AC128E): AC133E = {
    (ac124E.value, ac128E.value) match {
      case (None, None) => AC133E(None)
      case _ => AC133E(Some(ac124E.orZero - ac128E.orZero))
    }
  }

  def calculateAC212(ac212A: AC212A, ac212B: AC212B, ac212C: AC212C, ac212D: AC212D, ac212E: AC212E): AC212 = {
    (ac212A.value, ac212B.value, ac212C.value, ac212D.value, ac212E.value) match {
      case (None, None, None, None, None) => AC212(None)
      case _ => AC212(Some(ac212A.orZero + ac212B.orZero + ac212C.orZero + ac212D.orZero + ac212E.orZero))
    }
  }

  def calculateAC213(ac213A: AC213A, ac213B: AC213B, ac213C: AC213C, ac213D: AC213D, ac213E: AC213E): AC213 = {
    (ac213A.value, ac213B.value, ac213C.value, ac213D.value, ac213E.value) match {
      case (None, None, None, None, None) => AC213(None)
      case _ => AC213(Some(ac213A.orZero + ac213B.orZero + ac213C.orZero + ac213D.orZero + ac213E.orZero))
    }
  }

  def calculateAC214(ac214A: AC214A, ac214B: AC214B, ac214C: AC214C, ac214D: AC214D, ac214E: AC214E): AC214 = {
    (ac214A.value, ac214B.value, ac214C.value, ac214D.value, ac214E.value) match {
      case (None, None, None, None, None) => AC214(None)
      case _ => AC214(Some(ac214A.orZero + ac214B.orZero + ac214C.orZero + ac214D.orZero + ac214E.orZero))
    }
  }
}
