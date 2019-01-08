package com.bigpowerlifting.bigsoftware.bigpowerlifting.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedHashMap;

import javax.xml.transform.ErrorListener;

/**
 * Created by shanesepac on 6/9/18.
 */

public class Cacher {

    @FunctionalInterface
    public interface SerializationError{
        void onError(Exception ex);
    }

    /**
     * Returns true if serialization succeeded
     */
    private static class CacheSerializer extends AsyncTask<Object, Void, Void>{

        private SerializationError errorListener;

        CacheSerializer(SerializationError errorListener){
            this.errorListener = errorListener;
        }

        @Override
        protected Void doInBackground(Object... objects) {

            try
            {
                Context c = (Context)objects[0];
                String fileName = (String)objects[1];
                LinkedHashMap<String, String> hmap = (LinkedHashMap)objects[2];

                if(!isCancelled()) {
                    String filePath = c.getFilesDir().getPath() + fileName;
                    FileOutputStream fos =
                            new FileOutputStream(new File(filePath));
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(hmap);
                    oos.close();
                    fos.close();
                    System.out.printf("Serialized LinkedHashMap data is saved in LinkedHashMap.ser");
                }
            }catch(IOException | ClassCastException e)
            {
                e.printStackTrace();
                errorListener.onError(e);
            }
            return null;
        }
    }

    public static class CacheDeserializer extends AsyncTask<Object, Void, LinkedHashMap<String, String>>{

        @FunctionalInterface
        public interface DeserializationListener{
            void deserializationComplete(LinkedHashMap<String, String> hmap);
        }

        private DeserializationListener listener;
        private SerializationError errorListener;

        CacheDeserializer(DeserializationListener listener, SerializationError errorListener){
            this.listener = listener;
            this.errorListener = errorListener;
        }


        @Override
        protected LinkedHashMap<String, String> doInBackground(Object... objects) {
            LinkedHashMap<String, String> map = null;
            try
            {
                Context c = (Context)objects[0];
                String filename = (String)objects[1];

                if(!isCancelled()) {
                    String filePath = c.getFilesDir().getPath() + filename;
                    FileInputStream fis = new FileInputStream(new File(filePath));
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    map = (LinkedHashMap) ois.readObject();
                    ois.close();
                    fis.close();
                }
                return map;
            }catch(IOException|ClassNotFoundException|ClassCastException e)
            {
                e.printStackTrace();
                errorListener.onError(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(LinkedHashMap<String, String> stringStringLinkedHashMap) {
            super.onPostExecute(stringStringLinkedHashMap);
            if(!isCancelled()) {
                listener.deserializationComplete(stringStringLinkedHashMap);
            }
        }
    }

    public static void serializeLinkedHashMap(LinkedHashMap<String, String> hmap, String fileName, Context c, SerializationError errorListener){
        new CacheSerializer(errorListener).execute(c, fileName, hmap);
    }

    public static void deserializeLinkedHashMap(String filename, Context c,
                                          CacheDeserializer.DeserializationListener listener,
                                          SerializationError errorListener){
        new CacheDeserializer(listener, errorListener).execute(c, filename);
    }
}
