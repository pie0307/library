package pro.pie.me.httpclient.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import pro.pie.me.httpclient.client.HeaderVO;
import pro.pie.me.httpclient.client.HttpClientUtils;
import pro.pie.me.httpclient.exception.HttpClientException;

import java.util.Map;

public class HttpHelper {


    private HttpClientUtils httpClientUtils;

    public HttpHelper() {

    }


    public HttpHelper config(HttpClientUtils httpClientUtils) {
        setHttpClientUtils(httpClientUtils);
        return this;
    }

    public final HttpResult httpPostJson(String url, String jsonParams, HttpConfig httpConfig) {
        try {

            String result = getHttpClientUtils().httpPostJson(url, jsonParams);
            if (StringUtils.isBlank(result)) {
                return HttpResult.ofErr("not return");
            }
            JSONObject resp = JSON.parseObject(result);

            HttpResult ret = parse(resp, httpConfig);

            return ret;
        } catch (HttpClientException httpClientException) {
            return HttpResult.of(httpClientException);
        } catch (JSONException jsonException) {
            return HttpResult.of(jsonException);
        } catch (Exception e) {
            return HttpResult.of(e);
        }
    }

    public final HttpResult httpPostJson(String url, String jsonParams, HeaderVO headerVO, HttpConfig httpConfig) {
        return HttpPostJson(url, jsonParams, null, -1, httpConfig);
    }

    public final HttpResult httpPostJson(String url, String jsonParams, int requestTimeout, HttpConfig httpConfig) {
        return HttpPostJson(url, jsonParams, null, requestTimeout, httpConfig);
    }

    public final HttpResult HttpPostJson(String url, String jsonParams, HeaderVO headerVO, int requestTimeout, HttpConfig httpConfig) {
        try {
            String result = getHttpClientUtils().httpPostJson(url, jsonParams, requestTimeout, getHttpClientUtils().DEFAULT_ENCODING, headerVO);
            if (StringUtils.isBlank(result)) {
                return HttpResult.ofErr("not return");
            }
            JSONObject resp = JSON.parseObject(result);

            HttpResult ret = parse(resp, httpConfig);

            return ret;
        } catch (HttpClientException httpClientException) {
            return HttpResult.of(httpClientException);
        } catch (JSONException jsonException) {
            return HttpResult.of(jsonException);
        } catch (Exception e) {
            return HttpResult.of(e);
        }
    }

    public final HttpResult httpPostForm(String url, Map<String, String> params, HttpConfig httpConfig) {
        return httpPostForm(url, params, null, httpConfig);
    }

    public final HttpResult httpPostForm(String url, Map<String, String> params, HeaderVO headerVO, HttpConfig httpConfig) {
        return httpPostForm(url, params, null, -1, httpConfig);
    }

    public final HttpResult httpPostForm(String url, Map<String, String> params, int requestTimeout, HttpConfig httpConfig) {
        return httpPostForm(url, params, null, requestTimeout, httpConfig);
    }

    public final HttpResult httpPostForm(String url, Map<String, String> params, HeaderVO headerVO, int requestTimeout, HttpConfig httpConfig) {
        try {
            String result = getHttpClientUtils().httpPostForm(url, headerVO, params, requestTimeout, getHttpClientUtils().DEFAULT_ENCODING);
            if (StringUtils.isBlank(result)) {
                return HttpResult.ofErr("not return");
            }
            JSONObject resp = JSON.parseObject(result);

            HttpResult ret = parse(resp, httpConfig);

            return ret;
        } catch (HttpClientException httpClientException) {
            return HttpResult.of(httpClientException);
        } catch (JSONException jsonException) {
            return HttpResult.of(jsonException);
        } catch (Exception e) {
            return HttpResult.of(e);
        }
    }

    public HttpResult httpGet(String url, Map<String, String> queryParams, HttpConfig httpConfig) {

        try {
            String result = getHttpClientUtils().httpGet(url, queryParams);
            if (StringUtils.isBlank(result)) {
                return HttpResult.ofErr("not return");
            }
            JSONObject resp = JSON.parseObject(result);
            HttpResult ret = parse(resp, httpConfig);
            return ret;
        } catch (HttpClientException httpClientException) {
            return HttpResult.of(httpClientException);
        } catch (JSONException jsonException) {
            return HttpResult.of(jsonException);
        } catch (Exception e) {
            return HttpResult.of(e);
        }
    }

    public HttpResult httpGet(String url, Map<String, String> queryParams, int requestTimeout, HttpConfig httpConfig) {

        try {
            String result = getHttpClientUtils().httpGet(url, queryParams, requestTimeout);
            if (StringUtils.isBlank(result)) {
                return HttpResult.ofErr("not return");
            }
            JSONObject resp = JSON.parseObject(result);
            HttpResult ret = parse(resp, httpConfig);
            return ret;
        } catch (HttpClientException httpClientException) {
            return HttpResult.of(httpClientException);
        } catch (JSONException jsonException) {
            return HttpResult.of(jsonException);
        } catch (Exception e) {
            return HttpResult.of(e);
        }
    }

    private HttpResult parse(JSONObject resp, HttpConfig httpConfig) {
        StatusConfig statusConfig = httpConfig.getConfig();

        Object messagevalue = resp.get(statusConfig.getMessageField());
        Object statusCodeValue = resp.get(statusConfig.getStatusField());
        //成功
        if (statusCodeValue.toString().equals(statusConfig.getSuccCode())) {
            String data = resp.getString(statusConfig.getDataField());
            HttpResult result = HttpResult.ofSucc(String.valueOf(messagevalue), data);
            result.setSourceStatusCode(statusCodeValue);
            return result;
        } else {
            //失败
            return HttpResult.ofErr(messagevalue.toString());
        }
    }

    public HttpClientUtils getHttpClientUtils() {
        Preconditions.checkArgument(httpClientUtils != null, "httpClientUtils must be set");
        return httpClientUtils;
    }

    public synchronized void setHttpClientUtils(HttpClientUtils httpClientUtils) {
        this.httpClientUtils = httpClientUtils;
    }
}
