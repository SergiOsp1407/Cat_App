package com.sj.main;

import javax.swing.*;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {

        int optionMenu = -1;
        String[] buttons = {
                "1. See some cats",
                "2. Exit"
        };

        do{
            //Main menu
            String option = (String) JOptionPane.showInputDialog(null,"Java Kitties","Main Menu",JOptionPane.INFORMATION_MESSAGE,
                    null,buttons,buttons[0]);

            //We validate which option the user choose.
            for(int i = 0; i < buttons.length; i++){

                if(option.equals(buttons[i])){
                    optionMenu = i;
                }
            }

            switch (optionMenu){
                case 0:
                    CatsService.seeCats();
                    break;
                default:
                    break;
            }
        }while(optionMenu != 1);
    }
}
