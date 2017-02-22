package com.tucanoo.searchtest

class Person extends Contact {

  static searchable = {
    only:
    ['forename', 'middle', 'surname']
  }

  static constraints = {
    title maxSize: 15, nullable: true
    forename maxSize: 100, nullable: true
    middle maxSize: 50, nullable: true
    surname maxSize: 100, nullable: true
    suffix maxSize: 15, nullable: true
    sex maxSize: 1, nullable: true, inList: ['M', 'F']
  }

  String title
  String forename
  String middle
  String surname
  String suffix
  String sex

  static mapping = {
    version false
    tablePerHierarchy false
  }


  transient String getShortText() {
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

  transient String getSalutation() {

    StringBuilder sb = new StringBuilder()

    if (title) {
      sb.append(title).append(' ')
    }

    if (surname)
      sb.append(surname)

    return sb.toString().trim()
  }

  def getFullName() {
    def names = [title, forename, middle, surname]
    names.removeAll([null])
    StringBuilder processedName = new StringBuilder(names.join(' '))
    if (suffix) {
      if (!suffix.startsWith(','))
        processedName.append(' ')

      processedName.append(suffix)
    }
    return processedName.toString()
  }

  def getFullNameReversed() {
    def names = [surname ? surname + ',' : null, suffix, forename, middle, title]
    names.removeAll([null])
    names.join(' ')
  }
}

