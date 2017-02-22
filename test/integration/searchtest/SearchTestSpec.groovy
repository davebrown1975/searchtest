package searchtest

import com.tucanoo.searchtest.Customer
import spock.lang.Shared
import spock.lang.Specification

/**
 *
 */
class SearchTestSpec extends Specification {

  def searchableService

  def setupSpec() {
    new Customer(firmName: 'Daves Firm',forename: 'Frank',surname: 'Black', dateCreated: new Date() - 10).save(failOnError: true)
    new Customer(forename: 'Dave', surname: 'Brown', dateCreated: new Date() - 9).save(failOnError: true)
    new Customer(firmName: 'Daves Firm', forename: 'Dave', surname: 'Smith', dateCreated: new Date() - 8).save(failOnError: true)
    new Customer(firmName: 'Simons Firm', forename: 'Simon', surname: 'Smith', dateCreated: new Date() - 8).save(failOnError: true)
    new Customer(forename: 'John', surname: 'Smith', dateCreated: new Date() - 7).save(failOnError: true,flush:true)
  }

  def cleanupSpec() {
    Customer.findAll()*.delete()
  }

  // Sort by date created(Contact),  this WORKS
  void "test sort by datecreated"() {
    when:
      def searchParams = [offset:0, max:10, sort:'dateCreated',order:'desc']
      def results = Customer.search("Smith",searchParams)
    then:
      assert results != null
      println results
  }

  // Sort by firmName (Customer),  this FAILS
  void "test sort by firm"() {
    when:
      def searchParams = [offset:0, max:10, sort:'firmName',order:'asc']
      def results = Customer.search("Smith",searchParams)
    then:
      assert results != null
      println results
  }

  // Sort by sortName(Contact),  this FAILS
  void "test sort by sortName"() {
    when:
      def searchParams = [offset:0, max:10, sort:'sortName',order:'asc']
      def results = Customer.search("Smith",searchParams)
    then:
      assert results != null
      println results
  }

  // simple test to ensure search index is created
  void "test search index is present"() {
    when:
      def count = Customer.countHits("John")
    then:
      println "COUNT = $count"
      assert 1 == count
  }
}
