# Batch Request API

Simple web controller which allows executing many different requests with optional JSON body in one HTTP request.

**Only for endpoints which produces or consumes `application/json`.**

## How to run?

Just add the following dependency to your Spring Boot WebFlux project.

```xml
<dependency>
    <groupId>io.github.jkrauze</groupId>
    <artifactId>bra</artifactId>
    <version>0.1.0</version>
</dependency>
```

Add `@EnableBra` annotation to your application.

```java
@EnableBra
@EnableWebFlux
@SpringBootApplication
public class MyApplication {
  ...
```

Your API should have the Batch Request API endpoint now at `POST /bra-v0`.

```javascript
POST /bra-v0

[
	{
		"method":"POST",
		"path":"/items",
		"body":{
			"name":"Hello world!"
		}
	},
	{
		"method":"GET",
		"path":"/items/1"
	},
	{
		"method":"PUT",
		"path":"/items/1",
		"body":{
			"name":"Boo!"
		}
	},
	{
		"method":"GET",
		"path":"/items"
	},
	{
		"method":"DELETE",
		"path":"/items/1"
	},
	{
		"method":"GET",
		"path":"/items/1"
	}
]

---

HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

[
    {
        "timestamp": 1540317044782,
        "method": "POST",
        "path": "/items",
        "body": {
            "id": 1,
            "name": "Hello world!"
        },
        "status": 200,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8",
            "Content-Length": "30"
        }
    },
    {
        "timestamp": 1540317044805,
        "method": "GET",
        "path": "/items/1",
        "body": {
            "id": 1,
            "name": "Hello world!"
        },
        "status": 200,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8",
            "Content-Length": "30"
        }
    },
    {
        "timestamp": 1540317044812,
        "method": "PUT",
        "path": "/items/1",
        "body": {
            "id": 1,
            "name": "Boo!"
        },
        "status": 200,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8",
            "Content-Length": "22"
        }
    },
    {
        "timestamp": 1540317044820,
        "method": "GET",
        "path": "/items",
        "body": [
            {
                "id": 1,
                "name": "Boo!"
            }
        ],
        "status": 200,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8"
        }
    },
    {
        "timestamp": 1540317044823,
        "method": "DELETE",
        "path": "/items/1",
        "body": null,
        "status": 200,
        "headers": {}
    },
    {
        "timestamp": 1540317044882,
        "method": "GET",
        "path": "/items/1",
        "body": {
            "timestamp": 1540317044849,
            "path": "/items/1",
            "status": 404,
            "error": "Not Found",
            "message": null
        },
        "status": 404,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8",
            "Content-Length": "93"
        }
    }
]
```

## Benchmarks

Batch Request API benchmarks can be found [here](https://github.com/jkrauze/bra-sample/blob/master/README.md).

## Getting started

Let's suppose you have a [REST API implemented using Spring WebFlux](https://github.com/jkrauze/bra-sample/blob/master/src/main/java/io/github/jkrauze/sample/bra/SampleController.java).

Suppose a consumer wants to create 15 items. He must make 15 separate `POST /items` request.

```javascript
POST /items

{
	"name":"Hello World!"
}

---

HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
    "id": 1,
    "name": "Hello World!"
}
```

Now, he wants to get 10 items with ids from 4 to 13. He can make 10 separate HTTP request to `GET /items/{id}` endpoint or get a collection of all items from `GET /items` which will return more items than requested.

```javascript
GET /items/4

---

HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
    "id": 4,
    "name": "Hello World!"
}
```

```javascript
GET /items

---

HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

[
    {
        "id": 1,
        "name": "Hello World!"
    },
    ...
    {
        "id": 15,
        "name": "Hello World!"
    }
]
```

After that, he wants to change the name of 5 items. The only way is to make 5 requests to `PUT /items/{id}` endpoint.

```javascript
PUT /items/5

{
    "name": "Boo!"
}

---

HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
    "id": 5,
    "name": "Boo!"
}
```

Finally, he wants to delete 3 of them. As before, the only way is to make 3 requests to `DELETE /items/{id}` endpoint.

```javascript
DELETE /items/15

---

HTTP/1.1 200 OK
```

To execute these operations consumer had to make 15 + 10 + 5 + 3 = 33 requests.


Now, he can make all of these operations in one HTTP request.
```javascript
POST /bra-v0

[
	{
		"method":"POST",
		"path":"/items",
		"body":{
			"name":"Hello world!"
		}
	},
	...
	{
		"method":"POST",
		"path":"/items",
		"body":{
			"name":"Hello world!"
		}
	},
	{
		"method":"GET",
		"path":"/items/4"
	},
	...
	{
		"method":"GET",
		"path":"/items/13"
	},
	{
		"method":"PUT",
		"path":"/items/5",
		"body":{
			"name":"Boo!"
		}
	},
	...
	{
		"method":"PUT",
		"path":"/items/9",
		"body":{
			"name":"Boo!"
		}
	},
	{
		"method":"DELETE",
		"path":"/items/13"
	},
	...
	{
		"method":"DELETE",
		"path":"/items/15"
	}
]

---

HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

[
    {
        "timestamp": 1540319668872,
        "method": "POST",
        "path": "/items",
        "body": {
            "id": 1,
            "name": "Hello world!"
        },
        "status": 200,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8",
            "Content-Length": "30"
        }
    },
    ...
    {
        "timestamp": 1540319668924,
        "method": "POST",
        "path": "/items",
        "body": {
            "id": 15,
            "name": "Hello world!"
        },
        "status": 200,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8",
            "Content-Length": "31"
        }
    },
    {
        "timestamp": 1540319668946,
        "method": "GET",
        "path": "/items/4",
        "body": {
            "id": 4,
            "name": "Hello world!"
        },
        "status": 200,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8",
            "Content-Length": "30"
        }
    },
    ...
    {
        "timestamp": 1540319668969,
        "method": "GET",
        "path": "/items/13",
        "body": {
            "id": 13,
            "name": "Hello world!"
        },
        "status": 200,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8",
            "Content-Length": "31"
        }
    },
    {
        "timestamp": 1540319668974,
        "method": "PUT",
        "path": "/items/5",
        "body": {
            "id": 5,
            "name": "Boo!"
        },
        "status": 200,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8",
            "Content-Length": "22"
        }
    },
    ...
    {
        "timestamp": 1540319668987,
        "method": "PUT",
        "path": "/items/9",
        "body": {
            "id": 9,
            "name": "Boo!"
        },
        "status": 200,
        "headers": {
            "Content-Type": "application/json;charset=UTF-8",
            "Content-Length": "22"
        }
    },
    {
        "timestamp": 1540319668989,
        "method": "DELETE",
        "path": "/items/13",
        "body": null,
        "status": 200,
        "headers": {}
    },
    ...
    {
        "timestamp": 1540319668992,
        "method": "DELETE",
        "path": "/items/15",
        "body": null,
        "status": 200,
        "headers": {}
    }
]
```

## License

Licensed under the [MIT License](LICENSE).
