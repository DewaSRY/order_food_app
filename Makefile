


compose-up: 
	docker-compose -f docker/docker-compose.yaml up --build 

compose-down:
	docker-compose -f docker/docker-compose.yaml down

.PHONY: compose-up compose-down