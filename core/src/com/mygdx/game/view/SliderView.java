package com.mygdx.game.view;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class SliderView extends BaseView {

    public ImageView stickImage;
    public ImageView pointerImage;

    OnSliderNewValueListener onSliderNewValueListener;

    public boolean isDragging;

    public SliderView(int x, int y, int width, int height, SpriteBatch batch) {
        super(x, y, width, height, batch);

        stickImage = new ImageView(
                x, (int) (y + 1. / 3. * height),
                width, (int) (1. / 3. * height),
                "slider/sliderStick.png",
                batch
        );

        pointerImage = new ImageView(
                x, y,
                height, height,
                "slider/pointer.png", batch
        );

    }
    public float getValue(){
        return (float) (pointerImage.x-x)/(stickImage.width - pointerImage.width);
    }
    public void setOnSliderNewValueListener(OnSliderNewValueListener onSliderNewValueListener) {
        this.onSliderNewValueListener = onSliderNewValueListener;
    }

    @Override
    public void draw() {
        stickImage.draw();
        pointerImage.draw();
    }

    @Override
    public boolean isHit(int tx, int ty) {
        if (tx > x - 50 && tx < x + width + 50 && ty > y - 50 && ty < y + height + 50) {
            //if (onClickListener != null) onClickListener.onClick();
            return true;
        }
        return false;
    }

    public interface OnSliderNewValueListener {
        void onNewValue(float newValue);
    }

    public static class SliderInputProcessor extends InputAdapter {

        OrthographicCamera camera;
        SliderView sliderView;

        public SliderInputProcessor(OrthographicCamera camera, SliderView sliderView) {
            this.camera = camera;
            this.sliderView = sliderView;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 vector = new Vector3(screenX, screenY, 0);
            vector = camera.unproject(vector);
            if (sliderView.isHit((int) vector.x, (int) vector.y)) {
                sliderView.isDragging = true;
                int newX = (int) (vector.x - sliderView.pointerImage.width / 2.);
                newX = (int) Math.min(sliderView.x + sliderView.stickImage.width
                        - sliderView.pointerImage.width, newX);
                newX = (int) Math.max(sliderView.x, newX);

                sliderView.pointerImage.x = newX;
            }

            return super.touchDown(screenX, screenY, pointer, button);
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            Vector3 vector = new Vector3(screenX, screenY, 0);
            vector = camera.unproject(vector);
            if (sliderView.isDragging) {
                int newX = (int) (vector.x - sliderView.pointerImage.width / 2.);
                newX = (int) Math.min(sliderView.x + sliderView.stickImage.width
                        - sliderView.pointerImage.width, newX);
                newX = (int) Math.max(sliderView.x, newX);

                sliderView.pointerImage.x = newX;
            }

            return super.touchDragged(screenX, screenY, pointer);
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            Vector3 vector = new Vector3(screenX, screenY, 0);
            vector = camera.unproject(vector);

            if (sliderView.isDragging) sliderView.isDragging = false;
            sliderView.onSliderNewValueListener.onNewValue(
                    (sliderView.pointerImage.x-sliderView.x)/(sliderView.width - sliderView.pointerImage.width)
            );

            return super.touchUp(screenX, screenY, pointer, button);
        }
    }



}
