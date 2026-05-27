


compose-up: 
	docker-compose -f docker/docker-compose.yaml up -d 

compose-down:
	docker-compose -f docker/docker-compose.yaml down

.PHONY: compose-up compose-down