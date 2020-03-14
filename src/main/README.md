## Development guidelines

### Contract-first approach

The good practise is to design the WSDL contract first and generate artifacts from it.

### Generate stubs from WSDL and XSD

The Apache CXF plugin is configured to generate stubs from the WSDL and XSD files stored in the src/resources/soap directory. The Apache CXF plugin is configured in the pom.xml.

The plugin is configured with the --keep argument so it will not override any generated source code in the case the WSDL or XSD file change. A warning will be raised during the Maven generate-sources phase.

Generated source code will be put inside the src/main/java/soap directory. The Apache CXF plugin is configured to generate :

- DTO that represent objects described in XSD and WSDL
- Interfaces that represents the WSL port binding / interfaces and their operations
- A server-side implementation of the webservices interfaces

Here, it is the SayHelloInterfaceImpl.java that contains server-side logic to handle the SOAP webservice requests.

### Develop webservice logic

You should add the @SchemaValidation above the class definition to enable XML validation of the incoming requests. You should also add the @Service to register the webservice implementation inside Spring IoC.

The Apache is configured to generate the webservice implementation with three methods by operation. One handle the request synchronously, meaning that the receiving thread processes the request and its responses. You can add the @UseAsyncMethod above this method so the framework will rather use the async. methods to process incoming requests.

The two other methods can be used to process the incoming request asynchronously, meaning that the receiving thread can delegate request processing to worker thread and send back the response when it will be available. In the mean time, the receiving thread can process other incoming requests.

This is all you need to know. The rest is pure Spring development.

### Configure the webservice endpoint

An endpoint that use the webservice logic must be registered in the Spring IoC. You can see how simply it is done in the src\main\java\guillaumebraibant\springsoapwshellow\configuration\soap\EndpointConfiguration.java source file.
