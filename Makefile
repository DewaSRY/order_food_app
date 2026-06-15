


compose-up: 
	docker-compose -f docker/docker-compose.yaml up -d 

compose-down:
	docker-compose -f docker/docker-compose.yaml down

compose-clean: 
	docker-compose -f docker/docker-compose.yaml down --rmi all --volumes --remove-orphans

.PHONY: compose-up compose-down compose-clean