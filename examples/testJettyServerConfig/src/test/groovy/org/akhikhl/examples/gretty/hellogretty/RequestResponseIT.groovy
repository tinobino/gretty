package org.akhikhl.examples.gretty.hellogretty

import geb.spock.GebReportingSpec

class RequestResponseIT extends GebReportingSpec {

  private static String host
  private static int port
  private static int augmentedPort
  private static String contextPath

  void setupSpec() {
    host = System.getProperty('gretty.host')
    port = System.getProperty('gretty.port') as int
    contextPath = System.getProperty('gretty.contextPath')
  }
  
  def 'should get expected static page'() {
  when:
    go "http://${host}:${port}${contextPath}/index.html"
  then:
    $('h1').text() == 'Hello, world!'
    $('p', 0).text() == /This is static HTML page./
    $('p strong').text() == /Please note different server port: it was set in "jetty.xml"./
  }

  def 'should get expected response from servlet'() {
  when:
    go "http://${host}:${port}${contextPath}/dynamic"
  then:
    // port is overridden in jetty-env.xml
    $('h1').text() == 'Hello, world!'
    $('p', 0).text() == /This is dynamic HTML page generated by servlet./
    $('p strong').text() == /Please note different server port: it was set in "jetty.xml"./
  }
}
