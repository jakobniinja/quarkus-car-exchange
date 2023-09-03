# car-exchange

This project uses Quarkus, the Supersonic Subatomic Java Framework.

### Daily log
``` 
08/08 Resolved Unsupported MediaType (reactive-jackson)
11/08 Resolved issue where intellij picked java 17 - https://code.quarkus.io/
TODO: Start validating for valid emails

12/08 Validation now impl, find and findOne needs testing ;)
12/08 Reusing the user entity and fixed oom and other miss

13/08 learned about quaryParam and preoccupied routes
14/08 Added plenty of tests and got time to play with update ;)

18/8 patch now working, not to happy with the readabilitty tho
18/8 clean code and some what simpler code + test coverage
19/8 added JsonbTransient, learning custom interceptors with dtos

21/8 had some regex problems, working on Interceptors and it's lifecycle methods
21/8 interceptors and tricky, but i will find a way ;)

26/8 Turns out we don't need to interceptors, JsonbTransient on the getter of the DTO did the trick
26/8 Added hashing with SCrypt for passwords, not hexa tho :/

2/09 impl minimalistic signin and learned to read java docs scrypt
3/09 trying to figure out @Context HttpServletRequest - 400 bad request
``