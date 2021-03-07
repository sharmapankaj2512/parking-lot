#### Acceptance Tests
    - tests are concerned with does the feature work
    - always start a new feature with a failing acceptance test  
    - should have enough requirements to force a manageble increase in functionality

#### Integration Tests
    - concerned with does are code work with external code that we cannot change
    
#### Unit Tests
    - concerned wtih do our objects do the right thing, are they convinient to work with

#### Mocking
- Parking lot can be mocked in command processor tests
    - might lead to excessive mocking of behaviour
    - might be brittle as changes in behaviour will also break the mocks