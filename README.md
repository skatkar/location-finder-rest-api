# location-finder
Spring REST based service to allow the users to get the longitude and latitude of the specified location.
Once the API returns the location details, front end which will be developed in ReactJS, will plot these locations on the map using Google Maps API.

## Database
The service uses Apache Solr as a database which is the search paltform to lookup the location details. Apache Solr is deployed on GCP.

## Facilities provided so far
- Lookup the location details by its type eg. School,College,Parks, etc.
- Lookup the location by its id
- Add the data to the Solr (only admin can do it. Admin update is in progress)

## Technology stack
- ReactJS
- Spring REST
- Apache Solr
- Google CLoud Platform
