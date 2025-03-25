package prototype.wong_xin_thung;


import javax.swing.*;

public class Tower {
    private int level;
    private boolean hasDome;

    private static final ImageIcon[] TOWER_ICONS = {
            null,
            new ImageIcon("src/prototype/wong_xin_thung/pics/level1.png"),
            new ImageIcon("src/prototype/wong_xin_thung/pics/level2.png"),
            new ImageIcon("src/prototype/wong_xin_thung/pics/level3.png"),
            new ImageIcon("src/prototype/wong_xin_thung/pics/dome.png")
    };

    public Tower() {
        this.level = 0;
        this.hasDome = false;
    }

    public boolean build() {
        if (level < 4) {
            level++;
            if (level == 4) {
                hasDome = true;  // ✅ Set hasDome when level reaches 4
            }
            return true;
        }
        return false;
    }

    public int getLevel() {
        return level;
    }

    public boolean hasDome() {
        return hasDome;
    }

    // if the tower level is between 1-4, then return the image based on diff levels
    public static ImageIcon getIcon(int level) {
        return (level >= 1 && level <= 4) ? TOWER_ICONS[level] : null;
    }

    public String toString() {
        return hasDome ? "D" : (level > 0 ? String.valueOf(level) : "");
    }
}
