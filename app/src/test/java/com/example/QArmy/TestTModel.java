package com.example.QArmy;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTModel {
    private boolean called;
    class MyView implements TView<TModel> {
        @Override
        public void update(TModel model) {
            called = true;
        }
    }

    @Test
    public void testNotify() {
        TModel<MyView> model = new TModel<>();
        model.addView(new MyView());
        model.notifyViews();
        assertTrue(called);
    }
}
