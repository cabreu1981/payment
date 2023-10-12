In the directory of payments 
open the command prompt and type docker compose up -d
after the command is finished type docker compose ps to verify the db is created

open an build the project with IntelliJ

now go to http://localhost:5050/browser/
set a master password for the pgadmin, 
recomended for testing admin1234
create a new server and set the name to getontop
the connection to the one in the project, this time is 
host: postgres
port:5432
username: getontop
password: getontop1234

the local url for eureka is 

http://localhost:8761/

1 - run first the eureka server then run the other microservices, with no order then use the collection attached to execute actions
2 - first you need to create a user
3 - then you can create transactions directly using the endpoint or you can make a transfer an automatic transaction will be created


- with more time  what i would do would be implement a message queue to handle all the topics messages for all microservices
- I could configure a private docker registry to pull the images of the microservices put on a container and configure a srver for github to pull
   the configurations.
- I  would also configure a notifications server to deliver the messages produced by kafka / rabbitMQ

- all those microservices can be installed on a Kubernetes container and managed with it so no 
	Eureka will be needed a that point since K8 is better a managing the pods

- in the middle of the authentication i would put fraud server to verify the email and the NationalID to see if it's a fraudster.
	there i can verify too the location of the incoming request and block it based on a geofence.


- for a PRODUCTION project the database should be on a server outside of K8 but for speed up the development process i left it inside the k8


	
