package com.androidlearning.boris.familycentralcontroler.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by poxiaoge on 2016/12/22.
 */

public class SocketUtil {


    public static byte[] readInputBytes(InputStream inStream)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len=0;
        byte[] buffer = new byte[1024];
        try
        {
            //int len = inStream.read(buffer);
            //outputStream.write(buffer, 0, length);
            while((len=inStream.read(buffer))!=-1)
            {
                //System.out.println("--"+len);
                outputStream.write(buffer, 0, len);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }


}
