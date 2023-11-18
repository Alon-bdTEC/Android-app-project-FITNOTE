package com.example.fitnote13022021;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PictureFileHelper {

    public static void writeFileToInternalStorage(Context context, Bitmap bitmap, String filename)
    {
        SharedPreferences sp= context.getSharedPreferences("info",0);
        int counter=sp.getInt("counter", 0);
        try {
            FileOutputStream os = ((Activity)context).openFileOutput(filename+counter, Context.MODE_PRIVATE);
            //Here compress 50%, store the compressed data in os.
            bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
            counter++;
            SharedPreferences.Editor editor=sp.edit();
            editor.putInt("counter",counter);
            editor.commit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap readFileFromInternalStorage(Context context,String filename)
    {
        Bitmap b=null;
        try {
            InputStream in = ((Activity)context).openFileInput(filename);
            b= BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static Bitmap getPic(Context context, String name)
    {
        File mydir = context.getFilesDir();
        File lister = mydir.getAbsoluteFile();
        Bitmap bitmap=null;

        for (String list : lister.list())
        {
            if(list.toString().contains(name)) {
                //Toast.makeText(context, list, Toast.LENGTH_LONG).show();
                bitmap = readFileFromInternalStorage(context, list);

            }
        }

        return bitmap;
    }

}
