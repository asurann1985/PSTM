package com.xhlx.pstm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.io.CloseMode;

import com.xhlx.pstm.model.PstmMethod;
import com.xhlx.pstm.model.PstmRequest;
import com.xhlx.pstm.model.PstmResponse;
import com.xhlx.pstm.model.attr.PstmHeaderItem;
import com.xhlx.pstm.model.attr.PstmQueryParamItem;
import com.xhlx.pstm.model.attr.auth.PstmAuth;

public class PstmSender {

    private static String judgeUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        } else {
            return "http://" + url;
        }
    }

    public static PstmResponse send(PstmRequest pstmReq) {

//        final IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setSoTimeout(Timeout.ofSeconds(300)).build();

        final CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();

        client.start();

        SimpleRequestBuilder requestBuilder = null;

        if (pstmReq.getMethod() == PstmMethod.GET) {
            requestBuilder = SimpleRequestBuilder.get(judgeUrl(pstmReq.getUrl()));
        } else if (pstmReq.getMethod() == PstmMethod.POST) {
            requestBuilder = SimpleRequestBuilder.post(judgeUrl(pstmReq.getUrl()));
        } else if (pstmReq.getMethod() == PstmMethod.DELETE) {
            requestBuilder = SimpleRequestBuilder.delete(judgeUrl(pstmReq.getUrl()));
        }

        if (pstmReq.getHeaders() != null && pstmReq.getHeaders().size() > 0) {
            for (PstmHeaderItem x : pstmReq.getHeaders()) {
                if (x.isActive()) {
                    requestBuilder.addHeader(x.getKey(), x.getValue());
                }
            }
        }

        if (pstmReq.getParams() != null && pstmReq.getParams().size() > 0) {
            for (PstmQueryParamItem x : pstmReq.getParams()) {
                if (x.isActive()) {
                    requestBuilder.addParameter(x.getKey(), x.getValue());
                }
            }
        }

        if (pstmReq.getAuths() != null) {
            PstmAuth auth = pstmReq.getAuths().stream().filter(x -> x.isActive()).findFirst().orElse(null);
            if (auth != null) {
                auth.auth(requestBuilder);
            }
        }

        if (pstmReq.getBodys() != null && pstmReq.getBodys().size() > 0) {
            pstmReq.getBodys().forEach(x -> {
                // TODO to fix it
//                requestBuilder.addParameter(x.getKey(), x.getValue());
            });
        }

        SimpleHttpRequest request = requestBuilder.build();
        System.out.println("Executing reqeust");
        final Future<SimpleHttpResponse> future = client.execute(SimpleRequestProducer.create(request),
                SimpleResponseConsumer.create(), new FutureCallback<SimpleHttpResponse>() {

                    @Override
                    public void completed(SimpleHttpResponse response) {
                        System.out.println(request + "->" + new StatusLine(response));
                        System.out.println(response.getBody());
                    }

                    @Override
                    public void failed(Exception ex) {
                        System.out.println(request + "->" + ex);
                    }

                    @Override
                    public void cancelled() {
                        System.out.println(request + "canceled");
                    }
                });
        SimpleHttpResponse response = null;
        try {
            response = future.get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        PstmResponse pstmResp = new PstmResponse();

        pstmResp.setStateLine(response.getCode());
        pstmResp.setStringBody(response.getBodyText());
        pstmResp.setByteBody(response.getBodyBytes());

        System.out.println("Shutting down");
        client.close(CloseMode.GRACEFUL);

        return pstmResp;
    }
}
