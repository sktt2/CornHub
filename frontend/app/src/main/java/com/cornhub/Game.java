package com.cornhub;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A class representing the main logic of this demo.
 */
public class Game {

    private final ArrayList<Object> locks = new ArrayList<>();

    private final Predicate<Consumer<Canvas>> useCanvas;

    private final static int targetFps = 30;

    private final static long intervalFps = 1000L;

    private final static long intervalUps = 1000L;

    private final Counter frameCounter = new Counter();

    private final ElapsedTimer elapsedTimer = new ElapsedTimer();

    private final DeltaStepper fpsUpdater = new DeltaStepper(intervalFps, this::fpsUpdate);

    private final DeltaStepper upsUpdater = new DeltaStepper(intervalUps, this::upsUpdate);

    private final Paint fpsText = new Paint();

    private final Paint circlePaint = new Paint();

    private final Paint circleOutlinePaint = new Paint();

    private final Paint tickPaint = new Paint();

    private final Paint handPaint = new Paint();

    private final Paint spinnerPaint = new Paint();

    private final Runnable runnable;

    private int width = 0;

    private int height = 0;

    private double avgFps = 0.0;

    private boolean showFps = false;

    private int secondCount = 0;

    private float spinner = 0.0f;

    private boolean finished = false;

    private final ArrayList<BackgroundCircle> circles = new ArrayList<BackgroundCircle>();

    private final BackgroundCircleUpdaterPool backgroundCircleUpdaterPool = new BackgroundCircleUpdaterPool();

    public Game(final Runnable runnable, final Predicate<Consumer<Canvas>> useCanvas) {
        this.runnable = runnable;
        this.useCanvas = useCanvas;
        // Set the text for a frame-rate counter.
        {
            fpsText.setColor(Color.rgb(200, 200, 200));
            fpsText.setTextSize(40.0f);
        }
        // Set the background style.
        {
            circlePaint.setColor(Color.rgb(21, 28, 85));
            circleOutlinePaint.setColor(Color.WHITE);
        }
        // Set the seconds lines.
        {
            tickPaint.setColor(Color.rgb(255, 255, 255));
            tickPaint.setAntiAlias(true);
            tickPaint.setStrokeWidth(1);
            tickPaint.setStyle(Paint.Style.STROKE);
        }
        // Set the seconds hand.
        {
            handPaint.setColor(Color.rgb(198, 146, 0));
            handPaint.setAntiAlias(true);
            handPaint.setStrokeWidth(5);
            handPaint.setStyle(Paint.Style.STROKE);
        }
        // Set the progression arch.
        {
            spinnerPaint.setColor(Color.rgb(198, 146, 0));
            spinnerPaint.setAntiAlias(true);
            spinnerPaint.setStrokeWidth(7);
            spinnerPaint.setStyle(Paint.Style.STROKE);
        }
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        if (circles.size() == 0) {
            for (int i = 0; i < 100; ++i) {
                circles.add(new BackgroundCircle(width, height));
            }
        }
    }

    public void click(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            showFps = !showFps;
        } else if (circles.size() > 0) {
            backgroundCircleUpdaterPool.submit(this::growCircles);
        }
    }

    private boolean upsUpdate(long deltaTime) {
        if (secondCount < 60) {
            ++secondCount;
        }
        if (secondCount == 60) {
            if (!finished) {
                finished = true;
                try {
                    runnable.run();
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                spinnerPaint.setColor(Color.BLACK);
            }
        }
        return true;
    }

    private boolean fpsUpdate(long deltaTime) {
        final double fractionTime = intervalFps / (double)deltaTime;
        avgFps = frameCounter.getValue() * fractionTime;
        return false;
    }

    public void draw() {
        if (useCanvas.test(this::draw)) {
            frameCounter.increment();
        }
    }

    @SuppressLint("DefaultLocale")
    private void draw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        final float radius = Math.min(width, height) * 0.40f;
        final float centerWidth = width / 2.0f;
        final float centerHeight = height / 2.0f;
        // Draw the background.
        {
            canvas.drawColor(Color.BLACK);
            synchronized (mutex) {
                for (final BackgroundCircle circle : circles) {
                    final Paint paint = new Paint();
                    paint.setColor(Color.rgb(circle.getR(), circle.getG(), circle.getB()));
                    canvas.drawCircle(circle.getX(), circle.getY(), circle.getRadius(), paint);
                }
            }
        }
        // Draw the face of a clock.
        {
            canvas.drawCircle(centerWidth, centerHeight, radius * 1.02f, circleOutlinePaint);
            canvas.drawCircle(centerWidth, centerHeight, radius, circlePaint);
        }
        // Draw the center of a clock.
        {
            canvas.drawCircle(centerWidth, centerHeight, radius * 0.01f, tickPaint);
        }
        // Draw minute ticks.
        {
            final float start = radius * 0.85f;
            final float end = radius * 0.88f;
            for (int i = 0; i < 60; ++i) {
                final double angle = (Math.PI * 2.0) * (i / 60.0);
                final float x = (float) Math.sin(angle);
                final float y = (float) Math.cos(angle);
                canvas.drawLine(
                        centerWidth + (x * start),
                        centerHeight - (y * start),
                        centerWidth + (x * end),
                        centerHeight - (y * end),
                        tickPaint
                );
            }
        }
        // Draw the seconds' hand.
        {
            final float start = radius * 0.05f;
            final float end = radius * 0.9f;
            final double angle = (Math.PI * 2.0) * (secondCount / 60.0);
            final float x = (float) Math.sin(angle);
            final float y = (float) Math.cos(angle);
            canvas.drawLine(
                    centerWidth + (x * start),
                    centerHeight - (y * start),
                    centerWidth + (x * end),
                    centerHeight - (y * end),
                    handPaint
            );
        }
        // Draw the progressing arch.
        {
            final float d = radius * 0.95f;
            canvas.drawArc(
                    centerWidth - d,
                    centerHeight - d,
                    centerWidth + d,
                    centerHeight + d,
                    -90, spinner,
                    false,
                    spinnerPaint
            );
        }
        // Draw the frame-rate counter.
        {
            if (showFps) {
                canvas.drawText(
                        String.format("%.2f", avgFps),
                        10.0f, 30.0f,
                        fpsText
                );
            }
        }
    }

    public long getSleepTime() {
        final double targetFrameTime = (1000.0 / targetFps);
        final long updateEndTime = System.currentTimeMillis();
        final long updateTime = updateEndTime - elapsedTimer.getUpdateStartTime();
        return Math.round(targetFrameTime - updateTime);
    }

    public void update() {
        final long deltaTime = elapsedTimer.progress();
        if (deltaTime <= 0) {
            return;
        }
        // Step updates.
        upsUpdater.update(deltaTime);
        fpsUpdater.update(deltaTime);
        // Immediate updates.
        spinner += (deltaTime / (60.0f * 1000.0f)) * 360.0f;
    }

    private void growCircles() {
        Random dice = new Random();
        final int sleepTime = dice.nextInt(10);
        final int index = dice.nextInt(circles.size());
        int steps = dice.nextInt(100);
        while (steps-- > 0) {
            try {
                Thread.sleep(sleepTime);
                synchronized (mutex) {
                    circles.get(index).grow();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
