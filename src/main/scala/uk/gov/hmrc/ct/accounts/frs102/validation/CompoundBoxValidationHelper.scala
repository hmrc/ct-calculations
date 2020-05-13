

package uk.gov.hmrc.ct.accounts.frs102.validation

import uk.gov.hmrc.ct.box.CtValidation


object CompoundBoxValidationHelper {
  /*
  This was labelled as @deprecated("", "29-09-2016 or earlier").
  This was used for a filing period before the date provided.
 */
  def contextualiseErrorKey(containerName: String, errorKey: String, index: Int): String = {
    val splitKey = errorKey.split('.')
    (splitKey.take(1) ++ Array("compoundList", containerName) ++ Array(index.toString) ++ splitKey.drop(1)).mkString(".")
  }

  def contextualiseError(owningBox: String, containerName: String, error: CtValidation, index: Int): CtValidation = {
    val splitKey = error.errorMessageKey.split('.')

    if(error.isGlobalError) {
      val errorMessage = (splitKey.take(2) ++ Array("compoundList", containerName) ++ Array(index.toString) ++ splitKey.drop(2)).mkString(".")
      error.copy(errorMessageKey = errorMessage)
    } else {
      val errorMessage = (splitKey.take(1) ++ Array("compoundList", containerName) ++ Array(index.toString) ++ splitKey.drop(1)).mkString(".")
      error.copy(boxId = Some(owningBox), errorMessageKey = errorMessage)
    }
  }
}
