package id.ryanandri.dekontaminasi.services

data class ResponseHandler<out T>(val status: Status?, val data: T?, val msg: String?) {
    companion object {
        fun <T> loading(data: T?): ResponseHandler<T> =
                ResponseHandler(status = Status.LOADING, data = data, msg = null)

        fun <T> success(data: T): ResponseHandler<T> =
                ResponseHandler(status = Status.SUCCESS, data = data, msg = null)

        fun <T> error(data: T?, message: String): ResponseHandler<T> =
                ResponseHandler(status = Status.ERROR, data = data, msg = message)
    }
}