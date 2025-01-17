package com.xhlx.pstm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.io.CloseMode;

import com.xhlx.pstm.model.BodyType;
import com.xhlx.pstm.model.PstmMethod;
import com.xhlx.pstm.model.PstmRequest;
import com.xhlx.pstm.model.PstmResponse;
import com.xhlx.pstm.model.attr.PstmBodyFileItem;
import com.xhlx.pstm.model.attr.PstmBodyFormItem;
import com.xhlx.pstm.model.attr.PstmBodyItem;
import com.xhlx.pstm.model.attr.PstmBodyJsonItem;
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

        if (pstmReq.getMethod() == PstmMethod.POST) {
            if (pstmReq.getBodys() != null && pstmReq.getBodys().size() > 0) {
                
                if (pstmReq.getBodyType() == BodyType.FORMDATA) {
                    MultipartEntityBuilder bodyBuilder = MultipartEntityBuilder.create();
                    for (PstmBodyItem x : pstmReq.getBodys()) {
                        if (x instanceof PstmBodyFormItem) {
                            PstmBodyFormItem formItem = (PstmBodyFormItem) x;
                            StringBody contentBody = new StringBody(formItem.getValue(), ContentType.TEXT_PLAIN);
                            bodyBuilder.addPart(formItem.getKey(), contentBody);
                        } else if (x instanceof PstmBodyJsonItem) {
                            // nothing
                        } else if (x instanceof PstmBodyFileItem) {
                            PstmBodyFileItem fileItem = (PstmBodyFileItem) x;
                            FileBody file = new FileBody(new File(fileItem.getPath()));
                            bodyBuilder.addPart(fileItem.getName(), file);
                        }
                        
                        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                            bodyBuilder.build().writeTo(output);
                            requestBuilder.setBody(output.toByteArray(), ContentType.MULTIPART_FORM_DATA);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (pstmReq.getBodyType() == BodyType.URLENCODED) {
                    StringBuilder bodyStrBuilder = new StringBuilder();
                    for (PstmBodyItem x : pstmReq.getBodys()) {
                        if (x instanceof PstmBodyFormItem) {
                            PstmBodyFormItem formItem = (PstmBodyFormItem) x;
                            bodyStrBuilder.append("&").append(formItem.getKey()).append("=").append(formItem.getValue());
                        } else if (x instanceof PstmBodyJsonItem) {
                            // nothing
                        } else if (x instanceof PstmBodyFileItem) {
                            // nothing
                        }
                        
                        if (bodyStrBuilder.length() > 0) {
                            bodyStrBuilder.deleteCharAt(0);
                        }
                        requestBuilder.setBody(bodyStrBuilder.toString(), ContentType.APPLICATION_FORM_URLENCODED);
                    }
                } else if (pstmReq.getBodyType() == BodyType.RAW) {
                    PstmBodyJsonItem formItem = (PstmBodyJsonItem) pstmReq.getBodys().get(0);
                    
                    requestBuilder.setBody(formItem.getJson(), ContentType.APPLICATION_JSON);
                }
            }
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
