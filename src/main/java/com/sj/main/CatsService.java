package com.sj.main;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatsService {

    public static void seeCats() throws IOException {

        //1. Getting data from the API - GET
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        String responseJson = response.body().string();

        //Deleting the first and the last character from the Json response
        responseJson = responseJson.substring(1, responseJson.length());
        responseJson = responseJson.substring(0, responseJson.length()-1);

        //Parsing to Cat Object
        Gson gson = new Gson();
        Cats cats = gson.fromJson(responseJson, Cats.class);

        //Resizing the image that we get
        Image image = null;

        try{
            URL url = new URL(cats.getUrl());
            image = ImageIO.read(url);

            ImageIcon backgroundCat = new ImageIcon(image);

            if(backgroundCat.getIconWidth() > 800){
                //Resizing
                Image background = backgroundCat.getImage();
                Image resizedImage = background.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                backgroundCat = new ImageIcon(resizedImage);
            }
        }catch(IOException e){

            System.out.println(e.getMessage());
        }


    }

}
