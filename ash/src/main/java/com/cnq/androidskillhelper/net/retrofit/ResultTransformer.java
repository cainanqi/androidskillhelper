package com.cnq.androidskillhelper.net.retrofit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;


/**
 * Author by ${HeXinGen}, Date on 2018/9/19.
 *
 * 处理网络，解析异常，服务器返回数据异常等情况
 */
public class ResultTransformer {
    /**
     *  服务器API异常，解析异常，服务器返回数据异常的处理操作
     * @param <T>
     * @return
     */
    public static  <T> ObservableTransformer<Result<T>,T>  handleResult(){
        return upstream -> upstream
                .onErrorResumeNext(new ErrorFunction<>())
                .flatMap(new ResponseFunction<>());
    }

    /**
     * 处理网络异常或者解析错误等
     * @param <T>
     */
    private static class ErrorFunction<T> implements Function<Throwable, ObservableSource<? extends Result<T>>>{
        @Override
        public ObservableSource<? extends Result<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(ApiException.ExceptionHandler.handlerException(throwable));
        }
    }
    /**
     * 处理常见的业务逻辑错误,非正常code，返回异常
     * 这里适配20000
     */
   private static class  ResponseFunction<T> implements Function<Result<T>, ObservableSource<T>>{
        @Override
        public ObservableSource<T> apply(Result<T> tResult) throws Exception {
            int code=tResult.getCode();
            return code==20000?(tResult.getData()==null?Observable.error(new ApiException(ApiException.error_response_bean_empty,"response bean empty")):Observable.just(tResult.getData()))
                    :Observable.error(new ApiException(code,tResult.getMsg()));
        }
    }
}
