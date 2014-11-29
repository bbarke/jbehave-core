
Scenario: Restarting scenario

When I restart scenario

Scenario: Restarting story

When I restart story

Scenario: Restarting in After steps

When I restart once in after scenario

Scenario: Restart in After steps when running examples table

When I restart once in after scenario using example <Test>
And pending to be sure restarts still happen with a pending step

Examples:
|Test|
|1|
|2|
|3|



