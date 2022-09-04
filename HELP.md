# Getting Started

### Reference Documentation

Service Type :	REST Web Service
Resource	 :  /api/natwest/assignment/getprimenumbers
Description	 :  Get API that taked a number and some optional fields described below as input and returns prime numbers as output
Params       :  number(required) - input element upto which prime numbers would be returned
             :  algorithm(optional) - optional value to switch algorithm
             :  contentType(optional) - header value based on which the output content is created
                for ex : if you've input application/xml as input then you'd get XML in output
                for all other inputs(or in case of emoty header) JSON would be returned in response
Authorization:  Basic Authorization is done, you'd need to input kunal/kunal123 as username/password
Communication
Protocol	 :  HTTP
HTTP Method  :	GET
Type	     :  Synchronous


