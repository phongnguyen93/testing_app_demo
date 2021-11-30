package com.example.app.movie.util

import com.example.app.movie.data.Result
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.uiSubscribe(): Observable<T> {
  return subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.uiSubscribe(): Flowable<T> {
  return subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.resultSubscriber(
  onResult: (Result<T>) -> Unit
): Disposable {
  return subscribe({
    onResult(Result.Success(it))
  }, {
    onResult(Result.Error(it))
  })
}

fun composePosterImg(url: String) = "${ServiceConst.IMAGE_POSTER_URL}/$url"

fun composeBackdropImg(url: String) = "${ServiceConst.IMAGE_BACKDROP_URL}/$url"