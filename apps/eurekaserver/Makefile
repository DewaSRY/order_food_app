APP_NAME=eureka-server
PORT=8761

build:
	mvn clean package -DskipTests

clean:
	mvn clean

run:
	mvn spring-boot:run

docker-build:
	docker build -t $(APP_NAME) .

docker-run:
	docker run -d \
		--name $(APP_NAME) \
		-p $(PORT):$(PORT) \
		$(APP_NAME)

logs:
	docker logs -f $(APP_NAME)

help:
	@echo "Available commands:"
	@echo " make build         - Build Maven project"
	@echo " make clean         - Clean Maven target"
	@echo " make run           - Run Spring Boot app locally"
	@echo " make docker-build  - Build Docker image"
	@echo " make docker-run    - Run Docker container"
	@echo " make logs          - Show container logs"



.PHONY: help build clean run docker-build docker-run logs