package com.tucanoo.searchtest


class Customer extends Person {

  static searchable = {
    only:
    ['firmName', 'socialSecurityNumber', 'driversLicenseNo', 'occupation']
    mapping boost: 4.0
    firmName name:'firmname_idx',index: 'not_analyzed', store:'yes'
  }

  Boolean representedByFirm = false
  String firmName
  String socialSecurityNumber
  String driversLicenseNo
  String occupation

  static constraints = {
    firmName nullable: true, maxSize: 200
    representedByFirm nullable: true, validator: { isFirm, Customer customer, errors ->
      if (! isFirm && !customer.fullName) {
        errors.rejectValue('surname', 'client.validation.surname.required')
        errors.rejectValue('forename', 'client.validation.forename.required')
      } else if (isFirm && !customer.firmName) {
        errors.rejectValue('firmName', 'client.validation.firm.required')
      }
    }
    socialSecurityNumber maxSize: 11, nullable: true
    driversLicenseNo maxSize: 15, nullable: true
    occupation maxSize: 50, nullable: true
  }

  @Override
  transient String getShortText() {
    if (representedByFirm)
      return firmName
    else {

      def names = [forename, middle, surname]
      names.removeAll([null])
      StringBuilder processedName = new StringBuilder(names.join(' '))
      if (suffix) {
        if (!suffix.startsWith(','))
          processedName.append(' ')

        processedName.append(suffix)
      }
      return processedName.toString()
    }
  }

  @Override
  def getFullName() {
    if (representedByFirm)
      return firmName
    else
      return super.fullName
  }

  @Override
  def getFullNameReversed() {
    if (representedByFirm)
      return firmName
    else
      return super.fullNameReversed
  }
}
