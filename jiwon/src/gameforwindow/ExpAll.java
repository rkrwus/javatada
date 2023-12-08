package gameforwindow;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

public class ExpAll {

    public static Image[] img_exp = new Image[27];

    static {
        for (int i = 0; i < img_exp.length; i++) {
            String filename = String.format("src/image_exp/exp_%02d.png", i + 1);
            System.out.println(filename);
            System.out.println("URL: " + ExpAll.class.getResource(filename));
            img_exp[i] = new ImageIcon(filename).getImage();
        }
    }

    public static class ExplosionManager {

        public List<Explosion> explosion_list = new ArrayList<>();

        public void makeExplosion(int x, int y) {
            Explosion exp = new Explosion();
            exp.pos.x = x - img_exp[0].getWidth(null) / 2;
            exp.pos.y = y - img_exp[0].getHeight(null) / 2;
            explosion_list.add(exp);
        }

        public void move() {
            Iterator<Explosion> iterator = explosion_list.iterator();
            while (iterator.hasNext()) {
                Explosion exp = iterator.next();
                if (!exp.move()) {
                    iterator.remove();
                }
            }
        }

        public void draw(Graphics g) {
            for (Explosion exp : explosion_list) {
                exp.draw(g);
            }
        }
    }

    public static class Explosion {
        public Rectangle pos = new Rectangle();
        public static final int INDEX_INTERVAL = 2;
        int index;
        int index_interval = INDEX_INTERVAL;

        public boolean move() {
            if (index_interval == 2) {
                index++;
            }
            index_interval--;
            if (index_interval < 0)
                index_interval = INDEX_INTERVAL;
            return (index < img_exp.length);
        }

        public void draw(Graphics g) {
            g.drawImage(img_exp[index], pos.x, pos.y, null);
        }
    }
}
