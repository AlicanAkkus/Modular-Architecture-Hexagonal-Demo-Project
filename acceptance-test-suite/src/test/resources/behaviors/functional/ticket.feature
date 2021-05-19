Feature: ticket features

  Scenario: Customer has not sufficient balance to buy ticket

    Given customer John has a balance with 0 TL
    And a ticket of Java Day Istanbul is 50 TL

    When customer John tries to buy 1 tickets from Java Day Istanbul
    Then customer John cannot buy tickets due to insufficient balance
    And customer John has a balance with 0 TL

  Scenario: Customer has sufficient balance to buy ticket

    Given customer John has a balance with 50 TL
    And a ticket of Java Day Istanbul is 50 TL

    When customer John tries to buy 1 tickets from Java Day Istanbul
    Then customer John can buy tickets
    And customer John has a balance with 0 TL

  Scenario: Customer buys multiple tickets

    Given customer John has a balance with 200 TL
    And a ticket of Java Day Istanbul is 50 TL

    When customer John tries to buy 3 tickets from Java Day Istanbul
    Then customer John can buy tickets
    And customer John has a balance with 50 TL