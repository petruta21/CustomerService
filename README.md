# CustomerService
This is a learning project which deals with connecting to RDBMs storage and provide REST operations. 


```docker build -t customer-service:0.0.1 .```

```docker run -p 8080:8080 -e DB_URL=jdbc:postgresql://localhost:5432/postgres -e DB_USERNAME=postgres -e  DB_PASSWORD=db-password -t customer-service:0.0.1```

```docker built -f Dockerfile.DB -t customer-service-db:0.0.1 .```


### Service assembly docker-compose.yml:
```docker-compose build```

### Running service locally:
```docker-compose up```

### Print the list of containers
```docker-compose ps```

### Display a list of images
```docker-compose images```

