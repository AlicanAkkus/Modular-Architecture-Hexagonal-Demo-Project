Feature: Ticket Features

  Scenario: Customer has not sufficient balance to buy ticket

    Given customer John initiated a balance with 0 TL
    And Java Day Istanbul will be on 2021-05-28 with a ticket price 50 TL

    When customer John tries to buy 1 tickets from Java Day Istanbul
    Then customer John cannot buy tickets due to error code 12
    And customer John has a balance with 0 TL

  Scenario: Customer has sufficient balance to buy ticket

    Given customer John initiated a balance with 50 TL
    And Java Day Istanbul will be on 2021-05-28 with a ticket price 50 TL

    When customer John tries to buy 1 tickets from Java Day Istanbul
    Then customer John can buy tickets
    And customer John has a balance with 0 TL

  Scenario: Customer buys multiple tickets

    Given customer John initiated a balance with 200 TL
    And Java Day Istanbul will be on 2021-05-28 with a ticket price 50 TL

    When customer John tries to buy 3 tickets from Java Day Istanbul
    Then customer John can buy tickets
    And customer John has a balance with 50 TL