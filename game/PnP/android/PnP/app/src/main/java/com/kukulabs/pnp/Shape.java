package com.kukulabs.pnp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.JsonReader;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dilip on 2/8/17.
 */
public class Shape {
    private Bitmap bmp;
    private ImageView img;
    private String json;

    public Shape(Bitmap bmp, ImageView img, String json) throws JSONException {

        this.bmp = bmp;
        this.img = img;
        this.json = json;
        onDraw();
    }


    private void onDraw() throws JSONException {

        if (bmp.getWidth() == 0 || bmp.getHeight() == 0) {
            return;
        }

        int w = img.getMaxWidth(), h = img.getMaxHeight(); //TODO: This is giving 0 always. Better use bmp.getWidth() etc!!!

        Bitmap roundBitmap = getTriangleBitmap(w, h, json); // img.getMaxWidth(), img.getMaxHeight());
        img.setImageBitmap(roundBitmap);

    }


    public static Bitmap getTriangleBitmap(int w, int h, String json) throws JSONException {
//        Bitmap finalBitmap;
//        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
//            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
//                    false);
//        else
//            finalBitmap = bitmap;
//        Bitmap b = Bitmap.createBitmap(finalBitmap.getWidth(),
//                finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);

        Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, b.getWidth(),
//                b.getHeight());

        class Node {
            String x, y, coin;
            ArrayList<String> N, E, W, S;
        }


/*        List<Node> nodes = new ArrayList<Node>();
        Node n = new Node();
        n.x = "10";
        n.y = "1";
        n.coin = "";
        n.N = new ArrayList<String>();
        n.E = new ArrayList<String>();
        n.W = new ArrayList<String>();
        n.S = new ArrayList<String>();
        nodes.add(n);*/


        Path path = new Path();
        int mult = 50;
        int xOff = 0;
        int yOff = 0;
        JSONObject all = new JSONObject(json);
        JSONArray nodes = all.getJSONArray("nodes");
        int[] drawPath = new int[]{1, 23, 24, 1, 25, 24, 26, 1, 27, 26, 6, 2, 9, 15, 22, 16, 9, 15, 8, 6};
        JSONObject n = nodes.getJSONObject(drawPath[0]-1);
        path.moveTo(xOff+(mult*(float)n.getDouble("x")), yOff+(mult*(float)n.getDouble("y")));
        for(int i=1; i<drawPath.length; i++) {
            n = nodes.getJSONObject(drawPath[i]-1);
            path.lineTo(xOff+(mult*(float)n.getDouble("x")), yOff+(mult*(float)n.getDouble("y")));
        }
        path.close();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawPath(path, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(b, rect, rect, paint);

        return b;
    }
}
