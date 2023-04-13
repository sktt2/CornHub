package com.cornhub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.function.Consumer;

/**
 * A class representing a view for the game activity.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final Game game = new Game(this::sendNotification, this::useCanvas);

    private GameThread gameThread;

    @SuppressLint("ClickableViewAccessibility")
    GameView(final Context context) {
        super(context);
        setKeepScreenOn(true);
        getHolder().addCallback(this);
        setFocusable(View.FOCUSABLE);
        setOnTouchListener((view, event) -> {
            game.click(event);
            return true;
        });
    }

    private void sendNotification() {
        NotificationPublisher.showNotification(getContext());
    }

    private boolean useCanvas(final Consumer<Canvas> onDraw) {
        boolean result = false;
        try {
            final SurfaceHolder holder = getHolder();
            final Canvas canvas = holder.lockCanvas();
            try {
                onDraw.accept(canvas);
            } finally {
                try {
                    holder.unlockCanvasAndPost(canvas);
                    result = true;
                } catch (final IllegalStateException e) {
                    // Do nothing
                }
            }
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void surfaceCreated(final SurfaceHolder surfaceHolder) {
        if ((gameThread == null) || (gameThread.getState() == Thread.State.TERMINATED)) {
            gameThread = new GameThread(game);
        }
        final Rect rect = getHolder().getSurfaceFrame();
        game.resize(rect.width(), rect.height());
        gameThread.startLoop();
    }

    @Override
    public void surfaceChanged(final SurfaceHolder surfaceHolder, final int format, final int width, final int height) {
        game.resize(width, height);
    }

    @Override
    public void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
        gameThread.stopLoop();
        gameThread = null;
    }

    @Override
    public void draw(final Canvas canvas) {
        super.draw(canvas);
        game.draw();
    }
}
