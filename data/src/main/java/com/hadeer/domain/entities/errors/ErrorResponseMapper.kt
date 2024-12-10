package com.hadeer.domain.entities.errors

internal fun ErrorResponse.getErrorMessage():ErrorResponseModal{
    return ErrorResponseModal(
        message = message
    )
}