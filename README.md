#Contract API

Endpoints

  /contract/  
  Returns all contracts, can be filtered by startDate,contractType,customerType,customerFirstName,customerLastName
  
  /contract/create  
  Creates a new contract based on the provided information which has to be starDate,contractType,customerSsn
  name can be optional as it can be provided in the RequestBody or if it is not present generated.
  
  /contract/customer/ssn  
  Finds all contracts based on the customersSsn
  
  Used SSN as it is a common unique identifier

TODO
Unit testing
