package com.zakia.idn.javaretrofitcrud.remote;

public class APIUtils {
    private APIUtils(){

    }

    public static final String API_URL = "http://192.168.1.6/marketplace2/index.php/";
    public static ProductService getProductService(){
        return RetrofitClient.getClient(API_URL).create(ProductService.class);
    }
}
