package io.github.captivecow;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class ScreenView extends JFrame implements Runnable, ScreenModelObserver {

    class DrawPanel extends JPanel {
        public DrawPanel(){
            super();
            setPreferredSize(new Dimension(800, 600));
            setDoubleBuffered(false);
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            g.drawImage(screenController.getDisplayBuffer(), 0, 0, null);
            graphics2D.dispose();
        }
    }

    private final GridBagLayout layout;
    private final GridBagConstraints constraints;
    private final DrawPanel panel;
    private final ScreenController screenController;

    public ScreenView(ScreenModel screenModel, ScreenController screenController) {
        super("Screen");
        panel = new DrawPanel();
        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        screenModel.registerObserver(this);
        this.screenController = screenController;
    }

    private void createAndShowGui() {
        getContentPane().setLayout(layout);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(panel, constraints);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void run() {
        createAndShowGui();
    }

    @Override
    public void updateView() {
        panel.repaint();
    }
}
