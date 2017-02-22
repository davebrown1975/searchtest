package searchtest

import com.tucanoo.searchtest.Customer
import spock.lang.Specification

/**
 *
 */
class SearchTestSpec extends Specification {
  def searchableService

  def setup() {
    searchableService.startMirroring()
    new Customer(firmName: 'Daves Firm',forename: 'Frank',surname: 'Black', dateCreated: new Date() - 10).save(failOnError: true)
    new Customer(forename: 'Dave', surname: 'Brown', dateCreated: new Date() - 9).save(failOnError: true)
    new Customer(firmName: 'Daves Firm', forename: 'Dave', surname: 'Smith', dateCreated: new Date() - 8).save(failOnError: true)
    new Customer(firmName: 'Simons Firm', forename: 'Simon', surname: 'Smith', dateCreated: new Date() - 8).save(failOnError: true)
    new Customer(forename: 'John', surname: 'Smith', dateCreated: new Date() - 7).save(failOnError: true,flush:true)

    //  searchableService.indexAll()
  }

  def cleanup() {
    Customer.findAll()*.delete()
  }

  void "test sort by datecreated"() {
    when:
      def searchParams = [offset:0, max:10, sort:'dateCreated',order:'desc']
      def results = Customer.search("Smith",searchParams)
    then:
      assert results != null
      println results
  }

  void "test sort by firm"() {
    when:
      def searchParams = [offset:0, max:10, sort:'firmName',order:'asc']
      def results = Customer.search("Smith",searchParams)
    then:
      assert results != null
      println results
  }

  void "test sort by sortName"() {
    when:
      def searchParams = [offset:0, max:10, sort:'sortName',order:'asc']
      def results = Customer.search("Smith",searchParams)
    then:
      assert results != null
      println results
  }

  void "test something"() {
    when:
      def count = Customer.countHits("John")
    then:
      println "COUNT = $count"
      assert 1 == count
  }
}
