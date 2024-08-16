# API

| 기능       | Method | URL             | request                                                                    | response                                                                                            | 상태 코드 |
|----------|--------|-----------------|----------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|-------|
| 할일 저장    | POST   | /api/todos      | body: { "content": string, "password": string, "userId": number }          | { "id": number, "content": string, "userId": number, "createAt": DateTime, "updateAt": DateTime }   | 200   |
| 할일 조회    | GET    | /api/todos/{id} | no content                                                                 | { "id": number, "content": string, "userId": number, "createAt": DateTime, "updateAt": DateTime }   | 200   |
| 특정 할일 조회 | GET    | /api/todos      | query string: {"updateAt"?: "YYYY-MM-DD", "userId"?: number}               | { "id": number, "content": string, "userId": number, "createAt": DateTime, "updateAt": DateTime }[] | 200   |
| 특정 할일 수정 | PUT    | /api/todos/{id} | header:{ "password": string }, body:{ "content": string "userId": number } | { "id": number, "content": string, "userId": number, "createAt": DateTime, "updateAt": DateTime }   | 200   |
| 특정 할일 삭제 | DELETE | /api/todos/{id} | header:{ "password": string }                                              | no content                                                                                          | 204   |
| 유저 추가    | POST   | /api/users/     | body: { "name": string, "email": string  }                                 | { "id": number, "name": string, "email": string, "createAt":DateTime, "updateAt": DateTime  }       | 200   |
| 유저 조회    | GET    | /api/users/     | no content                                                                 | { "id": number, "name": string, "email": string, "createAt":DateTime, "updateAt": DateTime  }[]     | 200   |
