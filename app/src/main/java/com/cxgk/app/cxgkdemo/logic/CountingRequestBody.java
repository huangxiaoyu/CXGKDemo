package com.cxgk.app.cxgkdemo.logic;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Huang on 2016/7/26.
 */
public class CountingRequestBody extends RequestBody {
    RequestBody mRequestBody;
    RequestProgressListener mListener;
    BufferedSink mSink;

    public CountingRequestBody(RequestBody requestBody, RequestProgressListener listener) {
        mRequestBody = requestBody;
        mListener = listener;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mSink == null) {
            //包装
            mSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(mSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        mSink.flush();


    }

    private Sink sink(BufferedSink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调
                mListener.onRequestProgress(bytesWritten, contentLength, bytesWritten == contentLength);
            }
        };

    }

    public interface RequestProgressListener {
        /**
         * 进度监听
         *
         * @param bytesWritten  已经上传的长度
         * @param contentLength 总长度
         * @param finish        是否完成上传
         */
        void onRequestProgress(long bytesWritten, long contentLength, boolean finish);
    }
}
