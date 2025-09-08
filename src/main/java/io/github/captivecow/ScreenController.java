package io.github.captivecow;

import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenController {

    private final ScreenView screenView;
    private final ScreenModel screenModel;
    private final ScheduledExecutorService scheduler;

    public ScreenController() {
        screenModel = new ScreenModel();
        screenView = new ScreenView(screenModel, this);
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void init() {
        scheduler.scheduleAtFixedRate(screenModel::update, 0, 1, TimeUnit.SECONDS);
        SwingUtilities.invokeLater(screenView);
    }

    public BufferedImage getDisplayBuffer(){
        return screenModel.getDisplayBuffer();
    }
}
