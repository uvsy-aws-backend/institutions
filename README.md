# institutions
[![serverless](http://public.serverless.com/badges/v3.svg)](http://www.serverless.com)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/27e5cba3e854477a92505676d88677b8)](https://www.codacy.com/gh/uvsy-aws-backend/api-institutions?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=uvsy-aws-backend/api-institutions&amp;utm_campaign=Badge_Grade)
[![Maintainability](https://api.codeclimate.com/v1/badges/2a555754ba7abb9ce5a7/maintainability)](https://codeclimate.com/github/uvsy-aws-backend/api-institutions/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/2a555754ba7abb9ce5a7/test_coverage)](https://codeclimate.com/github/uvsy-aws-backend/api-institutions/test_coverage)

Service that manages all institutions related information. This covers: 

- [x] Institutions
- [x] Careers
- [x] Programs
- [x] Subject
- [x] Correlatives


### Environment Requirements

- JDK 8
- NodeJS and npm
- Docker (optional)

&nbsp;
### SetUp

    make init

&nbsp;
### Create domain

    make domain
    
> This action should be performed once, only when the service is first created, before deploy.
> 
> e.g.
> 
> `make init`
>
> `make domain`
>
> `make deploy`
 
&nbsp;
### Build

    make build

&nbsp;    
### Deploy

    make deploy 