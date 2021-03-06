package com.example.group4.group4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;

/**
 * GraphView creates a scaled line or bar graph with x and y axis labels.
 * @author Arno den Hond
 *
 */
public class GraphView extends View {

    public static boolean BAR = false;
    public static boolean LINE = true;

    private Paint paint;
    private float[] values;
    private String[] horlabels;
    private String[] verlabels;
    private String title;
    private boolean type;

    public GraphView(Context context, float[] values, String title, String[] horlabels, String[] verlabels, boolean type) {
        super(context);
        if (values == null)
            values = new float[0];
        else
            this.values = values;
        if (title == null)
            title = "";
        else
            this.title = title;
        if (horlabels == null)
            this.horlabels = new String[0];
        else
            this.horlabels = horlabels;
        if (verlabels == null)
            this.verlabels = new String[0];
        else
            this.verlabels = verlabels;
        this.type = type;
        paint = new Paint();
    }

    public void setValues(float[] newValues)
    {
        this.values = newValues;
    }
    public void setX_AXIS(String[] newValues) { this.horlabels = newValues;}
//    public void setMark(String[] newHorlabels)
//    {
//        this.horlabels = newHorlabels;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        float border = 60;
        float horstart = border * 2;
        float height = getHeight();
        float width = getWidth()-1;
        float max = 10;
        float min = -10;
        float diff = max - min;
        float graphheight = height - (2 * border);
        float graphwidth = width - (2 * border);


        paint.setTextSize(40);
        paint.setTextAlign(Align.LEFT);
        int vers = verlabels.length - 1;
        for (int i = 0; i < verlabels.length; i++) {
            paint.setColor(Color.BLACK);
            float y = ((graphheight / vers) * i) + border;
            canvas.drawLine(horstart, y, width, y, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(verlabels[i], 0, y, paint);
        }
        int hors = horlabels.length - 1;
        for (int i = 0; i < horlabels.length; i++) {
            paint.setColor(Color.BLACK);
            float x = ((graphwidth / hors) * i) + horstart;
            canvas.drawLine(x, height - border, x, border, paint);
            paint.setTextAlign(Align.CENTER);
            if (i==horlabels.length-1)
                paint.setTextAlign(Align.RIGHT);
            if (i==0)
                paint.setTextAlign(Align.LEFT);
            paint.setColor(Color.WHITE);
            canvas.drawText(horlabels[i], x, height - 4, paint);
        }

        paint.setTextAlign(Align.CENTER);
        canvas.drawText(title, (graphwidth / 2) + horstart, border - 4, paint);

//        if (max != min) {
//            paint.setColor(Color.LTGRAY);
//            if (type == BAR) {
//                float datalength = values.length;
//                float colwidth = (width - (2 * border)) / datalength;
//                for (int i = 0; i < values.length; i++) {
//                    float val = values[i] - min;
//                    float rat = val / diff;
//                    float h = graphheight * rat;
//                    canvas.drawRect((i * colwidth) + horstart, (border - h) + graphheight, ((i * colwidth) + horstart) + (colwidth - 1), height - (border - 1), paint);
//                }
//            } else {
//                float datalength = values.length;
//                float colwidth = (width - (2 * border)) / datalength;
//                float halfcol = colwidth / 2;
//                float lasth = 0;
//                for (int i = 0; i < values.length; i++) {
//                    float val = values[i] - min;
//                    float rat = val / diff;
//                    float h = graphheight * rat;
//                    if (i > 0)
//                        paint.setColor(Color.GREEN);
//                    paint.setStrokeWidth(2.0f);
//
//                    canvas.drawLine(((i - 1) * colwidth) + (horstart + 1) + halfcol, (border - lasth) + graphheight, (i * colwidth) + (horstart + 1) + halfcol, (border - h) + graphheight, paint);
//                    lasth = h;
//                }
//            }
//        }

        int stride = 3;
        //Color of three line
        int colors[] = { Color.RED, Color.BLUE, Color.YELLOW };

        for (int line_index = 0; line_index < stride; line_index++) {
            int datalength = values.length / stride;
            float colwidth = (width - (2 * border)) / 9;
            float halfcol = colwidth / 2;
            float lasth = 0;
            paint.setColor(colors[line_index % stride]);
            paint.setStrokeWidth(7.0f);

            for (int i = 0; i < datalength; i++) {
                float val = values[line_index + i * stride] - min;
                float rat = val / diff;
                float h = graphheight * rat;
                if (i > 0) {
                    canvas.drawLine(
                            ((i - 1) * colwidth) + (horstart + 1),
                            (border - lasth) + graphheight,
                            (i * colwidth) + (horstart + 1),
                            (border - h) + graphheight, paint);
                }
                lasth = h;
            }
        }
    }


}
