package com.sj.main;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
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
        Cats cat = gson.fromJson(responseJson, Cats.class);

        //Resizing the image that we get
        Image image = null;

        try{

            //URL definitions lets download the pic from the API
            URL url = new URL(cat.getUrl());

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.addRequestProperty("User-Agent", "");
            BufferedImage bufferedImage = ImageIO.read(http.getInputStream());

            ImageIcon backgroundCat = new ImageIcon(bufferedImage);


            if(backgroundCat.getIconWidth() > 800){
                //Resizing
                Image background = backgroundCat.getImage();
                Image resizedImage = background.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                backgroundCat = new ImageIcon(resizedImage);
            }

            String menu = "Opciones: \n"
                    + "1. See other cat \n"
                    + "2. Add to favorites \n"
                    + "3. Return \n";

            String[] buttons = {"See other cat", "Add to favorites", "Return"};
            String catId = cat.getId();
            String option = (String) JOptionPane.showInputDialog(null, menu, catId,
                    JOptionPane.INFORMATION_MESSAGE,backgroundCat,buttons,buttons[0]);

            int selectedOption = -1;

            for(int i = 0; i < buttons.length; i++){
                if(option.equals(buttons[i])){
                    selectedOption = i;
                }
            }

            switch (selectedOption){
                case 0:
                    seeCats();
                    break;
                case 1:
                    setFavoriteCat(cat);
                    break;
                default:

                    break;

            }

        }catch(IOException e){

            System.out.println(e.getMessage());
        }

    }
    
    public static void setFavoriteCat(Cats cat){



    }

}
