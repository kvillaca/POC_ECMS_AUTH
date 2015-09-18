# POC_ECMS_AUTH

To call validation:
http://localhost:8080/rest/auth/login/validate/ (from IDEA with / path)
http://localhost:8080/POC_ECMS_AUTH/rest/auth/login/validate/ (from war file)

Payload raw value:
```json
{"userId": "dummy","password": "12345678","uri": "http://localhost:8080/ecms/rest/login/validate/"}
```

Header JSON returned if success:
```json
{"HEADER_VALUES":[{"TOKEN":"30ZMTtYhJVEYQdSxMY1duLX2BQA+YelLU+0eRPHJqXnl3P89YyT9AP7jIQEwc1A6LnFbdP3dsi7y\r\n9a7WoQmmm+lKpQNjzrSkdZzvigW+Hlc7DbvN3DAo\/Y+KPrWEAtz4gx71\/1yMzUV8uuyT+8OIQQ3D\r\nch+4Ywc1bKiZDrsZIR+r9czhJIVLe1cKSxytzFm9T3EGGMdUuQYcO5mnGcNlh1BYeaJsV3AMbUx7\r\n9BbITNZqExiATqVASMYRI0ws2KN\/b0bI84zG+DDL\/iCOr9ZOIqs4wRqEwLYrvw6jbMzAcJMVqlNM\r\nx2QQ0UI9R+LoW1s0c1cPyFBqpbEVm3L4Hwa2zQ=="},{"HEADER_USER_ROLES":"[supervisor]"},{"LOCAL_USER":"dummy"}]}
```
