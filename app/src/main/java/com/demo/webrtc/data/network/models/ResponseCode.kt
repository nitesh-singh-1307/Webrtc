package com.demo.newsapplication.data.network.models

enum class ResponseCode constructor(val code: Int) {
    OK(200),
    CREATED(201),
    BadRequest(400),
    Unauthenticated(401),
    Unauthorized(403),
    NotFound(404),
    RequestTimeOut(408),
    Conflict(409),
    InvalidData(422),
    Blocked(423),
    ForceUpdate(426),
    InternalServerError(500)
}