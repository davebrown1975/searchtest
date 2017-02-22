package com.tucanoo.searchtest

class Contact {

  Date dateCreated
  Date lastUpdated

  String website
  String sortName
  String sortNameLastFirst

  static searchable = {
    root false
    only: ['website']
    //id name:'contact_id',index:'no',store:'yes'
    dateCreated name:'contact_dc' ,index: "not_analyzed", store: "yes"
    lastUpdated name:'contact_lu',index: 'not_analyzed', store:'yes'
    sortName name:'contact_sn',index: 'not_analyzed', store:'yes'
    sortNameLastFirst name:'contact_snlf',index: 'not_analyzed', store:'yes'
  }

  static mapping = {
    version false
    tablePerHierarchy false
    sortName index: 'sortName'
    sortNameLastFirst index: 'sortNameLastFirst'
  }

  static constraints = {
    sortName nullable: true, maxSize: 400
    sortNameLastFirst nullable: true, maxSize: 400
    website nullable: true
  }

  def beforeInsert() {
    this.calculateFields()
  }

  def beforeUpdate() {
    this.calculateFields()
  }

  def private void calculateFields() {
    def temp

    if (this.hasProperty('firmName') && this.firmName)
      temp = [firmName, id]// ?: UUID.randomUUID()]
    else if (this.hasProperty('forename'))
      temp = [forename, middle, surname, suffix, id]// ?: UUID.randomUUID()]
    else if (this.hasProperty('name'))
      temp = [name]
    else
      temp = [this.getShortText()]

    temp.removeAll([null])
    this.sortName = temp.join(' ').trim()


    if (this.hasProperty('firmName') && this.firmName)
      temp = [firmName, id]// ?: UUID.randomUUID()]
    else if (this.hasProperty('forename'))
      temp = [surname, forename, middle, id]// ?: UUID.randomUUID()]
    else if (this.hasProperty('name'))
      temp = [name]
    else
      temp = [this.getShortText()]

    temp.removeAll([null])
    this.sortNameLastFirst = temp.join(' ').trim()

    //this.lastUpdated = new Date()

    //println 'sortnamelastfirst = ' + sortNameLastFirst
  }
}
